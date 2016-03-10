package library.neetoffice.com.genericadapter.base;

import android.content.Context;
import android.widget.AbsListView;

import java.util.Collection;

/**
 * Created by Deo-chainmeans on 2015/8/4.
 */
public abstract class GenericScrollAdapter<E,T> extends GenericAdapter<E,T> {

    public static interface OnScrollCallBack {
        public void onScrollend(AbsListView view);
    }

    public GenericScrollAdapter(Context context, Collection<E> items, int layoutId) {
        super(context, items, layoutId);
    }

    public final AbsListView.OnScrollListener getOnScrollListener(OnScrollCallBack callBack) {
        return new Listener(callBack);
    }

    private class Listener implements AbsListView.OnScrollListener {
        private int firstVisibleItem;
        private int visibleItemCount;
        private int totalItemCount;
        private OnScrollCallBack callBack;

        private Listener(OnScrollCallBack callBack) {
            this.callBack = callBack;
        }

        public void onScroll(AbsListView view, int f, int v, int t) {
            firstVisibleItem = f;
            visibleItemCount = v;
            totalItemCount = t;
        }

        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (scrollState == Listener.SCROLL_STATE_IDLE& (firstVisibleItem + visibleItemCount + 1 >= totalItemCount)) {
                callBack.onScrollend(view);
            }
        }
    }
}
