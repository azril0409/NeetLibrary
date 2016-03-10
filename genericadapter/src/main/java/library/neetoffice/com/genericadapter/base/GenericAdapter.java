package library.neetoffice.com.genericadapter.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Deo-chainmeans on 2015/8/4.
 */
public abstract class GenericAdapter<E, T> extends BaseAdapter implements GenericAdapterInterface<E> {
    private Context context;
    private int layoutId;
    private ArrayList<E> originalItems = new ArrayList<>();
    private ArrayList<Integer> indexs = new ArrayList<>();
    private Filter<E> filter = new Filter<E>() {

        @Override
        public boolean filter(E item) {
            return true;
        }
    };
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

    public GenericAdapter(Context context, Collection<E> items, int layoutId) {
        this.context = context;
        this.layoutId = layoutId;
        this.originalItems.addAll(items);
        refresh();
    }

    public final Context getContext() {
        return context;
    }

    public final LayoutInflater getLayoutInflater() {
        return LayoutInflater.from(context);
    }

    @Override
    public final void addAll(Collection<E> items) {
        int originalSize = originalItems.size();
        originalItems.addAll(items);
        for (int index = originalSize; index < originalItems.size(); index++) {
            E originalItem = originalItems.get(index);
            if (filter.filter(originalItem)) {
                indexs.add(index);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public final void setAll(Collection<E> items) {
        this.originalItems.clear();
        this.originalItems.addAll(items);
        refresh();
        notifyDataSetChanged();
    }

    @Override
    public final void add(E item) {
        final boolean b = originalItems.add(item);

        if (b && filter.filter(item)) {
            indexs.add(originalItems.size() - 1);
            notifyDataSetChanged();
        }
    }

    @Override
    public final void set(int index, E item) {
        try {
            originalItems.set(index, item);
            if (filter.filter(item)) {
                if (!indexs.contains(index)) {
                    indexs.add(index);
                    Collections.sort(indexs, indexSort);
                }
            } else {
                if (indexs.contains(index)) {
                    indexs.remove(indexs.indexOf(index));
                    Collections.sort(indexs, indexSort);
                }
            }
        } catch (Exception e) {

        } finally {
            notifyDataSetChanged();
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
        indexs.clear();
        notifyDataSetChanged();
    }

    @Override
    public final void setFilter(Filter<E> filter) {
        this.filter = filter;
        refresh();
    }

    @Override
    public void setSort(Comparator<E> sort) {
        this.sort = sort;
        refresh();
    }

    @Override
    public void refresh() {
        indexs.clear();
        for (int index = 0; index < this.originalItems.size(); index++) {
            E originalItem = this.originalItems.get(index);
            if (filter.filter(originalItem)) {
                indexs.add(index);
            }
        }
        Collections.sort(indexs, indexSort);
        notifyDataSetChanged();
    }

    @Override
    public final List<E> getItems() {
        return originalItems;
    }

    @Override
    public int getCount() {
        return indexs.size();
    }

    @Override
    public E getItem(int position) {
        return originalItems.get(indexs.get(position));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        T t;
        if (convertView == null) {
            convertView = getLayoutInflater().inflate(layoutId, null, false);
            t = onCreateTag(convertView);
            convertView.setTag(t);
        } else {
            t = (T) convertView.getTag();
        }
        onBind(t, position);
        return convertView;
    }

    public abstract T onCreateTag(View convertView);

    public abstract void onBind(T tag, int position);
}
