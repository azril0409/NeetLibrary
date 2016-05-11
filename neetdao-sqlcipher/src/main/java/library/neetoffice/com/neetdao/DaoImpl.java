package library.neetoffice.com.neetdao;

import android.content.ContentValues;

import net.sqlcipher.database.SQLiteDatabase;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Mac on 2016/03/12.
 */
class DaoImpl<E> implements Dao<E> {
    private final SQLiteHelper sqLiteHelper;
    private final String password;
    private final Class<E> modelClass;
    private final Collection<Field> fields;

    DaoImpl(SQLiteHelper sqLiteHelper, String password, Class<E> modelClass) {
        this.sqLiteHelper = sqLiteHelper;
        this.password = password;
        this.modelClass = modelClass;
        fields = Util.getFields(modelClass);
    }

    private ContentValues getContentValues(E entity) {
        final ContentValues cv = new ContentValues();
        for (Field field : fields) {
            try {
                if (field.getAnnotation(DatabaseField.class) == null) {
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
        return new QueryBuilderImpl<E>(sqLiteHelper, password, modelClass);
    }

    @Override
    public int count() {
        return new QueryBuilderImpl<E>(sqLiteHelper, password, modelClass).count();
    }

    @Override
    public long insert(E entity) {
        final SQLiteDatabase db = sqLiteHelper.getWritableDatabase(password);
        final long count = db.insert(Util.getTable(modelClass), null, getContentValues(entity));
        db.close();
        return count;
    }

    @Override
    public long insertOrReplace(E entity) {
        final SQLiteDatabase db = sqLiteHelper.getWritableDatabase(password);
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
                    count = db.update(Util.getTable(modelClass), getContentValues(entity), Where.eq(annotation.value(), object).selection, null);
                } else {
                    count = db.insert(Util.getTable(modelClass), null, getContentValues(entity));
                }
            } catch (IllegalAccessException e) {
            }
        } else {
            count = db.insert(Util.getTable(modelClass), null, getContentValues(entity));
        }
        db.close();
        return count;
    }

    @Override
    public int update(E entity) {
        final SQLiteDatabase db = sqLiteHelper.getWritableDatabase(password);
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
        }
        db.close();
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
                try {
                    f.setAccessible(true);
                    final Object value = f.get(entity);
                    wheres.add(Where.eq(Util.getColumnName(f), value));
                } catch (IllegalAccessException e) {
                }
            }
        }
        int count = 0;
        if (id_Field != null && annotation != null) {
            id_Field.setAccessible(true);
            try {
                final Object value = id_Field.get(entity);
                if (value != null) {
                    count = new QueryBuilderImpl<E>(sqLiteHelper, password, modelClass).where(Where.eq(annotation.value(), value)).delete();
                }
            } catch (IllegalAccessException e) {
            }
        } else {
            count = new QueryBuilderImpl<E>(sqLiteHelper, password, modelClass).where(wheres.toArray(new Where[wheres.size()])).delete();
        }
        return count;
    }

    @Override
    public E load() {
        return new QueryBuilderImpl<E>(sqLiteHelper, password, modelClass).one();
    }

    @Override
    public List<E> loadAll() {
        return new QueryBuilderImpl<E>(sqLiteHelper, password, modelClass).list();
    }
}