package library.nettoffice.com.restapi;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Deo-chainmeans on 2017/3/21.
 */

class RestApiHelp {
    final RestTemplate restTemplate = new RestTemplate();

    <Request, Response> Response request(Request request, Reference<Response> typeReference) {
        final Class<?> requestClass = request.getClass();
        final Get get = requestClass.getAnnotation(Get.class);
        final Post post = requestClass.getAnnotation(Post.class);
        final Put put = requestClass.getAnnotation(Put.class);
        final Patch patch = requestClass.getAnnotation(Patch.class);
        final Delete delete = requestClass.getAnnotation(Delete.class);

        final Accept accept = requestClass.getAnnotation(Accept.class);
        final ContentType contentType = requestClass.getAnnotation(ContentType.class);

        final ArrayList<Field> headerList = new ArrayList<>();
        final ArrayList<Field> pathList = new ArrayList<>();
        final ArrayList<Field> cookieList = new ArrayList<>();
        Class<?> c = requestClass;
        Object body = null;
        do {
            final Field[] fields = c.getDeclaredFields();
            for (Field field : fields) {
                final Header header = field.getAnnotation(Header.class);
                if (header != null) {
                    headerList.add(field);
                }
                final Path path = field.getAnnotation(Path.class);
                if (path != null) {
                    pathList.add(field);
                }
                final Cookie cookie = field.getAnnotation(Cookie.class);
                if (cookie != null) {
                    cookieList.add(field);
                }
                if (body == null) {
                    final Body b = field.getAnnotation(Body.class);
                    if (header != null) {
                        try {
                            field.setAccessible(true);
                            body = field.get(request);
                        } catch (IllegalAccessException e) {
                        }
                    }
                }
            }
            c = c.getSuperclass();
        } while (c != null);

        final String url;
        final HttpMethod method;
        Class<? extends HttpMessageConverter>[] converters;
        Header[] headers;
        Class<?> responseErrorHandler;
        if (get != null) {
            url = get.url();
            method = HttpMethod.GET;
            converters = get.converters();
            headers = get.headers();
            responseErrorHandler = get.responseErrorHandler();
        } else if (post != null) {
            url = post.url();
            method = HttpMethod.POST;
            converters = post.converters();
            headers = post.headers();
            responseErrorHandler = post.responseErrorHandler();
        } else if (put != null) {
            url = put.url();
            method = HttpMethod.PUT;
            converters = put.converters();
            headers = put.headers();
            responseErrorHandler = put.responseErrorHandler();
        } else if (patch != null) {
            url = patch.url();
            method = HttpMethod.PATCH;
            converters = patch.converters();
            headers = patch.headers();
            responseErrorHandler = patch.responseErrorHandler();
        } else if (delete != null) {
            url = delete.url();
            method = HttpMethod.DELETE;
            converters = delete.converters();
            headers = delete.headers();
            responseErrorHandler = delete.responseErrorHandler();
        } else {
            return null;
        }
        restTemplate.getMessageConverters().clear();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        for (Class<? extends HttpMessageConverter> converter : converters) {
            try {
                restTemplate.getMessageConverters().add(converter.newInstance());
            } catch (Exception e) {
            }
        }
        try {
            final Object errorHandler = responseErrorHandler.newInstance();
            if (errorHandler instanceof ResponseErrorHandler) {
                restTemplate.setErrorHandler((ResponseErrorHandler) errorHandler);
            }
        } catch (Exception e) {
        }
        final HttpHeaders httpHeaders = getHttpHeaders(headers, headerList, request);
        httpHeaders.add("Cookie", getCookie(cookieList, request));
        httpHeaders.setContentType(getContentType(contentType));
        httpHeaders.setAccept(getAccepts(accept));
        final HttpEntity<Object> requestEntity = new HttpEntity<>(body, httpHeaders);
        final HashMap<String, Object> urlVariables = getUrlVariables(pathList, request);
        final ResponseEntity<Response> responseEntity = restTemplate.exchange(url, method, requestEntity, typeReference, urlVariables);
        return responseEntity.getBody();
    }

    private HttpHeaders getHttpHeaders(Header[] headers, List<Field> headerList, Object request) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        for (Header header : headers) {
            httpHeaders.set(header.name(), header.value());
        }
        for (Field field : headerList) {
            final Header header = field.getAnnotation(Header.class);
            try {
                final String name;
                if (header.name().isEmpty()) {
                    name = field.getName();
                } else {
                    name = header.name();
                }
                field.setAccessible(true);
                final Object value = field.get(request);
                if (value != null && value instanceof String) {
                    httpHeaders.set(name, (String) value);
                } else if (!header.value().isEmpty()) {
                    httpHeaders.set(name, header.value());
                }
            } catch (IllegalAccessException e) {
            }
        }
        return httpHeaders;
    }

    private org.springframework.http.MediaType getMediaType(String accept, String charset) {
        final org.springframework.http.MediaType mediaType = org.springframework.http.MediaType.parseMediaType(accept);
        return new org.springframework.http.MediaType(mediaType.getType(), mediaType.getSubtype(), Charset.forName(charset));
    }

    private org.springframework.http.MediaType getContentType(ContentType contentType) {
        if (contentType == null) {
            return getMediaType(library.nettoffice.com.restapi.MediaType.ALL, "UTF-8");
        } else {
            return getMediaType(contentType.value(), contentType.charset());
        }
    }

    private List<org.springframework.http.MediaType> getAccepts(Accept accept) {
        if (accept == null) {
            return Collections.singletonList(getMediaType(library.nettoffice.com.restapi.MediaType.ALL, "UTF-8"));
        } else {
            return Collections.singletonList(getMediaType(accept.contentType(), accept.charset()));
        }
    }

    private String getCookie(List<Field> cookieList, Object request) {
        final StringBuilder cookiesValue = new StringBuilder();
        for (Field field : cookieList) {
            try {
                final Cookie cookie = field.getAnnotation(Cookie.class);
                final String name;
                if (cookie.value().isEmpty()) {
                    name = field.getName();
                } else {
                    name = cookie.value();
                }
                field.setAccessible(true);
                final Object value = field.get(request);
                if (value != null) {
                    cookiesValue.append(String.format("%s=%s;", name, value.toString()));
                }
            } catch (Exception e) {
            }
        }
        return cookiesValue.toString();
    }

    private HashMap<String, Object> getUrlVariables(List<Field> pathList, Object request) {
        final HashMap<String, Object> urlVariables = new HashMap<>();
        for (Field field : pathList) {
            final Path path = field.getAnnotation(Path.class);
            try {
                final String name;
                if (path.value().isEmpty()) {
                    name = field.getName();
                } else {
                    name = path.value();
                }
                field.setAccessible(true);
                final Object value = field.get(request);
                if (value != null) {
                    urlVariables.put(name, value);
                }
            } catch (Exception e) {
            }
        }
        return urlVariables;
    }
}
