package library.neetoffice.com.codeutil;

import com.google.zxing.Result;

/**
 * Created by Deo on 2016/3/22.
 */
public interface ResultCallback {
    void handleResult(Result rawResult);
}
