package library.neetoffice.com.adapters.base;

import java.util.Collection;
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

    void remove(int position);

    void clear();

    List<E> getItems();

    E getItem(int position);
}
