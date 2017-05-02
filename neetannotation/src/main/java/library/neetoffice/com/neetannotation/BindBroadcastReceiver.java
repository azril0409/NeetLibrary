package library.neetoffice.com.neetannotation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Deo on 2016/4/6.
 */
abstract class BindBroadcastReceiver {
    static void onReceive(@NonNull BroadcastReceiver a, Context b, Intent c) {
        Class<?> d = a.getClass();
        do {
            final NReceiver f = d.getAnnotation(NReceiver.class);
            if (f != null) {
                final Field[] g = d.getDeclaredFields();
                for (Field h : g) {
                    BindField.bindBean(a, h, b);
                    BindField.bindRootContext(a, h, b);
                    BindField.bindApp(a, h, b);
                    BindField.bindResString(a, h, b);
                    BindField.bindResStringArray(a, h, b);
                    BindField.bindResBoolean(a, h, b);
                    BindField.bindResDimen(a, h, b);
                    BindField.bindResInteger(a, h, b);
                    BindField.bindResColor(a, h, b, b.getTheme());
                    BindField.bindResDrawable(a, h, b, b.getTheme());
                    BindField.bindResAnimation(a, h, b);
                    BindField.bindResLayoutAnimation(a, h, b);
                }
                if (c != null) {
                    final Method[] i = d.getDeclaredMethods();
                    for (Method j : i) {
                        bindReceiverAction(a, b, j, c);
                    }
                }
            }
            d = d.getSuperclass();
        } while (d != null);
    }

    private static void bindReceiverAction(@NonNull BroadcastReceiver a, @NonNull Context p, @NonNull Method j, @NonNull Intent c) {
        final ReceiverAction b = j.getAnnotation(ReceiverAction.class);
        if (b == null) {
            return;
        }
        final String action = c.getAction();
        if (!action.equals(b.value())) {
            return;
        }
        final Class<?>[] d = j.getParameterTypes();
        final Object[] t = new Object[d.length];
        for (int i = 0; i < d.length; i++) {
            final Class<?> f = d[i];
            final Extra g = f.getAnnotation(Extra.class);
            if (f == Context.class) {
                t[i] = p;
            } else if (f == Intent.class) {
                t[i] = c;
            } else if (f == Bundle.class) {
                t[i] = c.getExtras();
            } else if (g != null) {
                t[i] = c.getExtras().get(g.value());
            } else {
                throw new AnnotationException(j.getName() + " neet  contex or Intent or Bundle or @Extra parameter");
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
