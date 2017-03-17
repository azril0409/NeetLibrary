package library.neetoffice.com.neetdao;

/**
 * Created by Mac on 2016/03/12.
 */
class QueryBuilderOrderImpl<E> extends QueryBuilderResultImpl<E> implements QueryBuilderOrder<E> {

    protected QueryBuilderOrderImpl(DatabaseManager manager, Class modelClass) {
        super(manager, modelClass);
    }

    @Override
    public QueryBuilderResult<E>  orderAsc(String fieldName) {
        if (fieldName == null) {
            return this;
        }
        orderBy = String.format("%s ASC", fieldName);
        return this;
    }

    @Override
    public QueryBuilderResult<E>  orderDesc(String fieldName) {
        if (fieldName == null) {
            return this;
        }
        orderBy = String.format("%s DESC", fieldName);
        return this;
    }
}
