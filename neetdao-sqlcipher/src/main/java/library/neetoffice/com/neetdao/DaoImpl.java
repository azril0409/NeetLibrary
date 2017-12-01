package library.neetoffice.com.neetdao;

import android.content.ContentValues;
import android.database.SQLException;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Mac on 2016/03/12.
 */
class DaoImpl<E> extends DatabaseManager implements Dao<E> {
    private final Class<E> modelClass;
    private final Collection<Field> fields;

    DaoImpl(SQLiteHelper sqLiteHelper, String password, Class<E> modelClass) {
        super(sqLiteHelper, password);
        this.modelClass = modelClass;
        fields = Util.getFields(modelClass);
    }

    private ContentValues getContentValues(E entity) {
        final ContentValues cv = new ContentValues();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                if (field.getAnnotation(DatabaseField.class) == null || field.getAnnotation(Id.class) == null) {
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
                }
            } catch (IllegalAccessException e) {
            }
            field.setAccessible(false);
        }
        return cv;
    }

    @Override
    public QueryBuilder<E> queryBuilder() {
        return new QueryBuilderImpl<E>(this, modelClass);
    }

    @Override
    public int count() {
        return new QueryBuilderImpl<E>(this, modelClass).count();
    }

    @Override
    public long insert(E entity) {
        final SQLiteDatabase db = openDatabase();
        final long count = db.insert(Util.getTable(modelClass), null, getContentValues(entity));
        close();
        return count;
    }

    @Override
    public long insertOrReplace(E entity) {
        final SQLiteDatabase db = openDatabase();
        Id annotation = null;
        Field id_Field = null;
        for (Field f : fields) {
            annotation = f.getAnnotation(Id.class);
            if (annotation != null) {
                id_Field = f;
                break;
            }
        }
        long count = 0;
        if (id_Field != null && annotation != null) {
            id_Field.setAccessible(true);
            try {
                final Object object = id_Field.get(entity);
                if (object != null) {
                    final String selection = Where.eq(annotation.value(), object).selection;
                    final Cursor cursor = db.query(Util.getTable(modelClass), null, selection, null, null, null, null);
                    final boolean exists = cursor.getCount() > 0;
                    cursor.close();
                    if (exists) {
                        count = db.update(Util.getTable(modelClass), getContentValues(entity), selection, null);
                    } else {
                        count = db.insert(Util.getTable(modelClass), null, getContentValues(entity));
                    }
                } else {
                    count = db.insert(Util.getTable(modelClass), null, getContentValues(entity));
                }
            } catch (IllegalAccessException e) {
            }
            id_Field.setAccessible(false);
        } else {
            count = db.insert(Util.getTable(modelClass), null, getContentValues(entity));
        }
        close();
        return count;
    }

    @Override
    public int update(E entity) {
        final SQLiteDatabase db = openDatabase();
        Field id_Field = null;
        Id annotation = null;
        for (Field f : fields) {
            final Id idAnnotation = f.getAnnotation(Id.class);
            if (idAnnotation != null) {
                annotation = idAnnotation;
                id_Field = f;
                break;
            }
        }
        int count = 0;
        if (id_Field != null && annotation != null) {
            id_Field.setAccessible(true);
            try {
                final Object value = id_Field.get(entity);
                if (value != null) {
                    count = db.update(Util.getTable(modelClass), getContentValues(entity), Where.eq(annotation.value(), value).selection, null);
                }
            } catch (IllegalAccessException e) {
            }
            id_Field.setAccessible(false);
        }
        close();
        return count;
    }

    @Override
    public int delete(E entity) {
        final ArrayList<Where> wheres = new ArrayList<>();
        Field id_Field = null;
        Id annotation = null;
        for (Field f : fields) {
            final Id idAnnotation = f.getAnnotation(Id.class);
            if (idAnnotation != null) {
                annotation = idAnnotation;
                id_Field = f;
            } else {
                f.setAccessible(true);
                try {
                    final Object value = f.get(entity);
                    if (f != null && value != null) {
                        wheres.add(Where.eq(Util.getColumnName(f), value));
                    }
                } catch (IllegalAccessException e) {
                }
                f.setAccessible(false);
            }
        }
        int count = 0;
        if (id_Field != null && annotation != null) {
            id_Field.setAccessible(true);
            try {
                final Object value = id_Field.get(entity);
                if (value != null) {
                    count = new QueryBuilderImpl<E>(this, modelClass).where(Where.eq(annotation.value(), value)).delete();
                }
            } catch (IllegalAccessException e) {
            }
            id_Field.setAccessible(false);
        } else {
            count = new QueryBuilderImpl<E>(this, modelClass).where(wheres.toArray(new Where[wheres.size()])).delete();
        }
        return count;
    }

    @Override
    public E one() {
        return new QueryBuilderImpl<E>(this, modelClass).one();
    }

    @Override
    public List<E> list() {
        return new QueryBuilderImpl<E>(this, modelClass).list();
    }

    @Override
    public void create() {
        SQLiteHelper.createTable(openDatabase(), modelClass);
        close();
    }

    @Override
    public void drop() {
        SQLiteHelper.dropTable(openDatabase(), modelClass);
        close();
    }

    @Override
    public void beginTransaction() {
        final SQLiteDatabase db = openDatabase();
        db.beginTransaction();
    }

    @Override
    public void setTransactionSuccessful() {
        final SQLiteDatabase db = openDatabase();
        db.setTransactionSuccessful();
    }

    @Override
    public void endTransaction() {
        final SQLiteDatabase db = openDatabase();
        db.endTransaction();
    }

    @Override
    public void execSQL(String sql) throws SQLException {
        final SQLiteDatabase db = openDatabase();
        db.execSQL(sql);
    }

    @Override
    public void rawQuery(String sql, String[] selectionArgs) throws SQLException {
        final SQLiteDatabase db = openDatabase();
        db.rawQuery(sql,selectionArgs);
    }
}
