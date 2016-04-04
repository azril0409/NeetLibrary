package library.neetoffice.com.neetdao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Deo on 2016/3/4.
 */
public abstract class DatabaseHelper extends SQLiteOpenHelper {
    private Class<?>[] modelClasses;

    public DatabaseHelper(Context context, String name, int version, Class<?>... modelClasses) {
        super(context, name, null, version);
        this.modelClasses = modelClasses;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (Class<?> modelClass : modelClasses) {
            try {
                createTable(db, modelClass);
            } catch (NeetSQLException e) {
                Log.e(Util.TAG, e.toString());
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (Class<?> modelClass : modelClasses) {
            final String table = Util.getTable(modelClass);
            final Cursor masterCursor = db.rawQuery("SELECT name FROM sqlite_master WHERE TYPE = 'table' AND name= '" + table + "'", null);
            if (masterCursor.getCount() > 0) {
                db.beginTransaction();
                final Cursor cursor = db.rawQuery("SELECT * FROM " + table, null);
                if (isUpdate(cursor, modelClass)) {
                    cursor.close();
                    final DaoImpl dao = new DaoImpl(db, modelClass);
                    List list = dao.loadAll();
                    dropTable(db, modelClass);
                    createTable(db, modelClass);
                    for (Object o : list) {
                        dao.insert(o);
                    }
                }
                db.setTransactionSuccessful();
                db.endTransaction();
            } else {
                createTable(db, modelClass);
            }
        }
    }

    public <E> Dao<E> getDao(Class<E> modelClass) {
        return new DaoImpl<>(getWritableDatabase(), modelClass);
    }

    private static boolean isUpdate(Cursor cursor, Class<?> modelClass) {
        final Field[] fields = modelClass.getDeclaredFields();
        for (Field field : fields) {
            try {
                final String columnName = Util.getColumnName(field);
                cursor.getColumnIndexOrThrow(columnName);
            } catch (NeetSQLException e) {
            } catch (IllegalArgumentException e) {
                return true;
            }
        }
        return false;
    }

    private static void dropTable(SQLiteDatabase db, Class<?> modelClass) {
        final String table = Util.getTable(modelClass);
        db.execSQL("DROP TABLE " + table);
    }

    private static void createTable(SQLiteDatabase db, Class<?> modelClass) throws NeetSQLException {
        try {
            modelClass.newInstance();
        } catch (InstantiationException e) {
            throw new NeetSQLException(modelClass.getName() + " doesn't have no-arg(default) constructor");
        } catch (IllegalAccessException e) {
            throw new NeetSQLException(e.getMessage());
        }
        final String table = Util.getTable(modelClass);
        final StringBuffer sql = new StringBuffer("CREATE TABLE ");
        sql.append(table);
        sql.append(" (");
        final ArrayList<Field> fields = new ArrayList<>();
        for (Field field : modelClass.getDeclaredFields()) {
            final Id id = field.getAnnotation(Id.class);
            final DatabaseField databaseField = field.getAnnotation(DatabaseField.class);
            if (id != null) {
                fields.add(field);
            } else if (databaseField != null) {
                fields.add(field);
            }
        }
        final Iterator<Field> iterator = fields.iterator();
        while (iterator.hasNext()) {
            final Field field = iterator.next();
            final Id id = field.getAnnotation(Id.class);
            final DatabaseField databaseField = field.getAnnotation(DatabaseField.class);
            if (id != null) {
                sql.append(Util.getColumnName(field));
                sql.append(" ");
                if (field.getType() == Integer.class) {
                    sql.append(" INTEGER");
                } else if (field.getType() == int.class) {
                    sql.append(" INTEGER");
                } else if (field.getType() == Long.class) {
                    sql.append(" INTEGER");
                } else if (field.getType() == long.class) {
                    sql.append(" INTEGER");
                } else if (field.getType() == String.class) {
                    sql.append(" TEXT");
                } else {
                    throw new NeetSQLException("Id's type should String or Number( Integer or Long)");
                }
                sql.append(" PRIMARY KEY NOT NULL");
            } else if (databaseField != null) {
                boolean notNull = databaseField.NotNull();
                sql.append(Util.getColumnName(field));
                sql.append(" ");
                if (field.getType() == Boolean.class) {
                    sql.append(" INTEGER");
                    notNull = true;
                } else if (field.getType() == boolean.class) {
                    sql.append(" INTEGER");
                    notNull = true;
                } else if (field.getType() == Short.class) {
                    sql.append(" INTEGER");
                } else if (field.getType() == short.class) {
                    sql.append(" INTEGER");
                } else if (field.getType() == Integer.class) {
                    sql.append(" INTEGER");
                } else if (field.getType() == int.class) {
                    sql.append(" INTEGER");
                } else if (field.getType() == Float.class) {
                    sql.append(" FLOAT");
                } else if (field.getType() == float.class) {
                    sql.append(" FLOAT");
                } else if (field.getType() == Double.class) {
                    sql.append(" DOUBLE");
                } else if (field.getType() == double.class) {
                    sql.append(" DOUBLE");
                } else if (field.getType() == Long.class) {
                    sql.append(" INTEGER");
                } else if (field.getType() == long.class) {
                    sql.append(" INTEGER");
                } else if (field.getType() == byte[].class) {
                    sql.append(" BLOB");
                } else if (field.getType() == char[].class) {
                    sql.append(" TEXT");
                } else if (field.getType() == String.class) {
                    sql.append(" TEXT");
                }
                if (notNull) {
                    sql.append(" NOT NULL");
                }
            }
            if (iterator.hasNext()) {
                sql.append(" , ");
            }
        }
        sql.append(")");
        db.execSQL(sql.toString());
    }
}
