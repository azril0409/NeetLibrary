package library.neetoffice.com.neetdao;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by Deo on 2016/3/7.
 */
public class Where {
    String selection;

    private Where(String selection) {
        this.selection = selection;
    }

    static Object getValue(Object value) {
        final Class cls = value.getClass();
        if (cls == Boolean.class) {
            return (Boolean) value ? 1 : 0;
        } else if (cls == boolean.class) {
            return (boolean) value ? 1 : 0;
        } else if (cls == char[].class) {
            return String.valueOf((char[]) value);
        } else {
            return value;
        }
    }

    /**
     * Creates an "equal ('=')".
     */
    public static Where eq(String fieldName, Object value) {
        return new Where(String.format("%s = '%s'", fieldName, getValue(value)));
    }

    /**
     * Creates an "greater or equal ('>=')".
     */
    public static Where ge(String fieldName, Object value) {
        return new Where(String.format("%s >= '%s'", fieldName, getValue(value)));
    }

    /**
     * Creates an "greater than ('>')".
     */
    public static Where gt(String fieldName, Object value) {
        return new Where(String.format("%s > '%s'", fieldName, getValue(value)));
    }

    /**
     * Creates an "IN (..., ..., ...)".
     */
    public static Where in(String fieldName, Object... value) {
        return in(fieldName, Arrays.asList(value));
    }

    /**
     * Creates an "IN (..., ..., ...)"  .
     */
    public static Where in(String fieldName, Collection<?> value) {
        final Iterator iterator = value.iterator();
        final StringBuffer stringBuffer = new StringBuffer(fieldName);
        stringBuffer.append(" IN ('");
        while (iterator.hasNext()) {
            stringBuffer.append(getValue(iterator.next()));
            if (iterator.hasNext()) {
                stringBuffer.append("','");
            }
        }
        stringBuffer.append("')");
        return new Where(stringBuffer.toString());
    }

    /**
     * Creates an "IS NOT NULL" .
     */
    public static Where isNotNull(String fieldName) {
        return new Where(String.format("%s IS NOT NULL", fieldName));
    }

    /**
     * Creates an "IS NULL" .
     */
    public static Where isNull(String fieldName) {
        return new Where(String.format("%s IS NULL", fieldName));
    }

    /**
     * Creates an "less or equal ('<=')".
     */
    public static Where le(String fieldName, Object value) {
        return new Where(String.format("%s <= '%s'", fieldName, getValue(value)));
    }

    /**
     * Creates an "LIKE".
     */
    public static Where like(String fieldName, String value) {
        return new Where(fieldName + " Like '%" + value + "%'");
    }

    /**
     * Creates an "not equal ('<')".
     */
    public static Where lt(String fieldName, Object value) {
        return new Where(String.format("%s < '%s'", fieldName, getValue(value)));
    }

    /**
     * Creates an "not equal ('<>')".
     */
    public static Where notEq(String fieldName, Object value) {
        return new Where(String.format("%s <> '%s'", fieldName, getValue(value)));
    }

    public static Where group(Where where) {
        return new Where("(" + where.selection + ")");
    }

    public Where AND(Where where) {
        selection += " AND " + where.selection;
        return this;
    }

    public Where OR(Where where) {
        selection += " OR " + where.selection;
        return this;
    }
}
