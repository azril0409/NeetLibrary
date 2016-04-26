package library.neetoffice.com.neetannotation;

import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by Deo on 2016/3/18.
 */
public class BindFragmentv4 {

    static void onCreate(Fragment a, Bundle b) {
        Class<?> c = a.getClass();
        do {
            final NFragment q = c.getAnnotation(NFragment.class);
            if (q != null) {
                final Field[] f = c.getDeclaredFields();
                for (Field g : f) {
                    final SaveInstance d = c.getAnnotation(SaveInstance.class);
                    if (d == null) {
                        continue;
                    }
                    final String h;
                    if (d.value().length() > 0) {
                        h = d.value();
                    } else {
                        h = "_" + c.getName();
                    }
                    final Object i = b.get(h);
                    if (f != null) {
                        try {
                            g.set(a, i);
                        } catch (IllegalAccessException e) {
                        }
                    }
                }
            }
            c = c.getSuperclass();
        } while (c != null);
    }

    static View onCreateView(Fragment a, ViewGroup b, Bundle w) {
        Class<?> c = a.getClass();
        final NFragment d = c.getAnnotation(NFragment.class);
        final View e;
        if (d != null && d.value() != -1) {
            e = LayoutInflater.from(a.getContext()).inflate(d.value(), b, false);
        } else {
            e = new View(a.getContext());
        }
        do {
            final NFragment q = c.getAnnotation(NFragment.class);
            if (q != null) {
                final Field[] f = c.getDeclaredFields();
                for (Field g : f) {
                    bindViewById(a, e, g);
                    BindField.bindBean(a, g, a.getContext());
                    BindField.bindRootContext(a, g, a.getContext());
                    BindField.bindResString(a, g, a.getResources());
                    BindField.bindResStringArray(a, g, a.getResources());
                    BindField.bindResBoolean(a, g, a.getResources());
                    BindField.bindResDimen(a, g, a.getResources());
                    BindField.bindResInteger(a, g, a.getResources());
                    BindField.bindResColor(a, g, a.getResources(), a.getContext().getTheme());
                    BindField.bindResDrawable(a, g, a.getResources(), a.getContext().getTheme());
                    BindField.bindResAnimation(a, g, a.getContext());
                    BindField.bindResLayoutAnimation(a, g, a.getContext());
                    bindArgument(a, g);
                    BindField.bindSaveInstance(a, g, w);
                }
                final Method[] h = c.getDeclaredMethods();
                final NeetTouchListener l = new NeetTouchListener(a);
                for (Method i : h) {
                    BindMethod.bindClick(a, e, i);
                    BindMethod.bindLongClick(a, e, i);
                    BindMethod.bindTouch(a, e, i,l);
                    BindMethod.bindTouchDown(a, e, i, l);
                    BindMethod.bindTouchMove(a, e, i, l);
                    BindMethod.bindTouchUp(a, e, i, l);
                    BindMethod.bindItemClick(a, e, i);
                    BindMethod.bindCheckedChange(a, e, i);
                    BindMethod.bindFocusChange(a, e, i);
                    BindMethod.bindTextChange(a, e, i);
                }
            }
            c = c.getSuperclass();
        } while (c != null);
        return e;
    }

    static void bindViewById(Fragment a, View b, Field c) {
        final ViewById d = c.getAnnotation(ViewById.class);
        if (d == null) {
            return;
        }
        final View f = b.findViewById(d.value());
        if (f != null) {
            try {
                c.setAccessible(true);
                c.set(a, f);
            } catch (IllegalAccessException e) {
            }
        }
    }

    static void bindArgument(Fragment a, Field b) {
        final Argument c = b.getAnnotation(Argument.class);
        if (c == null) {
            return;
        }
        final Bundle d = a.getArguments();
        final String f = c.value();
        final Object g = d.get(f);
        try {
            b.setAccessible(true);
            b.set(a, g);
        } catch (IllegalAccessException e) {
        }
    }

