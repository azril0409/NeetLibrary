package library.neetoffice.com.neetdao;

import android.database.sqlite.SQLiteDatabase;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by Deo on 2016/3/7.
 */
public interface QueryBuilder<E> extends QueryBuilderOrder<E>{

    QueryBuilderOrder<E> where(Where... wheres);
}
