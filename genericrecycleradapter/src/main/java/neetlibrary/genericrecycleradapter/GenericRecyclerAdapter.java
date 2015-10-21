package neetlibrary.genericrecycleradapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import library.neetoffice.com.adapters.base.GenericAdapterInterface;

/**
 * Created by Deo-chainmeans on 2015/6/5.
 */
public abstract class GenericRecyclerAdapter<E> extends RecyclerView.Adapter<ViewWrapper> implements GenericAdapterInterface<E> {
    protected ArrayList<E> es;
    private Context context;

    public GenericRecyclerAdapter(Context context, Collection<E> items) {
        this.context = context;
        this.es = new ArrayList(items);
    }

    public final Context getContext() {
        return context;
    }

    @Override
    public ViewWrapper onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewWrapper(onCreateItemView(parent, viewType));
    }

    @Override
    public void onBindViewHolder(ViewWrapper viewWrapper, int position) {
        if (es.size() > position) {
            viewWrapper.getView().bind(es.get(position));
        }
        if (getItemClickable(position)) {
            viewWrapper.getView().onItemClickable();
        }
    }

    public abstract CellView<E> onCreateItemView(ViewGroup parent, int viewType);

    public boolean getItemClickable(int position) {
        return false;
    }

    @Override
    public int getItemCount() {
        return es.size();
    }


    @Override
    public final void addAll(Collection<E> items) {
        this.es.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public final void setAll(Collection<E> items) {
        this.es.clear();
        this.es.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public final void add(E item) {
        es.add(item);
        notifyDataSetChanged();
    }

    @Override
    public final void set(int index, E item) {
        try {
            es.set(index, item);
        } catch (Exception e) {

        } finally {
            notifyDataSetChanged();
        }
    }

    @Override
    public final void remove(E item) {
        try {
            es.remove(item);
        } catch (Exception e) {

        } finally {
            notifyDataSetChanged();
        }
    }

    @Override
    public final void remove(int position) {
        remove(es.get(position));
    }

    @Override
    public final void clear() {
        es.clear();
        notifyDataSetChanged();
    }

    @Override
    public final List<E> getItems() {
        return es;
    }

    @Override
    public E getItem(int position) {
        return es.get(position);
    }
}
