package library.neetoffice.com.genericadapter.base;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Deo-chainmeans on 2015/8/4.
 */
public interface GenericAdapterInterface<E> {

    void addAll(Collection<E> items);

    void setAll(Collection<E> items);

    void add(E item);

    void set(int index, E item);

    void remove(E item);

    E remove(int position);

    void clear();

    void setFilter(Filter<E> filter);

    void setSort(Comparator<E> sort);

    void refresh();

    List<E> getItems();

    E getItem(int position);
}
