package library.neetoffice.com.neetannotation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Deo on 2016/3/18.
 */
abstract class BindActivity {

    static void onCreate(Activity a) {
        final Class<?> c = a.getClass();
        final NActivity d = c.getAnnotation(NActivity.class);
        if (d != null && d.value() != -1) {
            a.setContentView(d.value());
            final Field[] f = c.getDeclaredFields();
            for (Field g : f) {
                bindViewById(a, g);
                BindField.bindBean(a, g, a);
                BindField.bindRootContext(a, g, a);
                bindExtra(a, g);
            }
            final Method[] h = c.getDeclaredMethods();
            for (Method i : h) {
                BindMethod.bindClick(a, i);
                BindMethod.bindLongClick(a, i);
                BindMethod.bindTouch(a, i);
                BindMethod.bindItemClick(a, i);
            }
        }
    }

    private static void bindViewById(Activity a, Field b) {
        final ViewById c = b.getAnnotation(ViewById.class);
        if (c == null) {
            return;
        }
        final View d = a.findViewById(c.value());
        if (d != null) {
            try {
                b.setAccessible(true);
                b.set(a, d);
            } catch (IllegalAccessException e) {
            }
        }
    }

    private static void bindExtra(Activity a, Field b) {
        final Extra c = b.getAnnotation(Extra.class);
        if (c == null) {
            return;
        }
        final String d = c.value();
        final Intent e = a.getIntent();
        if (e == null) {
            return;
        }
        final Bundle f = e.getExtras();
        if (f == null) {
            return;
        }
        final Object g = f.get(d);
        try {
            b.setAccessible(true);
            b.set(a, g);
        } catch (IllegalAccessException e1) {
        }
    }
}
