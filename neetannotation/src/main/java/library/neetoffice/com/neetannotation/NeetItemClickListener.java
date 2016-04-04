package library.neetoffice.com.neetannotation;

import android.view.View;
import android.widget.AdapterView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Created by Deo on 2016/3/18.
 */
class NeetItemClickListener implements AdapterView.OnItemClickListener {
    final Object a;
    final Method b;
    final int d;
    final Class<?> f;

    NeetItemClickListener(Object a, Method b) throws BindExcetion {
        this.a = a;
        this.b = b;
        if (b.getModifiers() != Modifier.PUBLIC) {
            throw new BindExcetion("@library.neetoffice.com.neetannotation.LongClick cannot be used on a non public element");
        }
        final Class<?>[] c = b.getParameterTypes();
        if (c.length == 1) {
            d = 0;
            f = c[0];
        } else if (c.length == 2) {
            if (View.class.isAssignableFrom(c[0])) {
                d = 1;
                f = c[1];
            } else if (View.class.isAssignableFrom(c[1])) {
                d = 2;
                f = c[0];
            } else {
                throw new BindExcetion(b.getName() + " neet  no-arg or View parameter");
            }
        } else {
            throw new BindExcetion(b.getName() + " neet  no-arg or View parameter");
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final Object o = parent.getItemAtPosition(position);
        if (d == 0) {
            if (f == int.class) {
                try {
                    b.invoke(a, position);
                } catch (IllegalAccessException e) {
                } catch (InvocationTargetException e) {
                }
            } else if (o != null && f.isAssignableFrom(o.getClass())) {
                try {
                    b.invoke(a, o);
                } catch (IllegalAccessException e) {
                } catch (InvocationTargetException e) {
                }
            }
        } else if (d == 1) {
            if (f == int.class) {
                try {
                    b.invoke(a, parent, position);
                } catch (IllegalAccessException e) {
                } catch (InvocationTargetException e) {
                }
            } else if (o != null && o.getClass().isInstance(f)) {
                try {
                    b.invoke(a, parent, o);
                } catch (IllegalAccessException e) {
                } catch (InvocationTargetException e) {
                }
            }
        } else if (d == 2) {
            if (f == int.class) {
                try {
                    b.invoke(a, position, parent);
                } catch (IllegalAccessException e) {
                } catch (InvocationTargetException e) {
                }
            } else if (o != null && o.getClass().isInstance(f)) {
                try {
                    b.invoke(a, o, parent);
                } catch (IllegalAccessException e) {
                } catch (InvocationTargetException e) {
                }
            }
        }
    }
}
