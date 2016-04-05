package library.neetoffice.com.neetdao.neetdao;

import android.content.Context;
import android.database.Cursor;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Deo on 2016/3/4.
 */
public abstract class DatabaseHelper  {
    private SQLiteHelper sqLiteHelper;
    private final String password;

    public DatabaseHelper(Context context, String name,String password, int version, Class<?>... modelClasses) {
        this.password = password;
        sqLiteHelper = new SQLiteHelper(context, name, version, modelClasses);
    }

    public <E> Dao<E> getDao(Class<E> modelClass) {
        return new DaoImpl<>(sqLiteHelper.getWritableDatabase(password), modelClass);
    }
}
