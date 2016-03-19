package library.neetoffice.com.neetannotation;

import android.app.Fragment;
import android.view.View;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Created by Deo on 2016/3/17.
 */
class NeetOnClickListener implements View.OnClickListener {
    final Object a;
    final Method b;
    final int d;

    public NeetOnClickListener(Object a, Method b) throws BindExcetion {
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
            } else {
                throw new BindExcetion(b.getName() + " neet  no-arg or View parameter");
            }
        } else {
            throw new BindExcetion(b.getName() + " neet  no-arg or View parameter");
        }
    }

    @Override
    public void onClick(View v) {
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
        }
    }
}
