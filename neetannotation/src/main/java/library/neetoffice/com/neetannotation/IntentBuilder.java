package library.neetoffice.com.neetannotation;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Size;
import android.util.SizeF;
import android.util.SparseArray;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Mac on 2016/04/04.
 */
public abstract class IntentBuilder {
    private final Bundle bundle = new Bundle();

    public final Bundle bundle() {
        Class<?> a = getClass();
        final HashMap<String, Field> m = new HashMap<>();
        while (!IntentBuilder.class.equals(a)) {
            final Field[] b = a.getDeclaredFields();
            for (Field c : b) {
                final Extra d = c.getAnnotation(Extra.class);
                if (d != null) {
                    String f;
                    if ("".equals(d.value())) {
                        f = c.getName();
                    } else {
                        f = d.value();
                    }
                    if (!m.containsKey(f)) {
                        m.put(f, c);
                    }
                }
            }
            a = a.getSuperclass();
        }

        final Set<Map.Entry<String, Field>> s = m.entrySet();
        for (Map.Entry<String, Field> p : s) {
            final String n = p.getKey();
            final Field f = p.getValue();
            if (bundle.containsKey(n)) {
                continue;
            }
            final Object o;
            try {
                o = AnnotationUtil.get(f, this);
            } catch (IllegalAccessException e) {
                continue;
            }
            if (o == null) {
                continue;
            }
            if (o instanceof Boolean) {
                putBoolean(n, (Boolean) o);
            } else if (o instanceof Byte) {
                putByte(n, (Byte) o);
            } else if (o instanceof Character) {
                putChar(n, (Character) o);
            } else if (o instanceof Short) {
                putShort(n, (Short) o);
            } else if (o instanceof Integer) {
                putInt(n, (Integer) o);
            } else if (o instanceof Long) {
                putLong(n, (Long) o);
            } else if (o instanceof Float) {
                putFloat(n, (Float) o);
            } else if (o instanceof Double) {
                putDouble(n, (Double) o);
            } else if (o instanceof String) {
                putString(n, (String) o);
            } else if (o instanceof CharSequence) {
                putCharSequence(n, (CharSequence) o);
            } else if (o instanceof Size) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    putSize(n, (Size) o);
                }
            } else if (o instanceof SizeF) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    putSizeF(n, (SizeF) o);
                }
            } else if (o instanceof Parcelable) {
                putParcelable(n, (Parcelable) o);
            } else if (o instanceof Parcelable[]) {
                putParcelableArray(n, (Parcelable[]) o);
            } else if (o instanceof boolean[]) {
                putBooleanArray(n, (boolean[]) o);
            } else if (o instanceof byte[]) {
                putByteArray(n, (byte[]) o);
            } else if (o instanceof short[]) {
                putShortArray(n, (short[]) o);
            } else if (o instanceof char[]) {
                putCharArray(n, (char[]) o);
            } else if (o instanceof int[]) {
                putIntArray(n, (int[]) o);
            } else if (o instanceof long[]) {
                putLongArray(n, (long[]) o);
            } else if (o instanceof float[]) {
                putFloatArray(n, (float[]) o);
            } else if (o instanceof double[]) {
                putDoubleArray(n, (double[]) o);
            } else if (o instanceof String[]) {
                putStringArray(n, (String[]) o);
            } else if (o instanceof CharSequence[]) {
                putCharSequenceArray(n, (CharSequence[]) o);
            } else if (o instanceof ArrayList) {
                System.out.println("ArrayList");
                final Class<?> t = getParameterizedType(f);
                if (Parcelable.class.isAssignableFrom(t)) {
                    putParcelableArrayList(n, (ArrayList<? extends Parcelable>) o);
                } else if (Integer.class.isAssignableFrom(t)) {
                    putIntegerArrayList(n, (ArrayList<Integer>) o);
                } else if (String.class.isAssignableFrom(t)) {
                    putStringArrayList(n, (ArrayList<String>) o);
                } else if (CharSequence.class.isAssignableFrom(t)) {
                    putCharSequenceArrayList(n, (ArrayList<CharSequence>) o);
                }
            } else if (o instanceof SparseArray) {
                final Class<?> t = getParameterizedType(f);
                if (Parcelable.class.isAssignableFrom(t)) {
                    putSparseParcelableArray(n, (SparseArray<? extends Parcelable>) o);
                }
            } else if (o instanceof Serializable) {
                putSerializable(n, (Serializable) o);
            }
        }

        return bundle;
    }

    public void putBoolean(@Nullable String key, boolean value) {
        bundle.putBoolean(key, value);
    }

    public void putByte(@Nullable String key, byte value) {
        bundle.putByte(key, value);
    }

    public void putChar(@Nullable String key, char value) {
        bundle.putChar(key, value);
    }

    public void putShort(@Nullable String key, short value) {
        bundle.putShort(key, value);
    }

    public void putInt(@Nullable String key, int value) {
        bundle.putInt(key, value);
    }

    public void putLong(@Nullable String key, long value) {
        bundle.putLong(key, value);
    }

    public void putFloat(@Nullable String key, float value) {
        bundle.putFloat(key, value);
    }

    public void putDouble(@Nullable String key, double value) {
        bundle.putDouble(key, value);
    }

    public void putString(@Nullable String key, @Nullable String value) {
        bundle.putString(key, value);
    }

    public void putCharSequence(@Nullable String key, @Nullable CharSequence value) {
        bundle.putCharSequence(key, value);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void putSize(@Nullable String key, @Nullable Size value) {
        bundle.putSize(key, value);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void putSizeF(@Nullable String key, @Nullable SizeF value) {
        bundle.putSizeF(key, value);
    }

    public void putParcelable(@Nullable String key, @Nullable Parcelable value) {
        bundle.putParcelable(key, value);
    }

    public void putParcelableArray(@Nullable String key, @Nullable Parcelable[] value) {
        bundle.putParcelableArray(key, value);
    }

    public void putBooleanArray(@Nullable String key, @Nullable boolean[] value) {
        bundle.putBooleanArray(key, value);
    }

    public void putByteArray(@Nullable String key, @Nullable byte[] value) {
        bundle.putByteArray(key, value);
    }

    public void putShortArray(@Nullable String key, @Nullable short[] value) {
        bundle.putShortArray(key, value);
    }

    public void putCharArray(@Nullable String key, @Nullable char[] value) {
        bundle.putCharArray(key, value);
    }

    public void putIntArray(@Nullable String key, @Nullable int[] value) {
        bundle.putIntArray(key, value);
    }

    public void putLongArray(@Nullable String key, @Nullable long[] value) {
        bundle.putLongArray(key, value);
    }

    public void putFloatArray(@Nullable String key, @Nullable float[] value) {
        bundle.putFloatArray(key, value);
    }

    public void putDoubleArray(@Nullable String key, @Nullable double[] value) {
        bundle.putDoubleArray(key, value);
    }

    public void putStringArray(@Nullable String key, @Nullable String[] value) {
        bundle.putStringArray(key, value);
    }

    public void putCharSequenceArray(@Nullable String key, @Nullable CharSequence[] value) {
        bundle.putCharSequenceArray(key, value);
    }

    public void putParcelableArrayList(@Nullable String key, @Nullable ArrayList<? extends Parcelable> value) {
        bundle.putParcelableArrayList(key, value);
    }

    public void putIntegerArrayList(@Nullable String key, @Nullable ArrayList<Integer> value) {
        bundle.putIntegerArrayList(key, value);
    }

    public void putStringArrayList(@Nullable String key, @Nullable ArrayList<String> value) {
        bundle.putStringArrayList(key, value);
    }

    public void putCharSequenceArrayList(@Nullable String key, @Nullable ArrayList<CharSequence> value) {
        bundle.putCharSequenceArrayList(key, value);
    }

    public void putSparseParcelableArray(@Nullable String key, @Nullable SparseArray<? extends Parcelable> value) {
        bundle.putSparseParcelableArray(key, value);
    }

    public void putSerializable(@Nullable String key, @Nullable Serializable value) {
        bundle.putSerializable(key, value);
    }

    public void putAll(Bundle extras) {
        bundle.putAll(extras);
    }


    /**
     * {@hide}
     */
    private final static Class<?> getParameterizedType(Field f) {
        final Type fc = f.getGenericType();
        if (fc != null && fc instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) fc;
            Type[] types = pt.getActualTypeArguments();
            if (types != null && types.length > 0) {
                return (Class<?>) types[0];
            }
        }
        return null;
    }

    public void startActivity(@NonNull final Context context, @NonNull final Class<? extends Activity> cls) {
        final Intent intent = new Intent(context, cls);
        intent.putExtras(bundle());
        context.startActivity(intent);
    }

    public void startActivity(@NonNull final Context context, @NonNull final String action) {
        final Intent intent = new Intent(action);
        intent.putExtras(bundle());
        context.startActivity(intent);
    }

    public void startActivityForResult(@NonNull Activity activity, @NonNull Class<? extends Activity> cls, int requestCode) {
        final Intent intent = new Intent(activity, cls);
        intent.putExtras(bundle());
        activity.startActivityForResult(intent, requestCode);
    }

    public void startActivityForResult(@NonNull Activity activity, @NonNull String action, int requestCode) {
        final Intent intent = new Intent(action);
        intent.putExtras(bundle());
        activity.startActivityForResult(intent, requestCode);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void startActivityForResult(@NonNull android.app.Fragment fragment, @NonNull Class<? extends Activity> cls, int requestCode) {
        final Intent intent = new Intent(fragment.getActivity(), cls);
        intent.putExtras(bundle());
        fragment.startActivityForResult(intent, requestCode);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void startActivityForResult(@NonNull android.app.Fragment fragment, @NonNull String action, int requestCode) {
        final Intent intent = new Intent(action);
        intent.putExtras(bundle());
        fragment.startActivityForResult(intent, requestCode);
    }

    public void startActivityForResult(@NonNull android.support.v4.app.Fragment fragment, @NonNull Class<? extends Activity> cls, int requestCode) {
        final Intent intent = new Intent(fragment.getActivity(), cls);
        intent.putExtras(bundle());
        fragment.startActivityForResult(intent, requestCode);
    }

    public void startActivityForResult(@NonNull android.support.v4.app.Fragment fragment, @NonNull String action, int requestCode) {
        final Intent intent = new Intent(action);
        intent.putExtras(bundle());
        fragment.startActivityForResult(intent, requestCode);
    }

    public void setResult(@NonNull Activity activity) {
        final Intent intent = new Intent();
        intent.putExtras(bundle());
        activity.setResult(Activity.RESULT_OK, intent);
    }

    public void setResult(@NonNull Activity activity, int resultCode) {
        final Intent intent = new Intent();
        intent.putExtras(bundle());
        activity.setResult(Activity.RESULT_OK, intent);
    }

    public void startService(@NonNull final Context context, @NonNull Class<? extends Service> cls) {
        final Intent intent = new Intent(context, cls);
        intent.putExtras(bundle());
        context.startService(intent);
    }

    public void startService(@NonNull final Context context, @NonNull String action) {
        final Intent intent = new Intent(action);
        intent.putExtras(bundle());
        context.startService(intent);
    }

    public void bindService(@NonNull final Context context, @NonNull Class<? extends Service> cls, @NonNull ServiceConnection serviceConnection, int flags) {
        final Intent intent = new Intent(context, cls);
        intent.putExtras(bundle());
        context.bindService(intent, serviceConnection, flags);
    }

    public void sendBroadcast(@NonNull final Context context, @NonNull Class<? extends BroadcastReceiver> cls) {
        final Intent intent = new Intent(context, cls);
        intent.putExtras(bundle());
        context.sendBroadcast(intent);
    }

    public void sendBroadcast(@NonNull final Context context, @NonNull String action) {
        final Intent intent = new Intent(action);
        intent.putExtras(bundle());
        context.sendBroadcast(intent);
    }

    public void sendBroadcast(@NonNull final Context context, @NonNull Class<? extends BroadcastReceiver> cls, @Nullable String receiverPermission) {
        final Intent intent = new Intent(context, cls);
        intent.putExtras(bundle());
        context.sendBroadcast(intent, receiverPermission);
    }

    public void sendBroadcast(@NonNull final Context context, @NonNull String action, @Nullable String receiverPermission) {
        final Intent intent = new Intent(action);
        intent.putExtras(bundle());
        context.sendBroadcast(intent, receiverPermission);
    }
}
