package library.neetoffice.com.neetannotation;

import android.os.Looper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Deo on 2016/4/1.
 */
public abstract class EventBus {
    private static final HashMap<Object, HashMap<Class<?>, Method>> MAP = new HashMap<>();

    public static void register(Object subscriber) {
        synchronized (MAP) {
            final Class<?> c = subscriber.getClass();
            final Method[] a = c.getDeclaredMethods();
            final HashMap<Class<?>, Method> hashMap = new HashMap<>();
            for (Method b : a) {
                final Subscribe d = b.getAnnotation(Subscribe.class);
                if (d != null) {
                    continue;
                }
                final Class<?>[] e = b.getParameterTypes();
                if (e.length != 1) {
                    throw new BindExcetion(b.getName() + " neet one parameter");
                }
                hashMap.put(e[0], b);
            }
            MAP.put(subscriber, hashMap);
        }
    }

    public static void unregister(Object subscriber) {
        synchronized (MAP) {
            MAP.remove(subscriber);
        }
    }

    public static void post(Object event) {
        final Class<?> c = event.getClass();
        synchronized (MAP) {
            for (Object object : MAP.keySet()) {
                final HashMap<Class<?>, Method> hashMap = MAP.get(object);
                if (MAP.containsKey(c)) {
                    final Method method = hashMap.get(c);
                    try {
                        method.invoke(object, event);
                    } catch (IllegalAccessException e) {
                    } catch (InvocationTargetException e) {
                    }
                }
            }
        }
    }
}
