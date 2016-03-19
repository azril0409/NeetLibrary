package app.neetoffice.com.genericadaptersample;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Deo on 2016/3/16.
 */
public class RecyclerGridActivity extends RecyclerListActivity {

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);
    }
}
