package library.neetoffice.com.neetannotation;

import android.app.Activity;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toolbar;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Deo-chainmeans on 2017/3/22.
 */

abstract class BindMenu {
    static void bindMenu(Activity a) {
        final OptionsMenu b = a.getClass().getAnnotation(OptionsMenu.class);
        if (b == null) {
            return;
        }
        View c = null;
        if (b.toolbarId() != -1) {
            c = a.findViewById(b.toolbarId());
        }
        if (a instanceof AppCompatActivity && c != null && c instanceof android.support.v7.widget.Toolbar) {
            ((AppCompatActivity) a).setSupportActionBar((android.support.v7.widget.Toolbar) c);
        } else if (a instanceof AppCompatActivity && c != null && c instanceof Toolbar) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ((AppCompatActivity) a).setActionBar((Toolbar) c);
            }
        } else if (c != null && c instanceof Toolbar) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                a.setActionBar((Toolbar) c);
            }
        }
    }

    static boolean onCreateOptionsMenu(Activity a, Menu b) {
        final OptionsMenu c = a.getClass().getAnnotation(OptionsMenu.class);
        if (c == null) {
            return true;
        }
        int d = c.value();
        if (d > 0) {
            a.getMenuInflater().inflate(d, b);
        }
        return true;
    }

    static boolean onOptionsItemSelected(Activity a, MenuItem b) {
        Class<?> c = a.getClass();
        Method j = null;
        do {
            final NActivity q = c.getAnnotation(NActivity.class);
            if (q != null) {
                final Method[] f = c.getDeclaredMethods();
                for (Method d : f) {
                    final OptionsItem g = d.getAnnotation(OptionsItem.class);
                    if (g == null) {
                        continue;
                    }
                    for (int i : g.value()) {
                        if (i == b.getItemId()) {
                            j = d;
                            break;
                        }
                    }
                }
            }
            c = c.getSuperclass();
        } while (c != null);
        if (j != null) {
            final Class<?>[] k = j.getParameterTypes();
            if (k.length == 0) {
                try {
                    AnnotationUtil.invoke(j, a);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                return true;
            } else if (k.length == 1 && MenuItem.class.isAssignableFrom(k[0])) {
                try {
                    AnnotationUtil.invoke(j, a, b);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                return true;
            }
        }
        return false;
    }
}