    static void onSaveInstanceState(Fragment a, Bundle b) {
        Class<?> c = a.getClass();
        do {
            final NFragment q = c.getAnnotation(NFragment.class);
            if (q != null) {
                final Field[] f = c.getDeclaredFields();
                for (Field g : f) {
                    final SaveInstance d = g.getAnnotation(SaveInstance.class);
                    if (d == null) {
                        continue;
                    }
                    final String h;
                    if (d.value().length() > 0) {
                        h = d.value();
                    } else {
                        h = "_" + g.getName();
                    }
                    try {
                        final Object i = g.get(a);
                        if (g.getType() == byte.class) {
                            b.putByte(h, (byte) i);
                        } else if (g.getType() == Byte.class) {
                            b.putByte(h, (Byte) i);
                        } else if (g.getType() == byte[].class) {
                            b.putByteArray(h, (byte[]) i);
                        } else if (g.getType() == boolean.class) {
                            b.putBoolean(h, (boolean) i);
                        } else if (g.getType() == Boolean.class) {
                            b.putBoolean(h, (Boolean) i);
                        } else if (g.getType() == boolean[].class) {
                            b.putBooleanArray(h, (boolean[]) i);
                        } else if (g.getType() == short.class) {
                            b.putShort(h, (short) i);
                        } else if (g.getType() == Short.class) {
                            b.putShort(h, (Short) i);
                        } else if (g.getType() == short[].class) {
                            b.putShortArray(h, (short[]) i);
                        } else if (g.getType() == int.class) {
                            b.putInt(h, (int) i);
                        } else if (g.getType() == Integer.class) {
                            b.putInt(h, (Integer) i);
                        } else if (g.getType() == int[].class) {
                            b.putIntArray(h, (int[]) i);
                        } else if (g.getType() == long.class) {
                            b.putLong(h, (long) i);
                        } else if (g.getType() == Long.class) {
                            b.putLong(h, (Long) i);
                        } else if (g.getType() == long[].class) {
                            b.putLongArray(h, (long[]) i);
                        } else if (g.getType() == float.class) {
                            b.putFloat(h, (float) i);
                        } else if (g.getType() == Float.class) {
                            b.putFloat(h, (Float) i);
                        } else if (g.getType() == float[].class) {
                            b.putFloatArray(h, (float[]) i);
                        } else if (g.getType() == double.class) {
                            b.putDouble(h, (double) i);
                        } else if (g.getType() == Double.class) {
                            b.putDouble(h, (Double) i);
                        } else if (g.getType() == double[].class) {
                            b.putDoubleArray(h, (double[]) i);
                        } else if (g.getType() == char.class) {
                            b.putChar(h, (char) i);
                        } else if (g.getType() == Character.class) {
                            b.putChar(h, (Character) i);
                        } else if (g.getType() == char[].class) {
                            b.putCharArray(h, (char[]) i);
                        } else if (g.getType() == String.class) {
                            b.putString(h, (String) i);
                        } else if (g.getType() == String[].class) {
                            b.putStringArray(h, (String[]) i);
                        } else if (g.getType() == CharSequence.class) {
                            b.putCharSequence(h, (CharSequence) i);
                        } else if (g.getType() == CharSequence[].class) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
                                b.putCharSequenceArray(h, (CharSequence[]) i);
                            }
                        } else if (Parcelable.class.isAssignableFrom(g.getType())) {
                            b.putParcelable(h, (Parcelable) i);
                        } else if (Parcelable[].class.isAssignableFrom(g.getType())) {
                            b.putParcelableArray(h, (Parcelable[]) i);
                        } else if (g.getType() == ArrayList.class) {
                            b.putSerializable(h, (ArrayList<?>) i);
                        }
                    } catch (IllegalAccessException e) {
                    }
                }
            }
            c = c.getSuperclass();
        } while (c != null);
    }
}
