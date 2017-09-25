package library.neetoffice.com.neetannotation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Deo on 2016/3/18.
 */
abstract class BindActivity {

    static void onCreate(Activity a, Bundle b) {
        Class<?> c = a.getClass();
        final NActivity d = c.getAnnotation(NActivity.class);
        if (d != null && d.value() != -1) {
            a.setContentView(d.value());
        }
        final ArrayList<Method> j = new ArrayList<>();
        do {
            final NActivity q = c.getAnnotation(NActivity.class);
            if (q != null) {
                final Field[] f = c.getDeclaredFields();
                for (Field g : f) {
                    bindViewById(a, g);
                    BindField.bindBean(a, g, a);
                    BindField.bindRootContext(a, g, a);
                    BindField.bindApp(a, g, a);
                    BindField.bindResString(a, g, a);
                    BindField.bindResStringArray(a, g, a);
                    BindField.bindResBoolean(a, g, a);
                    BindField.bindResDimen(a, g, a);
                    BindField.bindResInteger(a, g, a);
                    BindField.bindResColor(a, g, a, a.getTheme());
                    BindField.bindResDrawable(a, g, a, a.getTheme());
                    BindField.bindResAnimation(a, g, a);
                    BindField.bindResLayoutAnimation(a, g, a);
                    BindField.bindSharedPreferences(a,g,a);
                    BindField.bindHandler(a,g,a);
                    BindField.bindSystemService(a,g,a);
                    BindExtra.bindExtra(a, g);
                    if (b != null) {
                        bindSaveInstance(a, b, g);
                    }
                    BindMenu.bindMenu(a);
                    BindRestService.bind(a, g);
                }
                final Method[] h = c.getDeclaredMethods();
                final TouchListener l = new TouchListener(a);
                for (Method i : h) {
                    BindMethod.bindClick(a, i);
                    BindMethod.bindLongClick(a, i);
                    BindMethod.bindTouch(a, i, l);
                    BindMethod.bindTouchDown(a, i, l);
                    BindMethod.bindTouchMove(a, i, l);
                    BindMethod.bindTouchUp(a, i, l);
                    BindMethod.bindItemClick(a, i);
                    BindMethod.bindItemLongClick(a, i);
                    BindMethod.bindCheckedChange(a, i);
                    BindMethod.bindFocusChange(a, i);
                    BindMethod.bindTextChange(a, i);
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

    private static void bindViewById(Activity a, Field b) {
        final ViewById c = b.getAnnotation(ViewById.class);
        if (c == null) {
            return;
        }
        try {
            final View d = a.findViewById(FindResources.id(a, c.value(), b));
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

    private static void bindSaveInstance(Activity a, Bundle b, Field c) {
        final SaveInstance d = c.getAnnotation(SaveInstance.class);
        if (d == null) {
            return;
        }
        final String h;
        if (d.value().length() > 0) {
            h = d.value();
        } else {
            h = "_" + c.getName();
        }
        final Object f = b.get(h);
        if (f == null) {
            return;
        }
        try {
            AnnotationUtil.set(c, a, f);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    static void onSaveInstanceState(Activity a, Bundle b) {
        Class<?> c = a.getClass();
        do {
            final NActivity q = c.getAnnotation(NActivity.class);
            if (q != null) {
                final Field[] f = c.getDeclaredFields();
                for (Field g : f) {
                    final SaveInstance d = g.getAnnotation(SaveInstance.class);
                    if (d == null) {
                        continue;
                    }
                    final String h;
                    if (d.value().length() > 0) {
                        h = d.value();
                    } else {
                        h = "_" + g.getName();
                    }
                    BindBundle.addFieldToBundle(a, g, h, b);
                }
            }
            c = c.getSuperclass();
        } while (c != null);
    }

    static boolean onCreateOptionsMenu(Activity a, Menu b) {
        final boolean c = BindMenu.onCreateOptionsMenu(a, b);
        final boolean d = BindMenu.onCreateToolBarMenu(a, b);
        return c | d;
    }

    static boolean onOptionsItemSelected(Activity a, MenuItem b) {
        return BindMenu.onOptionsItemSelected(a, b);
    }
}
