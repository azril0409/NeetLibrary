package library.neetoffice.com.neetannotation;

import android.view.View;
import android.widget.CompoundButton;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Created by Deo on 2016/4/1.
 */
class NeetCheckedChangeListener implements CompoundButton.OnCheckedChangeListener {
    final Object a;
    final Method b;
    final int d;

    NeetCheckedChangeListener(Object a, Method b) {
        this.a = a;
        this.b = b;
        if (b.getModifiers() != Modifier.PUBLIC) {
            throw new BindExcetion("@library.neetoffice.com.neetannotation.Click cannot be used on a non public element");
        }
        final Class<?>[] c = b.getParameterTypes();
        if (c.length == 1) {
            if (c[0] == boolean.class) {
                d = 0;
            } else if (c[0] == Boolean.class) {
                d = 0;
            } else {
                throw new BindExcetion(b.getName() + " neet  boolean or (View,boolean) parameter");
            }
        } else if (c.length == 2) {
            if (View.class.isAssignableFrom(c[0])) {
                d = 1;
            } else if (View.class.isAssignableFrom(c[1])) {
                d = 2;
            } else {
                throw new BindExcetion(b.getName() + " neet  boolean or (View,boolean) parameter");
            }
        } else {
            throw new BindExcetion(b.getName() + " neet  boolean or (View,boolean) parameter");
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (d == 0) {
            try {
                b.invoke(a, isChecked);
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
        } else if (d == 1) {
            try {
                b.invoke(a, buttonView, isChecked);
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
        } else if (d == 2) {
            try {
                b.invoke(a, isChecked, buttonView);
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
        }
    }
}
