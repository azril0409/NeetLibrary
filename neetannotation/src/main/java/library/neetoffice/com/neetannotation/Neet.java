package library.neetoffice.com.neetannotation;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Deo on 2016/3/8.
 */
public class Neet {

    public static void onCreate(@NonNull Activity activity, Bundle savedInstanceState) {
        BindActivity.onCreate(activity, savedInstanceState);
    }

    public static void onSaveInstanceState(@NonNull Activity activity, Bundle outState) {
        BindActivity.onSaveInstanceState(activity, outState);
    }

    public static void onActivityResult(@NonNull Activity activity, int requestCode, int resultCode, Intent data) {
        BindMethod.onActivityResult(activity, requestCode, resultCode, data);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static View onCreateView(@NonNull android.app.Fragment fragment, ViewGroup container, Bundle savedInstanceState) {
        return BindFragment.onCreateView(fragment, container, savedInstanceState);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static void onSaveInstanceState(@NonNull android.app.Fragment fragment, Bundle outState) {
        BindFragment.onSaveInstanceState(fragment, outState);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static void onActivityResult(@NonNull android.app.Fragment fragment, int requestCode, int resultCode, Intent data) {
        BindMethod.onActivityResult(fragment, requestCode, resultCode, data);
    }

    public static View onCreateView(@NonNull android.support.v4.app.Fragment fragment, ViewGroup container, Bundle savedInstanceState) {
        return BindFragmentv4.onCreateView(fragment, container, savedInstanceState);
    }

    public static void onSaveInstanceState(@NonNull android.support.v4.app.Fragment fragment, Bundle outState) {
        BindFragmentv4.onSaveInstanceState(fragment, outState);
    }

    public static void onActivityResult(@NonNull android.support.v4.app.Fragment fragment, int requestCode, int resultCode, Intent data) {
        BindMethod.onActivityResult(fragment, requestCode, resultCode, data);
    }

    public static void onCreate(@NonNull ViewGroup view) {
        BindView.onCreate(view);
    }

    public static void onReceive(@NonNull BroadcastReceiver broadcastReceiver, Context context, Intent intent) {
        BindBroadcastReceiver.onReceive(broadcastReceiver, context, intent);
    }

    public static IntentBundle newIntent(Context context) {
        return new IntentBundle(context);
    }

    public static ArgumentBundle newArgument() {
        return new ArgumentBundle();
    }

}
