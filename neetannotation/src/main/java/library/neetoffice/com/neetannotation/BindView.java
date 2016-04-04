package library.neetoffice.com.neetannotation;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Deo on 2016/4/1.
 */
abstract class BindView {
    static void onCreate(ViewGroup a) {
        final Class<?> c = a.getClass();
        final NViewGroup d = c.getAnnotation(NViewGroup.class);
        if (d != null && d.value() != -1) {
            ViewGroup.inflate(a.getContext(),d.value(),a);
        }
        final Field[] f = c.getDeclaredFields();
        for (Field g : f) {
            bindViewById(a, g);
            BindField.bindBean(a, g, a.getContext());
            BindField.bindRootContext(a, g, a.getContext());
        }
        final Method[] h = c.getDeclaredMethods();
        for (Method i : h) {
            BindMethod.bindClick(a, a, i);
            BindMethod.bindLongClick(a, a, i);
            BindMethod.bindTouch(a, a, i);
            BindMethod.bindItemClick(a, a, i);
            BindMethod.bindCheckedChange(a, a, i);
        }
    }

    private static void bindViewById(ViewGroup a, Field b) {
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
}
