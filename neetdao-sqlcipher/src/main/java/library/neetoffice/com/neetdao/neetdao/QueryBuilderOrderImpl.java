package library.neetoffice.com.neetdao.neetdao;

import net.sqlcipher.database.SQLiteDatabase;

/**
 * Created by Mac on 2016/03/12.
 */
class QueryBuilderOrderImpl<E> extends QueryBuilderResultImpl<E> implements QueryBuilderOrder<E> {

    protected QueryBuilderOrderImpl(SQLiteDatabase db, Class modelClass) {
        super(db, modelClass);
    }

    @Override
    public QueryBuilderResult orderAsc(String fieldName) {
        if (fieldName == null) {
            return this;
        }
        orderBy = String.format("ORDER BY %s ASC", fieldName);
        return this;
    }

    @Override
    public QueryBuilderResult orderDesc(String fieldName) {
        if (fieldName == null) {
            return this;
        }
        orderBy = String.format("ORDER BY %s DESC", fieldName);
        return this;
    }
}
