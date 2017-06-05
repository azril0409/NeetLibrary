package library.neetoffice.com.neetannotation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Deo on 2016/3/18.
 */
public class BindSupportFragment {

    static void onCreate(Fragment a, Bundle b) {
        Class<?> c = a.getClass();
        do {
            final NFragment q = c.getAnnotation(NFragment.class);
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
                        h = "_" + c.getName();
                    }
                    if (b != null) {
                        final Object i = b.get(h);
                        try {
                            AnnotationUtil.set(g, a, i);
                        } catch (IllegalAccessException e) {
                        }
                    }
                }
            }
            c = c.getSuperclass();
        } while (c != null);
    }

    static View onCreateView(Fragment a, ViewGroup b, Bundle w) {
        Class<?> c = a.getClass();
        final NFragment d = c.getAnnotation(NFragment.class);
        final View v;
        if (d != null && d.value() != -1) {
            v = LayoutInflater.from(a.getContext()).inflate(d.value(), b, false);
        } else {
            v = new View(a.getContext());
        }
        final ArrayList<Method> j = new ArrayList<>();
        do {
            final NFragment q = c.getAnnotation(NFragment.class);
            if (q != null) {
                final Field[] f = c.getDeclaredFields();
                for (Field g : f) {
                    bindViewById(a, v, g);
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
                    BindExtra.bindArgument(a, g);
                    BindField.bindSaveInstance(a, g, w);
                    BindRestService.bind(a, g);
                }
                final Method[] h = c.getDeclaredMethods();
                final TouchListener l = new TouchListener(a);
                for (Method i : h) {
                    BindMethod.bindClick(a, v, i);
                    BindMethod.bindLongClick(a, v, i);
                    BindMethod.bindTouch(a, v, i, l);
                    BindMethod.bindTouchDown(a, v, i, l);
                    BindMethod.bindTouchMove(a, v, i, l);
                    BindMethod.bindTouchUp(a, v, i, l);
                    BindMethod.bindItemClick(a, v, i);
                    BindMethod.bindItemLongClick(a,v, i);
                    BindMethod.bindCheckedChange(a, v, i);
                    BindMethod.bindFocusChange(a, v, i);
                    BindMethod.bindTextChange(a, v, i);
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
        return v;
    }

    static void bindViewById(Fragment a, View b, Field c) {
        final ViewById d = c.getAnnotation(ViewById.class);
        if (d == null) {
            return;
        }
        try {
            final View f = b.findViewById(FindResources.id(a.getActivity(), d.value(), c));
            if (f != null) {
                AnnotationUtil.set(c, a, f);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    static void onSaveInstanceState(Fragment a, Bundle b) {
        Class<?> c = a.getClass();
        do {
            final NFragment q = c.getAnnotation(NFragment.class);
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
}
