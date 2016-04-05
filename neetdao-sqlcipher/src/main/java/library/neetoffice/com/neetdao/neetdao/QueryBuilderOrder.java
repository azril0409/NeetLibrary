package library.neetoffice.com.neetdao.neetdao;

/**
 * Created by Deo on 2016/3/7.
 */
public interface QueryBuilderOrder<E> extends QueryBuilderResult<E>{

    QueryBuilderResult orderAsc(String fieldName);

    QueryBuilderResult orderDesc(String fieldName);
}
