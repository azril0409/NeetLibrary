package library.neetoffice.com.neetdao;

/**
 * Created by Deo on 2016/3/7.
 */
public interface QueryBuilderOrder<E> extends QueryBuilderResult<E>{

    QueryBuilderResult<E> orderAsc(String fieldName);

    QueryBuilderResult<E> orderDesc(String fieldName);
}
