package library.neetoffice.com.neetdao;

import android.content.ContentValues;
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

    void beginTransaction();

    void commitTransaction();

    QueryBuilder<E> queryBuilder();

    int count();

    int delete();

    long insert(E entity);

    long insertOrReplace(E entity);

    int update(E entity);

    E load();

    List<E> loadAll();
}
