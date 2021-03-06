package library.neetoffice.com.neetdao;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by Mac on 2016/03/12.
 */
public class QueryBuilderImpl<E> extends QueryBuilderOrderImpl<E> implements QueryBuilder<E> {

    QueryBuilderImpl(DatabaseManager manager, Class modelClass) {
        super(manager, modelClass);
    }

    @Override
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
                stringBuffer.append(" AND ");
            }
        }
        selection = stringBuffer.toString();
        return this;
    }
}
