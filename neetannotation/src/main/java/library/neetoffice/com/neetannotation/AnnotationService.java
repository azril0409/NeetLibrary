package library.neetoffice.com.neetannotation;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;

/**
 * Created by Deo-chainmeans on 2017/9/8.
 */
@NBean
public abstract class AnnotationService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        BindService.onCreate(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        BindService.onStartCommand(this, intent);
        return super.onStartCommand(intent, flags, startId);
    }
}
