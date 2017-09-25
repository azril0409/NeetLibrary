package library.neetoffice.com.neetannotation;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.Proxy;

/**
 * Created by Deo-chainmeans on 2017/9/8.
 */

public class SharedPrefHelp {

    public static <T> T onCreate(Context context, Class<T> SharedPrefInterface) {
        final SharedPref g = SharedPrefInterface.getAnnotation(SharedPref.class);
        if (g == null) {
            return null;
        }
        final String name;
        if (g.value() == Scope.Singleton) {
            name = context.getApplicationContext().getClass().getSimpleName() + "_Pref";
        } else {
            name = context.getClass().getSimpleName() + "_Pref";
        }
        final SharedPreferences h = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        final Object i = Proxy.newProxyInstance(SharedPrefInterface.getClassLoader(), new Class<?>[]{SharedPrefInterface}, new SharedPrefInvocationHandler(context, h));
        return (T) i;
    }

}
