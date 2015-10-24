package neetlibrary.genericrecycleradapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import library.neetoffice.com.adapters.base.Filter;
import library.neetoffice.com.adapters.base.GenericAdapterInterface;

/**
 * Created by Deo-chainmeans on 2015/6/5.
 */
public abstract class GenericRecyclerAdapter<E> extends RecyclerView.Adapter<ViewWrapper> implements GenericAdapterInterface<E> {
    protected ArrayList<E> items;
    private Context context;
    private Filter<E> filter = new Filter<E>(){

        @Override
        public boolean filter(E item) {
            return true;
        }
    };

    public GenericRecyclerAdapter(Context context, Collection<E> items) {
        this.context = context;
        this.items = filter.init(new ArrayList<>(items));
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
        if (items.size() > position) {
            viewWrapper.getView().bind(items.get(position));
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
        return items.size();
    }


    @Override
    public final void addAll(Collection<E> items) {
        this.items.addAll(filter.init(items));
        notifyDataSetChanged();
    }

    @Override
    public final void setAll(Collection<E> items) {
        this.items.clear();
        this.items.addAll(filter.init(items));
        notifyDataSetChanged();
    }

    @Override
    public final void add(E item) {
        if(filter.filter(item)){
            items.add(item);
            notifyDataSetChanged();
        }
    }

    @Override
    public final void set(int index, E item) {
        if(filter.filter(item)){
            try {
                items.set(index, item);
            }catch (Exception e){
            }finally {
                notifyDataSetChanged();
            }
        }
    }

    @Override
    public final void remove(E item) {
        if(filter.filter(item)){
            try {
                items.remove(item);
            }catch (Exception e){

            }finally {
                notifyDataSetChanged();
            }
        }
    }

    @Override
    public final void remove(int position) {

        remove(items.get(position));
    }

    @Override
    public final void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    @Override
    public final List<E> getItems() {
        return items;
    }

    @Override
    public E getItem(int position) {
        return items.get(position);
    }
}
