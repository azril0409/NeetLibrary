package neetlibrary.genericrecycleradapter;

import android.widget.AbsListView;

public interface OnScrollCallBack {
	void onScroll(AbsListView view, int position, int count);

	void onScrollend(AbsListView view);

}
