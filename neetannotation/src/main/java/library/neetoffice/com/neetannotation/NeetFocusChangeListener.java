package library.neetoffice.com.neetannotation;

import android.view.View;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Created by Deo on 2016/4/6.
 */
public class NeetFocusChangeListener implements View.OnFocusChangeListener {
    final Object a;
    final Method b;
    final int d;

    public NeetFocusChangeListener(Object a, Method b) {
        this.a = a;
        this.b = b;
        if (b.getModifiers() != Modifier.PUBLIC) {
            throw new BindExcetion("@library.neetoffice.com.neetannotation.Click cannot be used on a non public element");
        }
        final Class<?>[] c = b.getParameterTypes();
        if (c.length == 0) {
            d = 0;
        } else if (c.length == 1) {
            if (View.class.isAssignableFrom(c[0])) {
                d = 1;
            } else if (c[0] == boolean.class) {
                d = 2;
            } else if (c[0] == Boolean.class) {
                d = 2;
            } else {
                throw new BindExcetion(b.getName() + " neet  () or (View) or (boolean) or (View,boolean) or (boolean,View) parameter");
            }
        } else if (c.length == 2) {
            if (View.class.isAssignableFrom(c[0]) && (c[1] == boolean.class || c[1] == Boolean.class)) {
                d = 3;
            } else if (View.class.isAssignableFrom(c[1]) && (c[0] == boolean.class || c[0] == Boolean.class)) {
                d = 4;
            } else {
                throw new BindExcetion(b.getName() + " neet  () or (View) or (boolean) or (View,boolean) or (boolean,View) parameter");
            }
        } else {
            throw new BindExcetion(b.getName() + " neet  () or (View) or (boolean) or (View,boolean) or (boolean,View) parameter");
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (d == 0) {
            try {
                b.invoke(a);
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
        } else if (d == 1) {
            try {
                b.invoke(a, v);
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
        } else if (d == 2) {
            try {
                b.invoke(a, hasFocus);
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
        } else if (d == 3) {
            try {
                b.invoke(a, v, hasFocus);
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
        } else if (d == 4) {
            try {
                b.invoke(a, hasFocus, v);
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
        }
    }
}