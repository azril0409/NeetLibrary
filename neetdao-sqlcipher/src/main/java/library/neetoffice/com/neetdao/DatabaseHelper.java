package library.neetoffice.com.neetdao;

import android.content.Context;

import net.sqlcipher.database.SQLiteDatabase;

/**
 * Created by Deo on 2016/3/4.
 */
public abstract class DatabaseHelper {
    private SQLiteHelper sqLiteHelper;
    private final String password;

    public DatabaseHelper(Context context, String name, String password, int version, Class<?>... modelClasses) {
        this.password = password;
        SQLiteDatabase.loadLibs(context);
        sqLiteHelper = new SQLiteHelper(context, name, password, version, modelClasses);
    }

    public <E> Dao<E> getDao(Class<E> modelClass) {
        return new DaoImpl<E>(sqLiteHelper, password, modelClass);
    }
}
