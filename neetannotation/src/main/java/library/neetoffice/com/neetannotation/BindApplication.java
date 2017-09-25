package library.neetoffice.com.neetannotation;

import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by Deo on 2016/4/11.
 */
public class BindApplication {
    static void onCreate(Application a) {
        Class<?> d = a.getClass();
        final ArrayList<Method> j = new ArrayList<>();
        do {
            final NApplication f = d.getAnnotation(NApplication.class);
            if (f != null) {
                final Field[] g = d.getDeclaredFields();
                for (Field h : g) {
                    BindField.bindBean(a, h, a);
                    BindField.bindRootContext(a, h, a);
                    BindField.bindApp(a, h, a);
                    BindField.bindResString(a, h, a);
                    BindField.bindResStringArray(a, h, a);
                    BindField.bindResBoolean(a, h, a);
                    BindField.bindResDimen(a, h, a);
                    BindField.bindResInteger(a, h, a);
                    BindField.bindResColor(a, h, a, a.getTheme());
                    BindField.bindResDrawable(a, h, a, a.getTheme());
                    BindField.bindSharedPreferences(a, h, a);
                    BindField.bindHandler(a, h, a);
                    BindField.bindSystemService(a, h, a);
                    BindRestService.bind(a, h);
                }
                final Method[] h = d.getDeclaredMethods();
                for (Method i : h) {
                    if (BindMethod.isAfterAnnotationMethod(i)) {
                        j.add(i);
                    }
                }
            }
            d = d.getSuperclass();
        } while (d != null);
        for (int i = j.size() - 1; i >= 0; i--) {
            try {
                final Method k = j.get(i);
                AnnotationUtil.invoke(k, a);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
