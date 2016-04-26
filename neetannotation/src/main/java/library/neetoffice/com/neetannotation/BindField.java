package library.neetoffice.com.neetannotation;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

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

    static void bindResString(Object a, Field b, Resources c) {
        final ResString d = b.getAnnotation(ResString.class);
        if (d == null) {
            return;
        }
        final String f = c.getString(d.value());
        try {
            b.set(a, f);
        } catch (IllegalAccessException e) {
        }
    }

    static void bindResBoolean(Object a, Field b, Resources c) {
        final ResBoolean d = b.getAnnotation(ResBoolean.class);
        if (d == null) {
            return;
        }
        final boolean f = c.getBoolean(d.value());
        try {
            b.set(a, f);
        } catch (IllegalAccessException e) {
        }
    }

    static void bindResDimen(Object a, Field b, Resources c) {
        final ResDimen d = b.getAnnotation(ResDimen.class);
        if (d == null) {
            return;
        }
        final float f = c.getDimensionPixelSize(d.value());
        try {
            b.set(a, f);
        } catch (IllegalAccessException e) {
        }
    }

    static void bindResInteger(Object a, Field b, Resources c) {
        final ResInt d = b.getAnnotation(ResInt.class);
        if (d == null) {
            return;
        }
        final float f = c.getInteger(d.value());
        try {
            b.set(a, f);
        } catch (IllegalAccessException e) {
        }
    }

    static void bindResStringArray(Object a, Field b, Resources c) {
        final ResStringArray d = b.getAnnotation(ResStringArray.class);
        if (d == null) {
            return;
        }
        final String[] f = c.getStringArray(d.value());
        try {
            b.set(a, f);
        } catch (IllegalAccessException e) {
        }
    }

    static void bindResAnimation(Object a, Field b, Context c) {
        final ResAnimation d = b.getAnnotation(ResAnimation.class);
        if (d == null) {
            return;
        }
        final Animation f = AnimationUtils.loadAnimation(c, d.value());
        try {
            b.set(a, f);
        } catch (IllegalAccessException e) {
        }
    }


    static void bindResLayoutAnimation(Object a, Field b, Context c) {
        final ResLayoutAnimation d = b.getAnnotation(ResLayoutAnimation.class);
        if (d == null) {
            return;
        }
        final LayoutAnimationController f = AnimationUtils.loadLayoutAnimation(c, d.value());
        try {
            b.set(a, f);
        } catch (IllegalAccessException e) {
        }
    }

    static void bindResColor(Object a, Field b, Resources c, Resources.Theme g) {
        final ResColor d = b.getAnnotation(ResColor.class);
        if (d == null) {
            return;
        }
        final int f;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            f = c.getColor(d.value(), g);
        } else {
            f = c.getColor(d.value());
        }
        try {
            b.set(a, f);
        } catch (IllegalAccessException e) {
        }
    }

    static void bindResDrawable(Object a, Field b, Resources c, Resources.Theme g) {
        final ResDrawable d = b.getAnnotation(ResDrawable.class);
        if (d == null) {
            return;
        }
        final Drawable f;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            f = c.getDrawable(d.value(), g);
        } else {
            f = c.getDrawable(d.value());
        }
        try {
            b.set(a, f);
        } catch (IllegalAccessException e) {
        }
    }
}
