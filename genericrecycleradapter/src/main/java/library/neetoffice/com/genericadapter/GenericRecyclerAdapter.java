package library.neetoffice.com.genericadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import library.neetoffice.com.genericadapter.base.Filter;
import library.neetoffice.com.genericadapter.base.GenericAdapterInterface;

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
    protected ArrayList<E> originalItems = new ArrayList<>();
    protected ArrayList<Integer> indexs = new ArrayList<>();
    private final Comparator<Integer> indexSort = new Comparator<Integer>() {
        @Override
        public int compare(Integer lhs, Integer rhs) {
            if (sort != null) {
                synchronized (sort) {
                    final E elhs = originalItems.get(lhs);
                    final E erhs = originalItems.get(rhs);
                    return sort.compare(elhs, erhs);
                }
            } else {
                return lhs - rhs;
            }
        }
    };
    private Comparator<E> sort;

    public GenericRecyclerAdapter(Context context, Collection<E> items) {
        this.context = context;
        originalItems = new ArrayList<>(items);
        refresh();
    }

    public final Context getContext() {
        return context;
    }

    @Override
    public ViewWrapper onCreateViewHolder(ViewGroup parent, int viewType) {
        CellView<E> cellView = onCreateItemView(parent, viewType);
        cellView.setGenericAdapter(this);
        return new ViewWrapper(cellView);
    }

    @Override
    public void onBindViewHolder(ViewWrapper viewWrapper, int position) {
        if (indexs.size() > position) {
            final E e = getItem(position);
            viewWrapper.getView().onBindViewHolder(e);
            viewWrapper.getView().bind(e);
        }
        if (getItemClickable(position)) {
            viewWrapper.getView().onItemClickable(true);
        } else {
            viewWrapper.getView().onItemClickable(false);
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
                refresh();
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
            refresh();
        } catch (Exception e) {
        } finally {
        }
    }

    @Override
    public final void add(E item) {
        try {
            final boolean b = originalItems.add(item);
            if (b && filter.filter(item)) {
                refresh();
            }
        } catch (Exception e) {
        } finally {
        }
    }

    @Override
    public final void set(int index, E item) {
        try {
            originalItems.set(index, item);
            refresh();
        } catch (Exception e) {
        } finally {
        }
    }

    @Override
    public final void remove(E item) {
        try {
            originalItems.remove(item);
            refresh();
        } catch (Exception e) {
        } finally {
        }
    }

    @Override
    public final E remove(int position) {
        try {
            E e = originalItems.remove(position);
            refresh();
            return e;
        } catch (Exception e) {
        } finally {
        }
        return null;
    }

    @Override
    public final void clear() {
        originalItems.clear();
        refresh();
    }

    @Override
    public final void setFilter(Filter<E> filter) {
        this.filter = filter;
        refresh();
    }

    @Override
    public final void setSort(Comparator<E> sort) {
        this.sort = sort;
        refresh();
    }

    @Override
    public final void refresh() {
        for (int i = 0; i < indexs.size(); i++) {
            notifyItemRemoved(i);
        }
        onRefreshIndexs();
        notifyDataSetChanged();
    }

    protected void onRefreshIndexs() {
        indexs.clear();
        for (int index = 0; index < originalItems.size(); index++) {
            final E originalItem = originalItems.get(index);
            if (filter.filter(originalItem)) {
                if (!indexs.contains(index)) {
                    indexs.add(index);
                }
            }
        }
        Collections.sort(indexs, indexSort);
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
