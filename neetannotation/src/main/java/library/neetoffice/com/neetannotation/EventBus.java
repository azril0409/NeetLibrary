package library.neetoffice.com.neetannotation;

import android.os.Handler;
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
    private static final Handler HANDLER = new Handler();
    private static final HashMap<Object, HashMap<Class<?>, Method>> MAP = new HashMap<>();
    private static final HashMap<Object, HashMap<Class<?>, Method>> BACKGROUND = new HashMap<>();

    public static void register(Object subscriber) {
        synchronized (MAP) {
            synchronized (BACKGROUND) {
                final Class<?> c = subscriber.getClass();
                final Method[] a = c.getDeclaredMethods();
                final HashMap<Class<?>, Method> hashMap = new HashMap<>();
                final HashMap<Class<?>, Method> hashMap2 = new HashMap<>();
                for (Method b : a) {
                    final Subscribe d = b.getAnnotation(Subscribe.class);
                    if (d != null) {
                        final Class<?>[] e = b.getParameterTypes();
                        if (e.length != 1) {
                            throw new BindExcetion(b.getName() + " neet one parameter");
                        }
                        hashMap.put(e[0], b);
                    }
                    final Subscribe f = b.getAnnotation(Subscribe.class);
                    if (f != null) {
                        final Class<?>[] e = b.getParameterTypes();
                        if (e.length != 1) {
                            throw new BindExcetion(b.getName() + " neet one parameter");
                        }
                        hashMap2.put(e[0], b);
                    }
                }
                BACKGROUND.put(subscriber, hashMap);
                MAP.put(subscriber, hashMap2);
            }
        }
    }

    public static void unregister(Object subscriber) {
        synchronized (MAP) {
            synchronized (BACKGROUND) {
                MAP.remove(subscriber);
                BACKGROUND.remove(subscriber);
            }
        }
    }

    public static void post(Object event) {
        HANDLER.post(new Task(BACKGROUND,event));
    }

    public static void postOnBackground(Object event) {
        new Thread(new Task(BACKGROUND,event)).start();
    }

    private static class Task implements Runnable {
        final Object event;
        final HashMap<Object, HashMap<Class<?>, Method>> map;

        private Task(HashMap<Object, HashMap<Class<?>, Method>> map, Object event) {
            this.event = event;
            this.map = map;
        }


        @Override
        public void run() {
            synchronized (MAP) {
                synchronized (BACKGROUND) {
                    final Class<?> c = event.getClass();
                    for (Object object : map.keySet()) {
                        final HashMap<Class<?>, Method> hashMap = map.get(object);
                        if (map.containsKey(c)) {
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
    }
}
