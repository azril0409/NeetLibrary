package neetlibrary.genericrecycleradapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import library.neetoffice.com.adapters.base.Filter;
import library.neetoffice.com.adapters.base.GenericAdapterInterface;

/**
 * Created by Deo-chainmeans on 2015/6/5.
 */
public abstract class GenericRecyclerAdapter<E> extends RecyclerView.Adapter<ViewWrapper> implements GenericAdapterInterface<E> {
    private Context context;
    private Filter<E> filter = new Filter<E>() {

        @Override
        public boolean filter(E item) {
            return true;
        }
    };
    private ArrayList<E> originalItems = new ArrayList<>();
    private ArrayList<Integer> indexs = new ArrayList<>();
    private final Comparator<Integer> sort = new Comparator<Integer>() {
        @Override
        public int compare(Integer lhs, Integer rhs) {
            return lhs - rhs;
        }
    };

    public GenericRecyclerAdapter(Context context, Collection<E> items) {
        this.context = context;
        originalItems = new ArrayList<>(items);
        filter.init(this);
        reFilter();
    }

    public final Context getContext() {
        return context;
    }

    @Override
    public ViewWrapper onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(getClass().getSimpleName(), "onCreateViewHolder");
        return new ViewWrapper(onCreateItemView(parent, viewType));
    }

    @Override
    public void onBindViewHolder(ViewWrapper viewWrapper, int position) {
        Log.d(getClass().getSimpleName(), "onBindViewHolder position = " + position);
        if (indexs.size() > position) {
            viewWrapper.getView().bind(originalItems.get(indexs.get(position)));
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
        return indexs.size();
    }


    @Override
    public final void addAll(Collection<E> items) {
        try {
            final int originalSize = originalItems.size();
            final boolean b = originalItems.addAll(items);
            for (int index = originalSize; index < originalItems.size(); index++) {
                E originalItem = originalItems.get(index);
                if (b && filter.filter(originalItem)) {
                    indexs.add(index);
                    notifyItemInserted(indexs.size() - 1);
                }
            }
        } catch (Exception e) {
        } finally {
        }
    }

    @Override
    public final void setAll(Collection<E> items) {
        try {
            originalItems.clear();
            originalItems.addAll(items);
            reFilter();
        } catch (Exception e) {
        } finally {
        }
    }

    @Override
    public final void add(E item) {
        try {
            final boolean b = originalItems.add(item);
            if (b && filter.filter(item)) {
                indexs.add(originalItems.size() - 1);
                notifyItemInserted(indexs.size() - 1);
            }
        } catch (Exception e) {
        } finally {
        }
    }

    @Override
    public final void set(int index, E item) {
        try {
            originalItems.set(index, item);
            if (filter.filter(item)) {
                if (!indexs.contains(index)) {
                    indexs.add(index);
                    Collections.sort(indexs, sort);
                }
            } else {
                if (indexs.contains(index)) {
                    indexs.remove(indexs.indexOf(index));
                    Collections.sort(indexs, sort);
                }
            }
        } catch (Exception e) {
        } finally {
        }
    }

    @Override
    public final void remove(E item) {
        try {
            originalItems.remove(item);
            reFilter();
        } catch (Exception e) {
        } finally {
        }
    }

    @Override
    public final void remove(int position) {
        try {
            originalItems.remove(position);
            reFilter();
        } catch (Exception e) {
        } finally {
        }
    }

    @Override
    public final void clear() {
        originalItems.clear();
        for (int i = 0; i < indexs.size(); i++) {
            notifyItemRemoved(i);
        }
        indexs.clear();
    }

    @Override
    public final void setFilter(Filter<E> filter) {
        filter.init(this);
        this.filter = filter;
        reFilter();
    }

    @Override
    public void reFilter() {
        for (int i = 0; i < indexs.size(); i++) {
            notifyItemRemoved(i);
        }
        indexs.clear();
        for (int index = 0; index < originalItems.size(); index++) {
            E originalItem = originalItems.get(index);
            if (filter.filter(originalItem)) {
                if (!indexs.contains(index)) {
                    indexs.add(index);
                    Collections.sort(indexs, sort);
                    notifyItemInserted(index);
                }
            } else {
                if (indexs.contains(index)) {
                    indexs.remove(indexs.indexOf(index));
                    Collections.sort(indexs, sort);
                    notifyItemRemoved(index);
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public final List<E> getItems() {
        return originalItems;
    }

    @Override
    public E getItem(int position) {
        return originalItems.get(indexs.get(position));
    }
}
