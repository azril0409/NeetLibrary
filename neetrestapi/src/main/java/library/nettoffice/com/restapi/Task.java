package library.nettoffice.com.restapi;

import android.os.AsyncTask;

import org.springframework.http.ResponseEntity;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by Deo-chainmeans on 2017/3/22.
 */

class Task extends AsyncTask<RestBuild, Throwable, Object> {
    private final RestApiRequester restApiHelp;
    private final ResponseCallBack responseCallBack;
    private final Type type;

    Task(RestApiRequester restApiHelp, ResponseCallBack responseCallBack, Type type) {
        this.restApiHelp = restApiHelp;
        this.responseCallBack = responseCallBack;
        this.type = type;
    }

    @Override
    protected Object doInBackground(RestBuild... params) {
        try {
            if (ParameterizedType.class.isInstance(type) && ParameterizedType.class.cast(type).getRawType() == ResponseEntity.class) {
                return  restApiHelp.request(params[0], new Reference(((ParameterizedType)type).getActualTypeArguments()[0]));
            }
            return restApiHelp.request(params[0], new Reference(type)).getBody();
        } catch (Exception e) {
            publishProgress(e);
            return null;
        }
    }

    @Override
    protected void onProgressUpdate(Throwable... values) {
        super.onProgressUpdate(values);
        if (responseCallBack != null) {
            responseCallBack.onFailure(values[0]);
        }
    }

    @Override
    protected void onPostExecute(Object responseEntity) {
        if (responseCallBack != null && responseEntity != null) {
            responseCallBack.onResponse(responseEntity);
        }
    }
}
