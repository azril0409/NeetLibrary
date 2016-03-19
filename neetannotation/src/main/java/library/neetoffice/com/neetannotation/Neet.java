package library.neetoffice.com.neetannotation;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Deo on 2016/3/8.
 */
public class Neet {

    public static void onCreate(Activity activity, Bundle savedInstanceState) {
        BindActivity.onCreate(activity);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static void onCreate(android.app.Fragment fragment, Bundle savedInstanceState) {
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static View onCreateView(android.app.Fragment fragment, ViewGroup container) {
        return BindFragment.onCreateView(fragment, container);
    }

    public static void onCreate(android.support.v4.app.Fragment fragment, Bundle savedInstanceState) {
    }

    public static View onCreateView(android.support.v4.app.Fragment fragment, ViewGroup container) {
        return BindFragmentv4.onCreateView(fragment, container);
    }
}
