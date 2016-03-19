package library.neetoffice.com.neetannotation;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Deo on 2016/3/17.
 */
abstract class BindFragment {

    @TargetApi(Build.VERSION_CODES.M)
    static View onCreateView(Fragment a, ViewGroup b) {
        final Class<?> c = a.getClass();
        final NFragment d = c.getAnnotation(NFragment.class);
        if (d != null && d.value() != -1) {
            final View e = LayoutInflater.from(a.getContext()).inflate(d.value(), b, false);
            final Field[] f = c.getDeclaredFields();
            for (Field g : f) {
                bindViewById(a, e, g);
                BindField.bindBean(a, g, a.getContext());
                BindField.bindRootContext(a, g, a.getContext());
                bindArgument(a, g);
            }
            final Method[] h = c.getDeclaredMethods();
            for (Method i : h) {
                BindMethod.bindClick(a, e, i);
                BindMethod.bindLongClick(a, e, i);
                BindMethod.bindTouch(a, e, i);
                BindMethod.bindItemClick(a,e,i);
            }
            return e;
        } else {
            return new View(a.getContext());
        }
    }

    static void bindViewById(Fragment a, View b, Field c) {
        final ViewById d = c.getAnnotation(ViewById.class);
        if (d == null) {
            return;
        }
        final View f = b.findViewById(d.value());
        if (f != null) {
            try {
                c.setAccessible(true);
                c.set(a, f);
            } catch (IllegalAccessException e) {
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    static void bindArgument(Fragment a, Field b) {
        final Argument c = b.getAnnotation(Argument.class);
        if (c == null) {
            return;
        }
        final Bundle d = a.getArguments();
        final String f = c.value();
        final Object g = d.get(f);
        try {
            b.setAccessible(true);
            b.set(a, g);
        } catch (IllegalAccessException e) {
        }
    }
}
