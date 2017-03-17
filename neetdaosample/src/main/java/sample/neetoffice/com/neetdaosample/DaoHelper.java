package sample.neetoffice.com.neetdaosample;

import android.content.Context;

import library.neetoffice.com.neetdao.Dao;
import library.neetoffice.com.neetdao.DatabaseHelper;

/**
 * Created by Deo on 2016/3/7.
 */
public class DaoHelper extends DatabaseHelper {
    private static final String NAME = "DEMO";
    private static final int VERSION = 1;

    public DaoHelper(Context context) {
        super(context, NAME,"123test", VERSION, Model.class);
    }

    public Dao<Model> getModelDao() {
        return getDao(Model.class);

    }
}
