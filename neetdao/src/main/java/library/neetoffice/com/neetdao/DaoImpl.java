package library.neetoffice.com.neetdao;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;
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
                field.setAccessible(true);
                final Object value = field.get(entity);
                if (value == null) {
                    continue;
                }
                if (field.getType() == Boolean.class) {
                    cv.put(Util.getColumnName(field), (Boolean) value ? 1 : 0);
                } else if (field.getType() == boolean.class) {
                    cv.put(Util.getColumnName(field), (boolean) value ? 1 : 0);
                } else if (field.getType() == Short.class) {
                    cv.put(Util.getColumnName(field), (Short) value);
                } else if (field.getType() == short.class) {
                    cv.put(Util.getColumnName(field), (short) value);
                } else if (field.getType() == Integer.class) {
                    cv.put(Util.getColumnName(field), (Integer) value);
                } else if (field.getType() == int.class) {
                    cv.put(Util.getColumnName(field), (int) value);
                } else if (field.getType() == Float.class) {
                    cv.put(Util.getColumnName(field), (Float) value);
                } else if (field.getType() == float.class) {
                    cv.put(Util.getColumnName(field), (float) value);
                } else if (field.getType() == Double.class) {
                    cv.put(Util.getColumnName(field), (Double) value);
                } else if (field.getType() == double.class) {
                    cv.put(Util.getColumnName(field), (double) value);
                } else if (field.getType() == Long.class) {
                    cv.put(Util.getColumnName(field), (Long) value);
                } else if (field.getType() == long.class) {
                    cv.put(Util.getColumnName(field), (long) value);
                } else if (field.getType() == byte[].class) {
                    cv.put(Util.getColumnName(field), (byte[]) value);
                } else if (field.getType() == char[].class) {
                    cv.put(Util.getColumnName(field), new String((char[]) value));
                } else if (field.getType() == String.class) {
                    cv.put(Util.getColumnName(field), (String) value);
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
    public long insert(E entity) {
        return db.insert(Util.getTable(modelClass), null, getContentValues(entity));
    }

    @Override
    public long insertOrReplace(E entity) {
        final Field[] fields = modelClass.getDeclaredFields();
        Field id_Field = null;
        Id annotation = null;
        for (Field f : fields) {
            annotation = f.getAnnotation(Id.class);
            if (annotation != null) {
                id_Field = f;
                break;
            }
        }
        if (id_Field != null && annotation != null) {
            id_Field.setAccessible(true);
            try {
                Object object = id_Field.get(entity);
                if (object != null) {
                    return db.update(Util.getTable(modelClass), getContentValues(entity), Where.eq(annotation.value(), object).selection, null);
                } else {
                    return db.insert(Util.getTable(modelClass), null, getContentValues(entity));
                }
            } catch (IllegalAccessException e) {
            }
        } else {
            return db.insert(Util.getTable(modelClass), null, getContentValues(entity));
        }
        return 0;
    }

    @Override
    public int update(E entity) {
        final Field[] fields = modelClass.getDeclaredFields();
        Field id_Field = null;
        Id annotation = null;
        for (Field f : fields) {
            annotation = f.getAnnotation(Id.class);
            if (annotation != null) {
                id_Field = f;
                break;
            }
        }
        if (id_Field != null && annotation != null) {
            id_Field.setAccessible(true);
            try {
                final Object value = id_Field.get(entity);
                if (value != null) {
                    return db.update(Util.getTable(modelClass), getContentValues(entity), Where.eq(annotation.value(), value).selection, null);
                }
            } catch (IllegalAccessException e) {
            }
        }
        return 0;
    }

    @Override
    public int delete(E entity) {
        ArrayList<Where> wheres = new ArrayList<>();
        final Field[] fields = modelClass.getDeclaredFields();
        Field id_Field = null;
        Id annotation = null;
        for (Field f : fields) {
            annotation = f.getAnnotation(Id.class);
            if (annotation != null) {
                id_Field = f;
            } else {
                try {
                    f.setAccessible(true);
                    final Object value = f.get(entity);
                    wheres.add(Where.eq(Util.getColumnName(f), value));
                } catch (IllegalAccessException e) {
                }
            }
        }
        if (id_Field != null && annotation != null) {
            id_Field.setAccessible(true);
            try {
                final Object value = id_Field.get(entity);
                if (value != null) {
                    return new QueryBuilderImpl<E>(db, modelClass).where(Where.eq(annotation.value(), value)).delete();
                }
            } catch (IllegalAccessException e) {
            }
        } else {
            return new QueryBuilderImpl<E>(db, modelClass).where(wheres.toArray(new Where[wheres.size()])).delete();
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
