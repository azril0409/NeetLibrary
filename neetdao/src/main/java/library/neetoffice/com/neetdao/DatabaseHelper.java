package library.neetoffice.com.neetdao;

import android.content.Context;

/**
 * Created by Mac on 2016/04/04.
 */
public class DatabaseHelper {
    private SQLiteHelper sqLiteHelper;

    public DatabaseHelper(Context context, String name, int version, Class<?>... modelClasses) {
        sqLiteHelper = new SQLiteHelper(context, name, version, modelClasses);
    }

    public <E> Dao<E> getDao(Class<E> modelClass) {
        return new DaoImpl<E>(sqLiteHelper, modelClass);
    }
}
