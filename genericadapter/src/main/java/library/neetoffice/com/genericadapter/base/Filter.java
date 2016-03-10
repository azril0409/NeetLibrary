package library.neetoffice.com.genericadapter.base;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Mac on 2015/10/24.
 */
public interface Filter<E> {

    boolean filter(E item);
}
