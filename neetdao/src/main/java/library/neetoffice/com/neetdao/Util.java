package library.neetoffice.com.neetdao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by Deo on 2016/3/16.
 */
public abstract class Util {
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
                return "_" + field.getName().toUpperCase();
            } else {
                return idField.value();
            }
        } else if (databaseField != null) {
            if (databaseField.columnName().length() == 0) {
                return "_" + field.getName().toUpperCase();
            } else {
                return databaseField.columnName();
            }
        } else {
            throw new NeetSQLException("No @DatabaseField");
        }
    }

    static Collection<Field> getFields(Class<?> modelClass) {
        final ArrayList<Field> fields = new ArrayList<>();
        final HashMap<String, Field> map = new HashMap<>();
        Field idField = null;
        Class<?> cls = modelClass;
        while (cls != null) {
            for (Field field : modelClass.getDeclaredFields()) {
                final Id id = field.getAnnotation(Id.class);
                final DatabaseField databaseField = field.getAnnotation(DatabaseField.class);
                if (id != null && idField == null) {
                    idField = field;
                } else if (databaseField != null) {
                    final String key = getColumnName(field);
                    if (!map.containsKey(key)) {
                        map.put(key, field);
                    }
                }
            }
            cls = cls.getSuperclass();
        }
        fields.add(idField);
        fields.addAll(map.values());
        return fields;
    }
}

