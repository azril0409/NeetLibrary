package library.neetoffice.com.adapters.base;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Mac on 2015/10/24.
 */
public abstract class Filter<E> {
    private GenericAdapterInterface genericAdapterInterface;

    public final void init(GenericAdapterInterface genericAdapterInterface) {
        this.genericAdapterInterface = genericAdapterInterface;
    }

    public final void reFilter() {
        genericAdapterInterface.reFilter();
    }

    public abstract boolean filter(E item);
}
