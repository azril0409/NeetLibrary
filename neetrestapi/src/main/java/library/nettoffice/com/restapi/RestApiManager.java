package library.nettoffice.com.restapi;

import android.content.Context;
import android.os.Handler;

import org.springframework.core.GenericTypeResolver;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Method;

/**
 * Created by Deo-chainmeans on 2017/3/21.
 */

public class RestApiManager {
    public static final RestApiManager getInstance(Context context) {
        return new RestApiManager(context);
    }

    private final Context context;
    private final RestApiHelp restApiHelp = new RestApiHelp();

    private RestApiManager(Context context) {
        this.context = context;
    }


    public <Request, Response> Response request(Request request, ParameterizedTypeReference<Response> typeReference) {
        return restApiHelp.request(request, new Reference<Response>(typeReference.getType()));
    }

    public <Request, Response> void request(final Request request, final ResponseCallBack<Response> responseCallBack) {
        new Task<Request, Response>(restApiHelp, responseCallBack).execute(request);
    }
}
