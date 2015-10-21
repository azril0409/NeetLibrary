package neetlibrary.genericrecycleradapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Deo-chainmeans on 2015/6/5.
 */
public class ViewWrapper extends RecyclerView.ViewHolder {
    CellView view;
    public ViewWrapper(CellView itemView) {
        super(itemView);
        view = itemView;
    }

    public CellView getView() {
        return view;
    }
}
