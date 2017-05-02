package library.neetoffice.com.neetannotation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Deo-chainmeans on 2017/4/29.
 */

public class AnnotationBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Neet.onReceive(this, context, intent);
    }
}
