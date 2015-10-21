package library.neetoffice.com.adapters.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Deo-chainmeans on 2015/8/4.
 */
public abstract class GenericAdapter<E, T> extends BaseAdapter implements GenericAdapterInterface<E>{
    private Context context;
    private ArrayList<E> items;
    private int layoutId;

    public GenericAdapter(Context context, Collection<E> items, int layoutId) {
        this.context = context;
        this.items = new ArrayList<>(items);
        this.layoutId = layoutId;
    }

    public final Context getContext() {
        return context;
    }

    public final LayoutInflater getLayoutInflater() {
        return LayoutInflater.from(context);
    }

    @Override
    public final void addAll(Collection<E> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public final void setAll(Collection<E> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public final void add(E item) {
        items.add(item);
        notifyDataSetChanged();
    }

    @Override
    public final void set(int index, E item) {
        try {
            items.set(index, item);
        }catch (Exception e){

        }finally {
            notifyDataSetChanged();
        }
    }

    @Override
    public final void remove(E item) {
        try {
            items.remove(item);
        }catch (Exception e){

        }finally {
            notifyDataSetChanged();
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
    public int getCount() {
        return items.size();
    }

    @Override
    public E getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        T t;
        if (convertView == null) {
            convertView = getLayoutInflater().inflate(layoutId, null);
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
