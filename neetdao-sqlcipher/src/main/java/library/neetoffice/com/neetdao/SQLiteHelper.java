package library.neetoffice.com.neetdao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Mac on 2016/04/04.
 */

class SQLiteHelper extends SQLiteOpenHelper {
    private final Class<?>[] modelClasses;
    private final String password;

    SQLiteHelper(Context context, String name, String password, int version, Class<?>... modelClasses) {
        super(context, name, null, version);
        this.modelClasses = modelClasses;
        this.password = password;
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

    static void dropTable(SQLiteDatabase db, Class<?> modelClass) {
        final String table = Util.getTable(modelClass);
        db.execSQL("DROP TABLE " + table);
    }

    static void createTable(SQLiteDatabase db, Class<?> modelClass) throws NeetSQLException {
        try {
            modelClass.newInstance();
        } catch (InstantiationException e) {
            throw new NeetSQLException(modelClass.getName() + " doesn't have no-arg(default) constructor");
        } catch (IllegalAccessException e) {
            throw new NeetSQLException(e.getMessage());
        }
        final String table = Util.getTable(modelClass);
        final StringBuffer sql = new StringBuffer("CREATE TABLE IF NOT EXISTS ");
        sql.append(table);
        sql.append(" (");
        final Collection<Field> fields = Util.getFields(modelClass);
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
                boolean notNull = field.isAnnotationPresent(NotNull.class);
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

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (Class<?> modelClass : modelClasses) {
            try {
                createTable(db, modelClass);
            } catch (NeetSQLException e) {
                e.printStackTrace();
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
                    final Collection<Field> fields = Util.getFields(modelClass);
                    final List<ContentValues> list = new ArrayList();
                    if (cursor.moveToFirst()) {
                        do {
                            final ContentValues values = new ContentValues();
                            list.add(values);
                            for (Field field : fields) {
                                final String name = Util.getColumnName(field);
                                final int index = cursor.getColumnIndex(name);
                                if (index != -1) {
                                    if (cursor.isNull(index)) {
                                        continue;
                                    }
                                    if (field.getType() == Boolean.class) {
                                        values.put(name, cursor.getInt(index));
                                    } else if (field.getType() == boolean.class) {
                                        values.put(name, cursor.getInt(index));
                                    } else if (field.getType() == Short.class) {
                                        values.put(name, cursor.getShort(index));
                                    } else if (field.getType() == short.class) {
                                        values.put(name, cursor.getShort(index));
                                    } else if (field.getType() == Integer.class) {
                                        values.put(name, cursor.getInt(index));
                                    } else if (field.getType() == int.class) {
                                        values.put(name, cursor.getInt(index));
                                    } else if (field.getType() == Float.class) {
                                        values.put(name, cursor.getFloat(index));
                                    } else if (field.getType() == float.class) {
                                        values.put(name, cursor.getFloat(index));
                                    } else if (field.getType() == Double.class) {
                                        values.put(name, cursor.getDouble(index));
                                    } else if (field.getType() == double.class) {
                                        values.put(name, cursor.getDouble(index));
                                    } else if (field.getType() == Long.class) {
                                        values.put(name, cursor.getLong(index));
                                    } else if (field.getType() == long.class) {
                                        values.put(name, cursor.getLong(index));
                                    } else if (field.getType() == byte[].class) {
                                        values.put(name, cursor.getBlob(index));
                                    } else if (field.getType() == char[].class) {
                                        values.put(name, cursor.getString(index));
                                    } else if (field.getType() == String.class) {
                                        values.put(name, cursor.getString(index));
                                    }
                                }
                            }
                        } while (cursor.moveToNext());
                    }
                    cursor.close();
                    dropTable(db, modelClass);
                    createTable(db, modelClass);
                    for (ContentValues o : list) {
                        db.insert(table, null, o);
                    }
                }
                db.setTransactionSuccessful();
                db.endTransaction();
            } else {
                createTable(db, modelClass);
            }
        }
    }
}
