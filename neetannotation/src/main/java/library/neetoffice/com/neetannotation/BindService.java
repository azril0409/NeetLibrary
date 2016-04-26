package library.neetoffice.com.neetannotation;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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
                    BindField.bindResString(a, h, a.getResources());
                    BindField.bindResStringArray(a, h, a.getResources());
                    BindField.bindResBoolean(a, h, a.getResources());
                    BindField.bindResDimen(a, h, a.getResources());
                    BindField.bindResInteger(a, h, a.getResources());
                    BindField.bindResColor(a, h, a.getResources(), a.getTheme());
                    BindField.bindResDrawable(a, h, a.getResources(), a.getTheme());
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
        final Class<?>[] d = j.getParameterTypes();
        final Object[] t = new Object[d.length];
        for (int i = 0; i < d.length; i++) {
            final Class<?> f = d[i];
            final Extra g = f.getAnnotation(Extra.class);
            if (f == Context.class) {
                t[i] = a;
            } else if (f == Intent.class) {
                t[i] = c;
            } else if (f == Bundle.class) {
                t[i] = c.getExtras();
            } else if (g != null) {
                t[i] = c.getExtras().get(g.value());
            } else {
                throw new BindExcetion(j.getName() + " neet  contex or Intent or Bundle or @Extra parameter");
            }
        }
        try {
            j.invoke(a, t);
        } catch (IllegalAccessException e) {
        } catch (InvocationTargetException e) {
        }
    }
}
