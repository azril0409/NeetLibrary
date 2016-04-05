package library.neetoffice.com.neetdao.neetdao;

import java.lang.reflect.Field;

/**
 * Created by Deo on 2016/3/16.
 */
public class Util {
    static final String TAG = "NeetDao";

    static String getTable(Class<?> modelClass) {
        final DatabaseTable databaseTable = modelClass.getAnnotation(DatabaseTable.class);
        if (databaseTable == null) {
            throw new NeetSQLException("No @DatabaseTable");
        }
        if (databaseTable.tableName().length() == 0) {
            return modelClass.getSimpleName().toUpperCase();
        } else {
            return databaseTable.tableName();
        }
    }

    static String getColumnName(Field field) throws NeetSQLException {
        final Id idField = field.getAnnotation(Id.class);
        final DatabaseField databaseField = field.getAnnotation(DatabaseField.class);
        if (idField != null) {
            if (idField.value().length() == 0) {
                return "_"+field.getName().toUpperCase();
            } else {
                return idField.value();
            }
        } else if (databaseField != null) {
            if (databaseField.columnName().length() == 0) {
                return "_"+field.getName().toUpperCase();
            } else {
                return databaseField.columnName();
            }
        } else {
            throw new NeetSQLException("No @DatabaseField");
        }
    }
}

