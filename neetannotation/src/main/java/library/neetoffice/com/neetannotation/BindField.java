package library.neetoffice.com.neetannotation;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/**
 * Created by Deo on 2016/3/18.
 */
abstract class BindField {
    private static final HashMap<String, Object> MAP = new HashMap<>();

    static Object newInstance(Class<?> a, Context c) {
        try {
            final Constructor g = a.getDeclaredConstructor(new Class[]{Context.class});
            g.setAccessible(true);
            return g.newInstance(c);
        } catch (NoSuchMethodException e) {
            try {
                return a.newInstance();
            } catch (InstantiationException e1) {
                throw new AnnotationException(a.getSimpleName() + " neet  no-arg or Context parameter");
            } catch (IllegalAccessException e1) {
                throw new AnnotationException(a.getSimpleName() + " neet  no-arg or Context parameter");
            }
        } catch (IllegalAccessException e) {
            throw new AnnotationException(a.getSimpleName() + " neet  no-arg or Context parameter");
        } catch (InstantiationException e) {
            throw new AnnotationException(a.getSimpleName() + " neet  no-arg or Context parameter");
        } catch (InvocationTargetException e) {
            throw new AnnotationException(a.getSimpleName() + " neet  no-arg or Context parameter");
        }
    }

    static Object newStaticInstance(Class<?> a, Context c) {
        if (MAP.containsKey(a.getName())) {
            return MAP.get(a.getName());
        } else {
            final Object b = newInstance(a, c);
            MAP.put(a.getName(), b);
            return b;
        }
    }

    static void bindBean(Object a, Field b, Context c) {
        final Bean d = b.getAnnotation(Bean.class);
        if (d == null) {
            return;
        }
        final Class<?> f = b.getType();
        Object h;
        final NBean k = f.getAnnotation(NBean.class);
        if (k != null && k.value() == Scope.Singleton) {
            h = newStaticInstance(f, c);
        } else {
            h = newInstance(f, c);
        }
        try {
            AnnotationUtil.set(b, a, h);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
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
        if (Context.class.isAssignableFrom(f)) {
            try {
                AnnotationUtil.set(b, a, c);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            throw new AnnotationException(b.getType() + " type is not Context");
        }
    }

    static void bindApp(Object a, Field b, Context c) {
        final App d = b.getAnnotation(App.class);
        if (d == null) {
            return;
        }
        final Context p = c.getApplicationContext();
        final Class<?> f = b.getType();
        if (p.getClass().isAssignableFrom(f)) {
            try {
                AnnotationUtil.set(b, a, p);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            throw new AnnotationException(b.getType() + " type is not Application");
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
                AnnotationUtil.set(b, a, i);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    static void bindResString(Object a, Field b, Context c) {
        final ResString d = b.getAnnotation(ResString.class);
        if (d == null) {
            return;
        }
        try {
            final String f = c.getString(FindResources.string(c, d, b));
            AnnotationUtil.set(b, a, f);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    static void bindResBoolean(Object a, Field b, Context c) {
        final ResBoolean d = b.getAnnotation(ResBoolean.class);
        if (d == null) {
            return;
        }
        try {
            final boolean f = c.getResources().getBoolean(FindResources.bool(c, d, b));
            AnnotationUtil.set(b, a, f);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    static void bindResDimen(Object a, Field b, Context c) {
        final ResDimen d = b.getAnnotation(ResDimen.class);
        if (d == null) {
            return;
        }
        try {
            final float f = c.getResources().getDimensionPixelSize(FindResources.dimen(c, d, b));
            AnnotationUtil.set(b, a, f);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    static void bindResInteger(Object a, Field b, Context c) {
        final ResInt d = b.getAnnotation(ResInt.class);
        if (d == null) {
            return;
        }
        try {
            final float f = c.getResources().getInteger(FindResources.integer(c, d, b));
            AnnotationUtil.set(b, a, f);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    static void bindResStringArray(Object a, Field b, Context c) {
        final ResStringArray d = b.getAnnotation(ResStringArray.class);
        if (d == null) {
            return;
        }
        try {
            final String[] f = c.getResources().getStringArray(FindResources.array(c, d, b));
            AnnotationUtil.set(b, a, f);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    static void bindResAnimation(Object a, Field b, Context c) {
        final ResAnimation d = b.getAnnotation(ResAnimation.class);
        if (d == null) {
            return;
        }
        try {
            final Animation f = AnimationUtils.loadAnimation(c, FindResources.anim(c, d, b));
            AnnotationUtil.set(b, a, f);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }


    static void bindResLayoutAnimation(Object a, Field b, Context c) {
        final ResLayoutAnimation d = b.getAnnotation(ResLayoutAnimation.class);
        if (d == null) {
            return;
        }
        try {
            final LayoutAnimationController f = AnimationUtils.loadLayoutAnimation(c, FindResources.anim(c, d, b));
            AnnotationUtil.set(b, a, f);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    static void bindResColor(Object a, Field b, Context c, Resources.Theme g) {
        final ResColor d = b.getAnnotation(ResColor.class);
        if (d == null) {
            return;
        }
        try {
            final int f;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                f = c.getResources().getColor(FindResources.color(c, d, b), g);
            } else {
                f = c.getResources().getColor(FindResources.color(c, d, b));
            }
            AnnotationUtil.set(b, a, f);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    static void bindResDrawable(Object a, Field b, Context c, Resources.Theme g) {
        final ResDrawable d = b.getAnnotation(ResDrawable.class);
        if (d == null) {
            return;
        }
        try {
            final Drawable f;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                f = c.getResources().getDrawable(FindResources.drwable(c, d, b), g);
            } else {
                f = c.getResources().getDrawable(FindResources.drwable(c, d, b));
            }
            AnnotationUtil.set(b, a, f);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
