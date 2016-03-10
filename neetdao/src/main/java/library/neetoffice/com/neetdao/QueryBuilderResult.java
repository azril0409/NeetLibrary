package library.neetoffice.com.neetdao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Deo on 2016/3/7.
 */
public class QueryBuilderResult<E> {
    private final SQLiteDatabase db;
    private final Class modelClass;
    private final HashMap<String, Field> map;
    protected String selection;
    protected String orderBy;

    protected QueryBuilderResult(SQLiteDatabase db, Class modelClass) {
        this.db = db;
        this.modelClass = modelClass;
        map = new HashMap<>();
        for (Field field : modelClass.getDeclaredFields()) {
            try {
                final String name = DatabaseHelper.getColumnName(field);
                map.put(name, field);
            }catch (NeetSQLException e){}
        }
    }

    public int count() {
        final Cursor cursor = db.query(DatabaseHelper.getTable(modelClass), null, selection, null, null, null, orderBy);
        return cursor.getCount();
    }

    public List<E> list() {
        final ArrayList<E> list = new ArrayList<>();
        final Cursor cursor = db.query(DatabaseHelper.getTable(modelClass), null, selection, null, null, null, orderBy);
        final int count = cursor.getCount();
        if (count > 0 && cursor.moveToFirst()) {
            do {
                try {
                    list.add(getObject(cursor));
                } catch (InstantiationException e) {
                } catch (IllegalAccessException e) {
                }
            } while (cursor.moveToNext());
        }
        return list;
    }

    public E one() {
        final Cursor cursor = db.query(DatabaseHelper.getTable(modelClass), null, selection, null, null, null, orderBy);
        final int count = cursor.getCount();
        if (count > 0 && cursor.moveToFirst()) {
            try {
                return getObject(cursor);
            } catch (InstantiationException e) {
            } catch (IllegalAccessException e) {
            }
        }
        return null;
    }

    public int delete() {
        return db.delete(DatabaseHelper.getTable(modelClass), selection, null);
    }

    private E getObject(Cursor cursor) throws IllegalAccessException, InstantiationException {
        final Object object = modelClass.newInstance();
        for (String columnName : map.keySet()) {
            final Field field = map.get(columnName);
            field.setAccessible(true);
            final int index = cursor.getColumnIndex(columnName);
            if (field.getType() == Boolean.class) {
                field.set(object, cursor.getInt(index) > 0);
            } else if (field.getType() == boolean.class) {
                field.set(object, cursor.getInt(index) > 0);
            } else if (field.getType() == Short.class) {
                field.set(object, cursor.getShort(index));
            } else if (field.getType() == short.class) {
                field.set(object, cursor.getShort(index));
            } else if (field.getType() == Integer.class) {
                field.set(object, cursor.getInt(index));
            } else if (field.getType() == int.class) {
                field.set(object, cursor.getInt(index));
            } else if (field.getType() == Float.class) {
                field.set(object, cursor.getFloat(index));
            } else if (field.getType() == float.class) {
                field.set(object, cursor.getFloat(index));
            } else if (field.getType() == Double.class) {
                field.set(object, cursor.getDouble(index));
            } else if (field.getType() == double.class) {
                field.set(object, cursor.getDouble(index));
            } else if (field.getType() == Long.class) {
                field.set(object, cursor.getLong(index));
            } else if (field.getType() == long.class) {
                field.set(object, cursor.getLong(index));
            } else if (field.getType() == byte[].class) {
                field.set(object, cursor.getBlob(index));
            } else if (field.getType() == char[].class) {
                field.set(object, cursor.getString(index).toCharArray());
            } else if (field.getType() == String.class) {
                field.set(object, cursor.getString(index));
            }
        }
        return (E) object;
    }
}
