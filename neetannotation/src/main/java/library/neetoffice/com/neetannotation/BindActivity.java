package library.neetoffice.com.neetannotation;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

/**
 * Created by Deo on 2016/3/18.
 */
abstract class BindActivity {

    static void onCreate(Activity a, Bundle b) {
        Class<?> c = a.getClass();
        final NActivity d = c.getAnnotation(NActivity.class);
        if (d != null && d.value() != -1) {
            a.setContentView(d.value());
        }
        do {
            final NActivity q = c.getAnnotation(NActivity.class);
            if (q != null) {
                final Field[] f = c.getDeclaredFields();
                for (Field g : f) {
                    bindViewById(a, g);
                    BindField.bindBean(a, g, a);
                    BindField.bindRootContext(a, g, a);
                    BindField.bindResString(a, g, a.getResources());
                    BindField.bindResStringArray(a, g, a.getResources());
                    BindField.bindResBoolean(a, g, a.getResources());
                    BindField.bindResDimen(a, g, a.getResources());
                    BindField.bindResInteger(a, g, a.getResources());
                    BindField.bindResColor(a, g, a.getResources(), a.getTheme());
                    BindField.bindResDrawable(a, g, a.getResources(), a.getTheme());
                    BindField.bindResAnimation(a, g, a);
                    BindField.bindResLayoutAnimation(a, g, a);
                    bindExtra(a, g);
                    if (b != null) {
                        bindSaveInstance(a, b, g);
                    }
                }
                final Method[] h = c.getDeclaredMethods();
                final NeetTouchListener l = new NeetTouchListener(a);
                for (Method i : h) {
                    BindMethod.bindClick(a, i);
                    BindMethod.bindLongClick(a, i);
                    BindMethod.bindTouch(a, i, l);
                    BindMethod.bindTouchDown(a, i, l);
                    BindMethod.bindTouchMove(a, i, l);
                    BindMethod.bindTouchUp(a, i, l);
                    BindMethod.bindItemClick(a, i);
                    BindMethod.bindCheckedChange(a, i);
                    BindMethod.bindFocusChange(a, i);
                    BindMethod.bindTextChange(a, i);
                }
            }
            c = c.getSuperclass();
        } while (c != null);
    }

    private static void bindViewById(Activity a, Field b) {
        final ViewById c = b.getAnnotation(ViewById.class);
        if (c == null) {
            return;
        }
        final View d = a.findViewById(c.value());
        if (d != null) {
            try {
                b.setAccessible(true);
                b.set(a, d);
            } catch (IllegalAccessException e) {
            }
        }
    }

    private static void bindExtra(Activity a, Field b) {
        final Extra c = b.getAnnotation(Extra.class);
        if (c == null) {
            return;
        }
        final String d = c.value();
        final Intent e = a.getIntent();
        if (e == null) {
            return;
        }
        final Bundle f = e.getExtras();
        if (f == null) {
            return;
        }
        final Object g = f.get(d);
        try {
            b.setAccessible(true);
            b.set(a, g);
        } catch (IllegalAccessException e1) {
        }
    }

    private static void bindSaveInstance(Activity a, Bundle b, Field c) {
        final SaveInstance d = c.getAnnotation(SaveInstance.class);
        if (d == null) {
            return;
        }
        final String h;
        if (d.value().length() > 0) {
            h = d.value();
        } else {
            h = "_" + c.getName();
        }
        final Object f = b.get(h);
        if (f != null) {
            try {
                c.set(a, f);
            } catch (IllegalAccessException e) {
            }
        }
    }

    static void onSaveInstanceState(Activity a, Bundle b) {
        Class<?> c = a.getClass();
        do {
            final NActivity q = c.getAnnotation(NActivity.class);
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
