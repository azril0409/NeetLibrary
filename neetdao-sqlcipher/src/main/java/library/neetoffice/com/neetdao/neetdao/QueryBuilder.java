package library.neetoffice.com.neetdao.neetdao;

/**
 * Created by Deo on 2016/3/7.
 */
public interface QueryBuilder<E> extends QueryBuilderOrder<E>{

    QueryBuilderOrder<E> where(Where... wheres);
}
