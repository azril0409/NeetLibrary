package library.neetoffice.com.neetdao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Deo on 2016/3/7.
 */
public interface QueryBuilderResult<E> {

    int count();

    List<E> list();

    E one();

    int delete();


}
