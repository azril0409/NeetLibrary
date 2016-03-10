package library.neetoffice.com.neetdao;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Deo on 2016/3/7.
 */
public class QueryBuilderOrder<E> extends QueryBuilderResult<E> {

    protected QueryBuilderOrder(SQLiteDatabase db, Class modelClass) {
        super(db, modelClass);
    }

    public QueryBuilderResult orderAsc(String fieldName) {
        if (fieldName == null) {
            return this;
        }
        orderBy = String.format("ORDER BY %s DESC", fieldName);
        return this;
    }

    public QueryBuilderResult orderDesc(String fieldName) {
        if (fieldName == null) {
            return this;
        }
        orderBy = String.format("ORDER BY %s DESC", fieldName);
        return this;
    }
}
