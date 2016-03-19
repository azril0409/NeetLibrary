package library.neetoffice.com.neetannotation;

import android.view.MotionEvent;
import android.view.View;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Created by Deo on 2016/3/18.
 */
public class NeetTouchListener implements View.OnTouchListener {
    final Object a;
    final Method b;
    final int d;

    public NeetTouchListener(Object a, Method b) {
        this.a = a;
        this.b = b;
        if (b.getModifiers() != Modifier.PUBLIC) {
            throw new BindExcetion("@library.neetoffice.com.neetannotation.Touch cannot be used on a non public element");
        }
        final Class<?>[] c = b.getParameterTypes();
        if (c.length == 1) {
            if (MotionEvent.class.isAssignableFrom(c[0])) {
                d = 0;
            } else {
                throw new BindExcetion(b.getName() + " neet  MotionEvent or View,MotionEvent parameter");
            }
        } else if (c.length == 2) {
            if (View.class.isAssignableFrom(c[0]) && MotionEvent.class.isAssignableFrom(c[1])) {
                d = 1;
            } else if (MotionEvent.class.isAssignableFrom(c[0]) && View.class.isAssignableFrom(c[1])) {
                d = 2;
            } else {
                throw new BindExcetion(b.getName() + " neet  MotionEvent or View,MotionEvent parameter");
            }
        } else {
            throw new BindExcetion(b.getName() + " neet  MotionEvent or View,MotionEvent parameter");
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (d == 0) {
            try {
                if (b.getReturnType() == void.class) {
                    b.invoke(a, event);
                } else if (b.getReturnType() == boolean.class) {
                    Object e = b.invoke(a, event);
                    return (boolean) e;
                } else if (b.getReturnType() == Boolean.class) {
                    Object e = b.invoke(a, event);
                    return (boolean) e;
                }
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
        } else if (d == 1) {
            try {
                if (b.getReturnType() == void.class) {
                    b.invoke(a, v, event);
                } else if (b.getReturnType() == boolean.class) {
                    Object e = b.invoke(a, v, event);
                    return (boolean) e;
                } else if (b.getReturnType() == Boolean.class) {
                    Object e = b.invoke(a, v, event);
                    return (boolean) e;
                }
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
        } else if (d == 2) {
            try {
                if (b.getReturnType() == void.class) {
                    b.invoke(a, event, v);
                } else if (b.getReturnType() == boolean.class) {
                    Object e = b.invoke(a, event, v);
                    return (boolean) e;
                } else if (b.getReturnType() == Boolean.class) {
                    Object e = b.invoke(a, event, v);
                    return (boolean) e;
                }
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
        }
        return false;
    }
}
