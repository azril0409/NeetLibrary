package library.neetoffice.com.neetannotation;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Mac on 2016/04/04.
 */
public class IntentBundle {
    private final Context context;
    private final Intent intent = new Intent();

    IntentBundle(Context context) {
        this.context = context;
    }

    public Intent intent() {
        return intent;
    }

    public IntentBundle addCategory(String category) {
        intent.addCategory(category);
        return this;
    }

    public IntentBundle setType(String type) {
        intent.setType(type);
        return this;
    }

    public IntentBundle setType(int flags) {
        intent.setFlags(flags);
        return this;
    }

    public IntentBundle addFlags(int flags) {
        intent.addFlags(flags);
        return this;
    }

    public IntentBundle putExtra(String name, boolean value) {
        intent.putExtra(name, value);
        return this;
    }

    public IntentBundle putExtra(String name, byte value) {
        intent.putExtra(name, value);
        return this;
    }

    public IntentBundle putExtra(String name, char value) {
        intent.putExtra(name, value);
        return this;
    }

    public IntentBundle putExtra(String name, short value) {
        intent.putExtra(name, value);
        return this;
    }

    public IntentBundle putExtra(String name, int value) {
        intent.putExtra(name, value);
        return this;
    }

    public IntentBundle putExtra(String name, long value) {
        intent.putExtra(name, value);
        return this;
    }

    public IntentBundle putExtra(String name, float value) {
        intent.putExtra(name, value);
        return this;
    }

    public IntentBundle putExtra(String name, double value) {
        intent.putExtra(name, value);
        return this;
    }

    public IntentBundle putExtra(String name, String value) {
        intent.putExtra(name, value);
        return this;
    }

    public IntentBundle putExtra(String name, CharSequence value) {
        intent.putExtra(name, value);
        return this;
    }

    public IntentBundle putExtra(String name, Parcelable value) {
        intent.putExtra(name, value);
        return this;
    }

    public IntentBundle putExtra(String name, Parcelable[] value) {
        intent.putExtra(name, value);
        return this;
    }

    public IntentBundle putExtra(String name, ArrayList<? extends Parcelable> value) {
        intent.putExtra(name, value);
        return this;
    }

    public IntentBundle putExtra(String name, Serializable value) {
        intent.putExtra(name, value);
        return this;
    }

    public IntentBundle putExtra(String name, boolean[] value) {
        intent.putExtra(name, value);
        return this;
    }

    public IntentBundle putExtra(String name, byte[] value) {
        intent.putExtra(name, value);
        return this;
    }

    public IntentBundle putExtra(String name, short[] value) {
        intent.putExtra(name, value);
        return this;
    }

    public IntentBundle putExtra(String name, char[] value) {
        intent.putExtra(name, value);
        return this;
    }

    public IntentBundle putExtra(String name, int[] value) {
        intent.putExtra(name, value);
        return this;
    }

    public IntentBundle putExtra(String name, long[] value) {
        intent.putExtra(name, value);
        return this;
    }

    public IntentBundle putExtra(String name, float[] value) {
        intent.putExtra(name, value);
        return this;
    }

    public IntentBundle putExtra(String name, double[] value) {
        intent.putExtra(name, value);
        return this;
    }

    public IntentBundle putExtra(String name, String[] value) {
        intent.putExtra(name, value);
        return this;
    }

    @TargetApi(Build.VERSION_CODES.FROYO)
    public IntentBundle putExtra(String name, CharSequence[] value) {
        intent.putExtra(name, value);
        return this;
    }

    public IntentBundle putExtra(String name, Bundle value) {
        intent.putExtra(name, value);
        return this;
    }

    public IntentBundle putExtras(Intent src) {
        intent.putExtras(src);
        return this;
    }

    public IntentBundle putExtras(Bundle extras) {
        intent.putExtras(extras);
        return this;
    }

    public IntentBundle replaceExtras(Intent src) {
        intent.replaceExtras(src);
        return this;
    }

    public IntentBundle replaceExtras(Bundle extras) {
        intent.replaceExtras(extras);
        return this;
    }

    public void startActivity(Class<? extends Activity> cls) {
        intent.setClass(context, cls);
        context.startActivity(intent);
    }

    public void startActivity(String action) {
        intent.setAction(action);
        context.startActivity(intent);
    }

    public void startActivityForResult(Activity activity, Class<? extends Activity> cls, int requestCode) {
        intent.setClass(activity, cls);
        activity.startActivityForResult(intent, requestCode);
    }

    public void startActivityForResult(Activity activity, String action, int requestCode) {
        intent.setAction(action);
        activity.startActivityForResult(intent, requestCode);
    }

    public void setResult(Activity activity) {
        activity.setResult(Activity.RESULT_OK, intent);
    }

    public void startService(Class<? extends Service> cls) {
        intent.setClass(context, cls);
        context.startService(intent);
    }

    public void startService(String action) {
        intent.setAction(action);
        context.startService(intent);
    }

    public void bindService(Class<? extends Service> cls, ServiceConnection serviceConnection, int flags) {
        intent.setClass(context, cls);
        context.bindService(intent, serviceConnection, flags);
    }

    public void sendBroadcast(Class<? extends BroadcastReceiver> cls) {
        intent.setClass(context, cls);
        context.sendBroadcast(intent);
    }

    public void sendBroadcast(String action) {
        intent.setAction(action);
        context.sendBroadcast(intent);
    }

    public void sendBroadcast(Class<? extends BroadcastReceiver> cls, @Nullable String receiverPermission) {
        intent.setClass(context, cls);
        context.sendBroadcast(intent, receiverPermission);
    }

    public void sendBroadcast(String action, @Nullable String receiverPermission) {
        intent.setAction(action);
        context.sendBroadcast(intent, receiverPermission);
    }
}
