package library.neetoffice.com.neetdao;

import android.database.sqlite.SQLiteDatabase;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by Deo on 2016/3/7.
 */
public class QueryBuilder<E> extends QueryBuilderOrder<E> {

    public QueryBuilder(SQLiteDatabase db, Class modelClass) {
        super(db, modelClass);
    }

    public QueryBuilderOrder<E> where(Where... wheres) {
        if (wheres == null) {
            return this;
        }
        final Iterator<Where> iterator = Arrays.asList(wheres).iterator();
        final StringBuffer stringBuffer = new StringBuffer();
        while (iterator.hasNext()) {
            final Where where = iterator.next();
            stringBuffer.append(where.selection);
            if (iterator.hasNext()) {
                stringBuffer.append(",");
            }
        }
        selection = stringBuffer.toString();
        return this;
    }
}
