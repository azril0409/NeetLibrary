package library.neetoffice.com.neetdao;

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
            return modelClass.getSimpleName();
        } else {
            return databaseTable.tableName();
        }
    }

    static String getColumnName(Field field) throws NeetSQLException{
        final DatabaseField databaseField = field.getAnnotation(DatabaseField.class);
        if (databaseField == null) {
            throw new NeetSQLException("No @DatabaseField");
        }
        if (databaseField.columnName().length() == 0) {
            return field.getName();
        } else {
            return databaseField.columnName();
        }
    }
}

