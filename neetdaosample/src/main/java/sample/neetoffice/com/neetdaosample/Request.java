package sample.neetoffice.com.neetdaosample;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import library.nettoffice.com.restapi.Accept;
import library.nettoffice.com.restapi.Get;
import library.nettoffice.com.restapi.Header;
import library.nettoffice.com.restapi.MediaType;
import library.nettoffice.com.restapi.Post;

/**
 * Created by Deo-chainmeans on 2017/3/21.
 */
@Post(url = "http://neetcrashflight.appspot.com/cp/searchProject",
        converters = MappingJackson2HttpMessageConverter.class,
        headers = {
                @Header(name = "User-Id", value = "5629499534213120"),
                @Header(name = "User-Key", value = "bIo2kNYVg4-VwZHHZtyliaq8G-ZBRkph")
        })
@Accept(contentType = MediaType.APPLICATION_JSON)
public class Request {
}
