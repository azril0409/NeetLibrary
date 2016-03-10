package library.neetoffice.com.genericadapter;

import android.widget.AbsListView;

public interface OnScrollCallBack {
	void onScroll(AbsListView view, int position, int count);

	void onScrollend(AbsListView view);

}
