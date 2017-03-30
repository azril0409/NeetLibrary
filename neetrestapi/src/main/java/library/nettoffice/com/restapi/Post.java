package library.nettoffice.com.restapi;

import org.springframework.http.converter.HttpMessageConverter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Deo on 2016/3/8.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Post {
    String url() default "";

    Header[] headers() default {};

    Class<? extends HttpMessageConverter>[] converters() default {};

    Class<?> responseErrorHandler() default Void.class;
}
