package library.neetoffice.com.neetannotation;

import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;

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
        final ArrayList<Method> j = new ArrayList<>();
        do {
            final NViewGroup q = c.getAnnotation(NViewGroup.class);
            if (q != null) {
                final Field[] f = c.getDeclaredFields();
                for (Field g : f) {
                    bindViewById(a, g);
                    BindField.bindBean(a, g, a.getContext());
                    BindField.bindRootContext(a, g, a.getContext());
                    BindField.bindApp(a, g, a.getContext());
                    BindField.bindResString(a, g, a.getContext());
                    BindField.bindResStringArray(a, g, a.getContext());
                    BindField.bindResBoolean(a, g, a.getContext());
                    BindField.bindResDimen(a, g, a.getContext());
                    BindField.bindResInteger(a, g, a.getContext());
                    BindField.bindResColor(a, g, a.getContext(), a.getContext().getTheme());
                    BindField.bindResDrawable(a, g, a.getContext(), a.getContext().getTheme());
                    BindField.bindResAnimation(a, g, a.getContext());
                    BindField.bindResLayoutAnimation(a, g, a.getContext());
                    BindField.bindSharedPreferences(a, g, a.getContext());
                    BindField.bindHandler(a, g, a.getContext());
                    BindField.bindSystemService(a, g, a.getContext());
                    BindRestService.bind(a, g);
                }
                final Method[] h = c.getDeclaredMethods();
                final TouchListener l = new TouchListener(a);
                for (Method i : h) {
                    BindMethod.bindClick(a, a, i);
                    BindMethod.bindLongClick(a, a, i);
                    BindMethod.bindTouch(a, a, i, l);
                    BindMethod.bindTouchDown(a, a, i, l);
                    BindMethod.bindTouchMove(a, a, i, l);
                    BindMethod.bindTouchUp(a, a, i, l);
                    BindMethod.bindItemClick(a, a, i);
                    BindMethod.bindLongClick(a, a, i);
                    BindMethod.bindCheckedChange(a, a, i);
                    BindMethod.bindFocusChange(a, a, i);
                    BindMethod.bindTextChange(a, a, i);
                    if (BindMethod.isAfterAnnotationMethod(i)) {
                        j.add(i);
                    }
                }
            }
            c = c.getSuperclass();
        } while (c != null);
        for (int i = j.size() - 1; i >= 0; i--) {
            try {
                final Method k = j.get(i);
                AnnotationUtil.invoke(k, a);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private static void bindViewById(ViewGroup a, Field b) {
        final ViewById c = b.getAnnotation(ViewById.class);
        if (c == null) {
            return;
        }
        try {
            final View d = a.findViewById(FindResources.id(a.getContext(), c.value(), b));
            if (d != null) {
                AnnotationUtil.set(b, a, d);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
