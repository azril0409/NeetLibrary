package library.neetoffice.com.neetannotation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Deo on 2016/3/18.
 */
public class BindFragmentv4 {

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
        final View e;
        if (d != null && d.value() != -1) {
            e = LayoutInflater.from(a.getContext()).inflate(d.value(), b, false);
        } else {
            e = new View(a.getContext());
        }
        do {
            final NFragment q = c.getAnnotation(NFragment.class);
            if (q != null) {
                final Field[] f = c.getDeclaredFields();
                for (Field g : f) {
                    bindViewById(a, e, g);
                    BindField.bindBean(a, g, a.getContext());
                    BindField.bindRootContext(a, g, a.getContext());
                    BindField.bindResString(a, g, a.getContext());
                    BindField.bindResStringArray(a, g, a.getContext());
                    BindField.bindResBoolean(a, g, a.getContext());
                    BindField.bindResDimen(a, g, a.getContext());
                    BindField.bindResInteger(a, g, a.getContext());
                    BindField.bindResColor(a, g, a.getContext(), a.getContext().getTheme());
                    BindField.bindResDrawable(a, g, a.getContext(), a.getContext().getTheme());
                    BindField.bindResAnimation(a, g, a.getContext());
                    BindField.bindResLayoutAnimation(a, g, a.getContext());
                    bindArgument(a, g);
                    BindField.bindSaveInstance(a, g, w);
                }
                final Method[] h = c.getDeclaredMethods();
                final TouchListener l = new TouchListener(a);
                for (Method i : h) {
                    BindMethod.bindClick(a, e, i);
                    BindMethod.bindLongClick(a, e, i);
                    BindMethod.bindTouch(a, e, i, l);
                    BindMethod.bindTouchDown(a, e, i, l);
                    BindMethod.bindTouchMove(a, e, i, l);
                    BindMethod.bindTouchUp(a, e, i, l);
                    BindMethod.bindItemClick(a, e, i);
                    BindMethod.bindCheckedChange(a, e, i);
                    BindMethod.bindFocusChange(a, e, i);
                    BindMethod.bindTextChange(a, e, i);
                }
            }
            c = c.getSuperclass();
        } while (c != null);
        return e;
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

    static void bindArgument(Fragment a, Field b) {
        final Argument c = b.getAnnotation(Argument.class);
        if (c == null) {
            return;
        }
        final Bundle d = a.getArguments();
        final String f = c.value();
        final Object g = d.get(f);
        try {
            AnnotationUtil.set(b, a, g);
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
