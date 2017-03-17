package library.neetoffice.com.neetdao;

import java.lang.reflect.Field;

/**
 * Created by Deo-chainmeans on 2016/9/13.
 */
public abstract class NeetDaoUtil {

    public static String getDefaultColumnName(String name) {
        return "_" + name.toUpperCase();
    }

    public static String getDefaultColumnName(Field field) {
        final Id idField = field.getAnnotation(Id.class);
        final DatabaseField databaseField = field.getAnnotation(DatabaseField.class);
        if (idField != null || databaseField != null) {
            return getDefaultColumnName(field.getName());
        } else {
            return getDefaultColumnName("");
        }
    }
}
