package library.neetoffice.com.neetannotation;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Deo on 2016/4/11.
 */
public class BindService {
    static void onStartCommand(Service a, Intent b) {
        Class<?> d = a.getClass();
        do {
            final NService f = d.getAnnotation(NService.class);
            if (f != null) {
                final Field[] g = d.getDeclaredFields();
                for (Field h : g) {
                    BindField.bindBean(a, h, a);
                    BindField.bindRootContext(a, h, a);
                    BindField.bindResString(a, h, a);
                    BindField.bindResStringArray(a, h, a);
                    BindField.bindResBoolean(a, h, a);
                    BindField.bindResDimen(a, h, a);
                    BindField.bindResInteger(a, h, a);
                    BindField.bindResColor(a, h, a, a.getTheme());
                    BindField.bindResDrawable(a, h, a, a.getTheme());
                    BindField.bindSharedPreferences(a, h, a);
                    BindRestService.bind(a, h);
                }
                if (b != null) {
                    final Method[] i = d.getDeclaredMethods();
                    for (Method j : i) {
                        bindStartAction(a, j, b);
                    }
                }
            }
            d = d.getSuperclass();
        } while (d != null);
    }

    private static void bindStartAction(@NonNull Service a, @NonNull Method j, @NonNull Intent c) {
        final StartAction b = j.getAnnotation(StartAction.class);
        if (b == null) {
            return;
        }
        final String action = c.getAction();
        final String methodAction = b.value();
        if (action.length() > 0 && action.equals(methodAction)) {
        } else if (action.length() == 0 && methodAction.length() == 0) {
        } else {
            return;
        }
        final Annotation[][] v = j.getParameterAnnotations();
        final Class<?>[] d = j.getParameterTypes();
        final Object[] t = new Object[d.length];
        for (int i = 0; i < d.length; i++) {
            final Class<?> f = d[i];
            if (f == Context.class) {
                t[i] = a;
            } else if (f == Intent.class) {
                t[i] = c;
            } else if (f == Bundle.class && c.getExtras() != null) {
                t[i] = c.getExtras();
            } else if (c.getExtras() != null) {
                final StartAction.Extra u = BindMethod.findParameterAnnotation(v[i], StartAction.Extra.class);
                if (u != null) {
                    t[i] = c.getExtras().get(u.value());
                }
            }
        }
        try {
            AnnotationUtil.invoke(j, a, t);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
