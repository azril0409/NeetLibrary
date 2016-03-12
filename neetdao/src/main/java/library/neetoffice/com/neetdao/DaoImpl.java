package library.neetoffice.com.neetdao;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by Mac on 2016/03/12.
 */
class DaoImpl<E> implements Dao<E> {
    private final SQLiteDatabase db;
    private final Class<E> modelClass;

    DaoImpl(SQLiteDatabase db, Class<E> modelClass) {
        this.db = db;
        this.modelClass = modelClass;
    }

    public void beginTransaction() {
        db.beginTransaction();
    }

    public void commitTransaction() {
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    private ContentValues getContentValues(E entity) {
        final ContentValues cv = new ContentValues();
        final Field[] fields = modelClass.getDeclaredFields();
        for (Field field : fields) {
            try {
                final DatabaseField databaseField = field.getAnnotation(DatabaseField.class);
                if (databaseField == null) {
                    continue;
                }
                if (databaseField.PrimaryKey()) {
                    continue;
                }
                field.setAccessible(true);
                final Object value = field.get(entity);
                if (value == null) {
                    continue;
                }
                if (field.getType() == Boolean.class) {
                    cv.put(DatabaseHelper.getColumnName(field), (Boolean) value ? 1 : 0);
                } else if (field.getType() == boolean.class) {
                    cv.put(DatabaseHelper.getColumnName(field), (boolean) value ? 1 : 0);
                } else if (field.getType() == Short.class) {
                    cv.put(DatabaseHelper.getColumnName(field), (Short) value);
                } else if (field.getType() == short.class) {
                    cv.put(DatabaseHelper.getColumnName(field), (short) value);
                } else if (field.getType() == Integer.class) {
                    cv.put(DatabaseHelper.getColumnName(field), (Integer) value);
                } else if (field.getType() == int.class) {
                    cv.put(DatabaseHelper.getColumnName(field), (int) value);
                } else if (field.getType() == Float.class) {
                    cv.put(DatabaseHelper.getColumnName(field), (Float) value);
                } else if (field.getType() == float.class) {
                    cv.put(DatabaseHelper.getColumnName(field), (float) value);
                } else if (field.getType() == Double.class) {
                    cv.put(DatabaseHelper.getColumnName(field), (Double) value);
                } else if (field.getType() == double.class) {
                    cv.put(DatabaseHelper.getColumnName(field), (double) value);
                } else if (field.getType() == Long.class) {
                    cv.put(DatabaseHelper.getColumnName(field), (Long) value);
                } else if (field.getType() == long.class) {
                    cv.put(DatabaseHelper.getColumnName(field), (long) value);
                } else if (field.getType() == byte[].class) {
                    cv.put(DatabaseHelper.getColumnName(field), (byte[]) value);
                } else if (field.getType() == char[].class) {
                    cv.put(DatabaseHelper.getColumnName(field), new String((char[]) value));
                } else if (field.getType() == String.class) {
                    cv.put(DatabaseHelper.getColumnName(field), (String) value);
                }
            } catch (IllegalAccessException e) {
            }
        }
        return cv;
    }

    @Override
    public QueryBuilder<E> queryBuilder() {
        return new QueryBuilderImpl<>(db, modelClass);
    }

    @Override
    public int count() {
        return new QueryBuilderImpl<E>(db, modelClass).count();
    }

    @Override
    public int delete() {
        return new QueryBuilderImpl<E>(db, modelClass).delete();
    }

    @Override
    public long insert(E entity) {
        final String tableName = DatabaseHelper.getTable(modelClass);
        Log.d(Dao.class.getSimpleName(), "tableName : " + tableName);
        return db.insert(DatabaseHelper.getTable(modelClass), null, getContentValues(entity));
    }

    @Override
    public long insertOrReplace(E entity) {
        final Field[] fields = modelClass.getDeclaredFields();
        Field field = null;
        for (Field f : fields) {
            final DatabaseField databaseField = f.getAnnotation(DatabaseField.class);
            if (databaseField == null) {
                continue;
            }
            if (databaseField.PrimaryKey()) {
                field = f;
                break;
            }
        }
        if (field != null) {
            try {
                Object object = field.get(entity);
                if (object != null) {
                    return db.update(DatabaseHelper.getTable(modelClass), getContentValues(entity), Where.eq(DatabaseHelper.getColumnName(field), object).selection, null);
                } else {
                    return db.insert(DatabaseHelper.getTable(modelClass), null, getContentValues(entity));
                }
            } catch (IllegalAccessException e) {
            }
        } else {
            return db.insert(DatabaseHelper.getTable(modelClass), null, getContentValues(entity));
        }
        return 0;
    }

    @Override
    public int update(E entity) {
        final Field[] fields = modelClass.getDeclaredFields();
        Field field = null;
        for (Field f : fields) {
            final DatabaseField databaseField = f.getAnnotation(DatabaseField.class);
            if (databaseField == null) {
                continue;
            }
            if (databaseField.PrimaryKey()) {
                field = f;
                break;
            }
        }
        if (field != null) {
            try {
                Object value = field.get(entity);
                if (value != null) {
                    return db.update(DatabaseHelper.getTable(modelClass), getContentValues(entity), Where.eq(DatabaseHelper.getColumnName(field), value).selection, null);
                }
            } catch (IllegalAccessException e) {
            }
        }
        return 0;
    }

    @Override
    public E load() {
        return new QueryBuilderImpl<E>(db, modelClass).one();
    }

    @Override
    public List<E> loadAll() {
        return new QueryBuilderImpl<E>(db, modelClass).list();
    }
}
