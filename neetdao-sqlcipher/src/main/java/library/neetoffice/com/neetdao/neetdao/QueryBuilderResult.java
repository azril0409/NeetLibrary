package library.neetoffice.com.neetdao.neetdao;

import java.util.List;

/**
 * Created by Deo on 2016/3/7.
 */
public interface QueryBuilderResult<E> {

    QueryBuilderResult<E> limit(int maxRows);

    QueryBuilderResult<E> offset(int maxRows);

    int count();

    List<E> list();

    E one();

    int delete();
}
