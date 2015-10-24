package library.neetoffice.com.adapters.base;

import android.widget.Adapter;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Mac on 2015/10/24.
 */
public abstract class Filter<E> {
    private GenericAdapterInterface genericAdapterInterface;

    public void init(GenericAdapterInterface genericAdapterInterface) {
        this.genericAdapterInterface = genericAdapterInterface;
    }


    public final void notifyDataSetChanged() {
    }

    public abstract boolean filter(E item);
}
