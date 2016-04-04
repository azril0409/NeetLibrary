package library.neetoffice.com.neetannotation;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
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

    @TargetApi(Build.VERSION_CODES.M)
    public static void onCreate(@NonNull android.app.Fragment fragment, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            BindFragment.onCreate(fragment, savedInstanceState);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static View onCreateView(@NonNull android.app.Fragment fragment, ViewGroup container) {
        return BindFragment.onCreateView(fragment, container);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static void onSaveInstanceState(@NonNull android.app.Fragment fragment, Bundle outState) {
        BindFragment.onSaveInstanceState(fragment, outState);
    }

    public static void onCreate(@NonNull android.support.v4.app.Fragment fragment, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            BindFragmentv4.onCreate(fragment, savedInstanceState);
        }
    }

    public static View onCreateView(@NonNull android.support.v4.app.Fragment fragment, ViewGroup container) {
        return BindFragmentv4.onCreateView(fragment, container);
    }

    public static void onSaveInstanceState(@NonNull android.support.v4.app.Fragment fragment, Bundle outState) {
        BindFragmentv4.onSaveInstanceState(fragment, outState);
    }

    public static void onCreate(@NonNull ViewGroup view) {
        BindView.onCreate(view);
    }
}
