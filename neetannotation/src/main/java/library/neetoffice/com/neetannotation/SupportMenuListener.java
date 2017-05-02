package library.neetoffice.com.neetannotation;

import android.app.Activity;
import android.view.MenuItem;

/**
 * Created by Deo-chainmeans on 2017/5/2.
 */

class SupportMenuListener implements android.support.v7.widget.Toolbar.OnMenuItemClickListener {
    final Activity a;

    SupportMenuListener(Activity a) {
        this.a = a;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return a.onOptionsItemSelected(item);
    }
}
