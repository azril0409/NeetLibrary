package library.neetoffice.com.neetdao;

/**
 * Created by Mac on 2016/03/12.
 */
class QueryBuilderOrderImpl<E> extends QueryBuilderResultImpl<E> implements QueryBuilderOrder<E> {

    protected QueryBuilderOrderImpl(SQLiteHelper help, Class modelClass) {
        super(help, modelClass);
    }

    @Override
    public QueryBuilderResult<E>  orderAsc(String fieldName) {
        if (fieldName == null) {
            return this;
        }
        orderBy = String.format("ORDER BY %s ASC", fieldName);
        return this;
    }

    @Override
    public QueryBuilderResult<E>  orderDesc(String fieldName) {
        if (fieldName == null) {
            return this;
        }
        orderBy = String.format("ORDER BY %s DESC", fieldName);
        return this;
    }
}
