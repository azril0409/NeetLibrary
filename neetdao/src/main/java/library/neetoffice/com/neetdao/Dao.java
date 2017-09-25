package library.neetoffice.com.neetdao;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Deo on 2016/3/7.
 */
public interface Dao<E> {

    QueryBuilder<E> queryBuilder();

    int count();

    long insert(E entity);

    long insertOrReplace(E entity);

    int update(E entity);

    int delete(E entity);

    List<E> list();

    E one();

    void create() throws NeetSQLException, SQLException;

    void drop() throws NeetSQLException, SQLException;

    void beginTransaction();

    void setTransactionSuccessful();

    void endTransaction();

    void execSQL(String sql) throws SQLException;
}
