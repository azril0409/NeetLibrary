package library.neetoffice.com.adapters.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Mac on 2015/10/24.
 */
public abstract class Filter<E> {
    private Collection<E> items;
    public final ArrayList<E> init(Collection<E> items) {
        this.items = items;
        ArrayList<E> newItems = new ArrayList<>();
        for(E item:items){
            if(filter(item)){
                newItems.add(item);
            }
        }
        return newItems;
    }

    public ArrayList<E> getItems(){
        if(items==null){
            return new ArrayList<E>();
        }
        return new ArrayList<E>(items);
    }

    public void addAll(Collection<E> items) {

    }

    public void setAll(Collection<E> items) {

    }

    public void add(E item) {

    }

    public void set(int index, E item) {

    }

    public abstract boolean filter(E item);
}
