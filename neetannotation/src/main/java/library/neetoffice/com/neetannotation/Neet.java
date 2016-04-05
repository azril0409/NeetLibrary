package library.neetoffice.com.neetannotation;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

    public static void onActivityResult(@NonNull Activity activity, int requestCode, Intent data) {
        BindMethod.onActivityResult(activity, requestCode, data);
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
    public static void onActivityResult(@NonNull android.app.Fragment fragment, int requestCode, Intent data) {
        BindMethod.onActivityResult(fragment, requestCode, data);
    }

    public static View onCreateView(@NonNull android.support.v4.app.Fragment fragment, ViewGroup container, Bundle savedInstanceState) {
        return BindFragmentv4.onCreateView(fragment, container, savedInstanceState);
    }

    public static void onSaveInstanceState(@NonNull android.support.v4.app.Fragment fragment, Bundle outState) {
        BindFragmentv4.onSaveInstanceState(fragment, outState);
    }

    public static void onActivityResult(@NonNull android.support.v4.app.Fragment fragment, int requestCode, Intent data) {
        BindMethod.onActivityResult(fragment, requestCode, data);
    }

    public static void onCreate(@NonNull ViewGroup view) {
        BindView.onCreate(view);
    }

    public static IntentBundle newIntent(Context context) {
        return new IntentBundle(context);
    }

    public static void main(String[] args) {
        Class<?> cls = B.class.getSuperclass();
        System.out.println("cls = " + cls.getName());
        Class<?> cls2 = cls.getSuperclass();
        System.out.println("cls2 = " + cls2.getName());
        Class<?> cls3 = cls2.getSuperclass();
        System.out.println("cls3 = " + (cls3 == null ? "is null" : "is not null"));
    }

    ;

    public static class A {

    }

    public static class B extends A {

    }
}
