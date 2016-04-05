package library.neetoffice.com.neetannotation;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Deo on 2016/3/18.
 */
abstract class BindField {

    static void bindBean(Object a, Field b, Context c) {
        final Bean d = b.getAnnotation(Bean.class);
        if (d == null) {
            return;
        }
        final Class<?> f = b.getType();
        Object h;
        try {
            final Constructor g = f.getDeclaredConstructor(new Class[]{Context.class});
            h = g.newInstance(c);
        } catch (NoSuchMethodException e) {
            try {
                final Constructor g = f.getDeclaredConstructor(new Class[0]);
                h = g.newInstance();
            } catch (NoSuchMethodException e1) {
                throw new BindExcetion(b.getType() + " neet  no-arg or Context parameter");
            } catch (InvocationTargetException e1) {
                throw new BindExcetion(b.getType() + " neet  no-arg or Context parameter");
            } catch (InstantiationException e1) {
                throw new BindExcetion(b.getType() + " neet  no-arg or Context parameter");
            } catch (IllegalAccessException e1) {
                throw new BindExcetion(b.getType() + " neet  no-arg or Context parameter");
            }
        } catch (InvocationTargetException e) {
            throw new BindExcetion(b.getType() + " neet  no-arg or Context parameter");
        } catch (InstantiationException e) {
            throw new BindExcetion(b.getType() + " neet  no-arg or Context parameter");
        } catch (IllegalAccessException e) {
            throw new BindExcetion(b.getType() + " neet  no-arg or Context parameter");
        }
        try {
            b.setAccessible(true);
            b.set(a, h);
        } catch (IllegalAccessException e) {
        }
        final Field[] i = f.getDeclaredFields();
        for (Field j : i) {
            bindBean(h, j, c);
            bindRootContext(h, j, c);
        }
    }

    static void bindRootContext(Object a, Field b, Context c) {
        final RootContext d = b.getAnnotation(RootContext.class);
        if (d == null) {
            return;
        }
        final Class<?> f = b.getType();
        if (f.isInstance(Context.class)) {
            try {
                b.setAccessible(true);
                b.set(a, c);
            } catch (IllegalAccessException e) {
            }
        } else {
            throw new BindExcetion(b.getType() + " type is not Context");
        }
    }

    static void bindSaveInstance(Object a, Field b, Bundle w) {
        if (w == null) {
            return;
        }
        final SaveInstance d = b.getAnnotation(SaveInstance.class);
        if (d == null) {
            return;
        }
        final String h;
        if (d.value().length() > 0) {
            h = d.value();
        } else {
            h = "_" + b.getName();
        }
        final Object i = w.get(h);
        if (i != null) {
            try {
                b.set(a, i);
            } catch (IllegalAccessException e) {
            }
        }
    }
}
