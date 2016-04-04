package library.neetoffice.com.neetdao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Mac on 2016/03/12.
 */
public class QueryBuilderResultImpl<E> implements QueryBuilderResult<E> {
    private final SQLiteDatabase db;
    private final Class modelClass;
    private final HashMap<String, Field> map;
    protected String selection;
    protected String orderBy;
    private int maxRows = Integer.MAX_VALUE;
    private int startRow = 0;

    protected QueryBuilderResultImpl(SQLiteDatabase db, Class modelClass) {
        this.db = db;
        this.modelClass = modelClass;
        map = new HashMap<>();
        for (Field field : modelClass.getDeclaredFields()) {
            try {
                final String name = Util.getColumnName(field);
                map.put(name, field);
            } catch (NeetSQLException e) {
            }
        }
    }

    private Cursor query() {
        if (startRow == 0 && maxRows == Integer.MAX_VALUE) {
            return db.query(Util.getTable(modelClass), null, selection, null, null, null, orderBy);
        } else {
            return db.query(Util.getTable(modelClass), null, selection, null, null, null, orderBy, startRow + "," + maxRows);
        }
    }

    @Override
    public QueryBuilderResult<E> limit(int maxRows) {
        this.maxRows = maxRows;
        return this;
    }

    @Override
    public QueryBuilderResult<E> offset(int startRow) {
        this.startRow = startRow;
        return this;
    }

    @Override
    public int count() {
        final Cursor cursor = query();
        return cursor.getCount();
    }

    @Override
    public List<E> list() {
        final ArrayList<E> list = new ArrayList<>();
        final Cursor cursor = query();
        final int count = cursor.getCount();
        final HashMap<String, Integer> columnIndexMap = new HashMap<>();
        for (String columnName : map.keySet()) {
            try {
                final int index = cursor.getColumnIndexOrThrow(columnName);
                columnIndexMap.put(columnName, index);
            } catch (IllegalArgumentException e) {
            }
        }
        if (count > 0 && cursor.moveToFirst()) {
            do {
                try {
                    list.add(getObject(columnIndexMap, cursor));
                } catch (InstantiationException e) {
                } catch (IllegalAccessException e) {
                }
            } while (cursor.moveToNext());
        }
        return list;
    }

    @Override
    public E one() {
        final Cursor cursor = query();
        final int count = cursor.getCount();
        final HashMap<String, Integer> columnIndexMap = new HashMap<>();
        for (String columnName : map.keySet()) {
            try {
                final int index = cursor.getColumnIndexOrThrow(columnName);
                columnIndexMap.put(columnName, index);
            } catch (IllegalArgumentException e) {
            }
        }
        if (count > 0 && cursor.moveToFirst()) {
            try {
                return getObject(columnIndexMap, cursor);
            } catch (InstantiationException e) {
            } catch (IllegalAccessException e) {
            }
        }
        return null;
    }

    @Override
    public int delete() {
        return db.delete(Util.getTable(modelClass), selection, null);
    }

    E getObject(HashMap<String, Integer> columnIndexMap, Cursor cursor) throws IllegalAccessException, InstantiationException {
        final Object object = modelClass.newInstance();
        for (String columnName : columnIndexMap.keySet()) {
            final Field field = map.get(columnName);
            field.setAccessible(true);
            try {
                final int index = columnIndexMap.get(columnName);
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
            } catch (IllegalArgumentException e) {
            }
        }
        return (E) object;
    }
}