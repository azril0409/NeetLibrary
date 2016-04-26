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
        Class<?> c = a.getClass();
        final NViewGroup d = c.getAnnotation(NViewGroup.class);
        if (d != null && d.value() != -1) {
            ViewGroup.inflate(a.getContext(), d.value(), a);
        }
        do {
            final NViewGroup q = c.getAnnotation(NViewGroup.class);
            if (q != null) {
                final Field[] f = c.getDeclaredFields();
                for (Field g : f) {
                    bindViewById(a, g);
                    BindField.bindBean(a, g, a.getContext());
                    BindField.bindRootContext(a, g, a.getContext());
                    BindField.bindResString(a, g, a.getResources());
                    BindField.bindResStringArray(a, g, a.getResources());
                    BindField.bindResBoolean(a, g, a.getResources());
                    BindField.bindResDimen(a, g, a.getResources());
                    BindField.bindResInteger(a, g, a.getResources());
                    BindField.bindResColor(a, g, a.getResources(), a.getContext().getTheme());
                    BindField.bindResDrawable(a, g, a.getResources(), a.getContext().getTheme());
                    BindField.bindResAnimation(a, g, a.getContext());
                    BindField.bindResLayoutAnimation(a, g, a.getContext());
                }
                final Method[] h = c.getDeclaredMethods();
                final NeetTouchListener l = new NeetTouchListener(a);
                for (Method i : h) {
                    BindMethod.bindClick(a, a, i);
                    BindMethod.bindLongClick(a, a, i);
                    BindMethod.bindTouch(a, a, i, l);
                    BindMethod.bindTouchDown(a, a, i, l);
                    BindMethod.bindTouchMove(a, a, i, l);
                    BindMethod.bindTouchUp(a, a, i, l);
                    BindMethod.bindItemClick(a, a, i);
                    BindMethod.bindCheckedChange(a, a, i);
                    BindMethod.bindFocusChange(a, a, i);
                    BindMethod.bindTextChange(a, a, i);
                }
            }
            c = c.getSuperclass();
        } while (c != null);
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
