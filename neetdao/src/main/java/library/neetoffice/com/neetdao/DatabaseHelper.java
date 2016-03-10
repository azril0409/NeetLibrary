package library.neetoffice.com.neetdao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

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
                Log.d(DatabaseHelper.class.getSimpleName(), e.toString());
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    static String getTable(Class<?> modelClass) {
        final DatabaseTable databaseTable = modelClass.getAnnotation(DatabaseTable.class);
        if (databaseTable == null) {
            throw new NeetSQLException("No @DatabaseTable");
        }
        if (databaseTable.tableName().isEmpty()) {
            Log.d("Dao","getTable return : "+modelClass.getSimpleName());
            return modelClass.getSimpleName();
        } else {
            Log.d("Dao","getTable return : "+databaseTable.tableName());
            return databaseTable.tableName();
        }
    }

    static String getColumnName(Field field) {
        final DatabaseField databaseField = field.getAnnotation(DatabaseField.class);
        if (databaseField == null) {
            throw new NeetSQLException("No @DatabaseField");
        }
        if (databaseField.columnName().isEmpty()) {
            Log.d("Dao","getColumnName return : "+field.getName());
            return field.getName();
        } else {
            Log.d("Dao","getColumnName return : "+databaseField.columnName());
            return databaseField.columnName();
        }
    }

    public <E> Dao<E> getDao(Class<E> modelClass) {
        return new Dao<>(getWritableDatabase(), modelClass);
    }

    private static void createTable(SQLiteDatabase db, Class<?> modelClass) throws NeetSQLException {
        final StringBuffer sql = new StringBuffer("CREATE TABLE ");
        sql.append(getTable(modelClass));
        sql.append(" (");
        final ArrayList<Field> fields = new ArrayList<>();
        for (Field field : modelClass.getDeclaredFields()) {
            final DatabaseField databaseField = field.getAnnotation(DatabaseField.class);
            if (databaseField == null) {
                continue;
            }
            fields.add(field);
        }
        final Iterator<Field> iterator = fields.iterator();
        while (iterator.hasNext()) {
            final Field field = iterator.next();
            final DatabaseField databaseField = field.getAnnotation(DatabaseField.class);
            final boolean notNull = databaseField.NotNull();
            final boolean primaryKey = databaseField.PrimaryKey();
            sql.append(getColumnName(field));
            sql.append(" ");
            if (field.getType() == Boolean.class) {
                sql.append(" INTEGER NOT NULL");
            } else if (field.getType() == boolean.class) {
                sql.append(" INTEGER NOT NULL");
            } else if (field.getType() == Short.class) {
                sql.append(" INTEGER");
                if (primaryKey) {
                    sql.append(" PRIMARY KEY NOT NULL");
                } else {
                    if (notNull) {
                        sql.append(" NOT NULL");
                    }
                }
            } else if (field.getType() == short.class) {
                sql.append(" INTEGER");
                if (primaryKey) {
                    sql.append(" PRIMARY KEY NOT NULL");
                } else {
                    if (notNull) {
                        sql.append(" NOT NULL");
                    }
                }
            } else if (field.getType() == Integer.class) {
                sql.append(" INTEGER");
                if (primaryKey) {
                    sql.append(" PRIMARY KEY NOT NULL");
                } else {
                    if (notNull) {
                        sql.append(" NOT NULL");
                    }
                }
            } else if (field.getType() == int.class) {
                sql.append(" INTEGER");
                if (primaryKey) {
                    sql.append(" PRIMARY KEY NOT NULL");
                } else {
                    if (notNull) {
                        sql.append(" NOT NULL");
                    }
                }
            } else if (field.getType() == Float.class) {
                sql.append(" FLOAT");
                if (primaryKey) {
                    sql.append(" PRIMARY KEY NOT NULL");
                } else {
                    if (notNull) {
                        sql.append(" NOT NULL");
                    }
                }
            } else if (field.getType() == float.class) {
                sql.append(" FLOAT");
                if (primaryKey) {
                    sql.append(" PRIMARY KEY NOT NULL");
                } else {
                    if (notNull) {
                        sql.append(" NOT NULL");
                    }
                }
            } else if (field.getType() == Double.class) {
                sql.append(" DOUBLE");
                if (primaryKey) {
                    sql.append(" PRIMARY KEY NOT NULL");
                } else {
                    if (notNull) {
                        sql.append(" NOT NULL");
                    }
                }
            } else if (field.getType() == double.class) {
                sql.append(" DOUBLE");
                if (primaryKey) {
                    sql.append(" PRIMARY KEY NOT NULL");
                } else {
                    if (notNull) {
                        sql.append(" NOT NULL");
                    }
                }
            } else if (field.getType() == Long.class) {
                sql.append(" INTEGER");
                if (primaryKey) {
                    sql.append(" PRIMARY KEY NOT NULL");
                } else {
                    if (notNull) {
                        sql.append(" NOT NULL");
                    }
                }
            } else if (field.getType() == long.class) {
                sql.append(" INTEGER");
                if (primaryKey) {
                    sql.append(" PRIMARY KEY NOT NULL");
                } else {
                    if (notNull) {
                        sql.append(" NOT NULL");
                    }
                }
            } else if (field.getType() == byte[].class) {
                sql.append(" BLOB");
                if (primaryKey) {
                    sql.append(" PRIMARY KEY NOT NULL");
                } else {
                    if (notNull) {
                        sql.append(" NOT NULL");
                    }
                }
            } else if (field.getType() == char[].class) {
                sql.append(" TEXT");
                if (primaryKey) {
                    sql.append(" PRIMARY KEY NOT NULL");
                } else {
                    if (notNull) {
                        sql.append(" NOT NULL");
                    }
                }
            } else if (field.getType() == String.class) {
                sql.append(" TEXT");
                if (primaryKey) {
                    sql.append(" PRIMARY KEY NOT NULL");
                } else {
                    if (notNull) {
                        sql.append(" NOT NULL");
                    }
                }
            }
            if (iterator.hasNext()) {
                sql.append(" , ");
            }
        }
        sql.append(")");
        Log.d("Dao", sql.toString());
        db.execSQL(sql.toString());
    }
}
