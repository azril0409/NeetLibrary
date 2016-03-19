package library.neetoffice.com.neetannotation;

import android.app.Fragment;
import android.view.View;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Created by Deo on 2016/3/17.
 */
public class NeetLongClickListener implements View.OnLongClickListener {
    final Object a;
    final Method b;
    final int d;

    public NeetLongClickListener(Object a, Method b) throws BindExcetion {
        this.a = a;
        this.b = b;
        if (b.getModifiers() != Modifier.PUBLIC) {
            throw new BindExcetion("@library.neetoffice.com.neetannotation.LongClick cannot be used on a non public element");
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
    public boolean onLongClick(View v) {
        if (d == 0) {
            try {
                if (b.getReturnType() == void.class) {
                    b.invoke(a);
                } else if (b.getReturnType() == boolean.class) {
                    Object e = b.invoke(a);
                    return (boolean) e;
                } else if (b.getReturnType() == Boolean.class) {
                    Object e = b.invoke(a);
                    return (boolean) e;
                }
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
        } else if (d == 1) {
            try {
                if (b.getReturnType() == void.class) {
                    b.invoke(a, v);
                } else if (b.getReturnType() == boolean.class) {
                    Object e = b.invoke(a, v);
                    return (boolean) e;
                } else if (b.getReturnType() == Boolean.class) {
                    Object e = b.invoke(a, v);
                    return (boolean) e;
                }
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
        }
        return false;
    }

}
