package library.nettoffice.com.restapi;

import android.os.AsyncTask;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by Deo-chainmeans on 2017/3/22.
 */

class Task<Request, Response> extends AsyncTask<Request, Void, Response> {
    private final RestApiHelp restApiHelp;
    private final ResponseCallBack<Response> responseCallBack;

    Task(RestApiHelp restApiHelp, ResponseCallBack<Response> responseCallBack) {
        this.restApiHelp = restApiHelp;
        this.responseCallBack = responseCallBack;
    }

    @Override
    protected Response doInBackground(Request... params) {
        return restApiHelp.<Request, Response>request(params[0], new Reference<Response>(responseCallBack.getType()));
    }

    @Override
    protected void onPostExecute(Response responseEntity) {
        if (responseCallBack != null) {
            responseCallBack.onResponse(responseEntity);
        }
    }
}
