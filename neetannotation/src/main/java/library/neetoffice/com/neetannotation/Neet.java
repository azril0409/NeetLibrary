package library.neetoffice.com.neetannotation;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
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

    public static boolean onCreateOptionsMenu(@NonNull Activity a, Menu b) {
        return BindActivity.onCreateOptionsMenu(a, b);
    }

    public static boolean onOptionsItemSelected(@NonNull Activity a, MenuItem b) {
        return BindActivity.onOptionsItemSelected(a, b);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static void onCreate(@NonNull android.app.Fragment fragment, Bundle savedInstanceState) {
        BindFragment.onCreate(fragment, savedInstanceState);
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

    public static void onCreate(@NonNull android.support.v4.app.Fragment fragment, Bundle savedInstanceState) {
        BindSupportFragment.onCreate(fragment, savedInstanceState);
    }

    public static View onCreateView(@NonNull android.support.v4.app.Fragment fragment, ViewGroup container, Bundle savedInstanceState) {
        return BindSupportFragment.onCreateView(fragment, container, savedInstanceState);
    }

    public static void onSaveInstanceState(@NonNull android.support.v4.app.Fragment fragment, Bundle outState) {
        BindSupportFragment.onSaveInstanceState(fragment, outState);
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
