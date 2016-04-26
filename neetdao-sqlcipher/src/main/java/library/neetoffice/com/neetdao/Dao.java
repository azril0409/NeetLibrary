package library.neetoffice.com.neetdao;

import java.util.List;

/**
 * Created by Deo on 2016/3/7.
 */
public interface Dao<E> {

    QueryBuilder<E> queryBuilder();

    int count();

    long insert(E entity);

    long insertOrReplace(E entity);

    int update(E entity);

    int delete(E entity);

    E load();

    List<E> loadAll();
}
