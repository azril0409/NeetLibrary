package library.neetoffice.com.genericadapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by Deo on 2016/3/17.
 */
public abstract class GenericStickyHeaderRecyclerAdapter<E, Header> extends GenericRecyclerAdapter<E> {
    private static final int HEADER = -1;
    private static final int ITEM = 0;
    private ArrayList<Header> headerItems = new ArrayList<>();

    public GenericStickyHeaderRecyclerAdapter(Context context, Collection<E> items) {
        super(context, items);
    }

    public GridLayoutManager getGridLayoutManager(final int spanCount, int orientation, boolean reverseLayout) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), spanCount, orientation, reverseLayout);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                final int index = indexs.get(position);
                if (index < 0) {
                    return spanCount;
                } else {
                    return 1;
                }
            }
        });
        return gridLayoutManager;
    }


    @Override
    public int getItemViewType(int position) {
        final int index = indexs.get(position);
        if (index >= 0) {
            return ITEM;
        }
        return HEADER;
    }

    @Override
    public ViewWrapper onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM) {
            CellView<E> cellView = onCreateItemView(parent, viewType);
            cellView.setGenericAdapter(this);
            return new ViewWrapper(cellView);
        } else {
            return new ViewWrapper(onCreateHeaderViewHolder(parent));
        }
    }

    @Override
    public void onBindViewHolder(ViewWrapper viewWrapper, int position) {
        if (indexs.size() > position) {
            final int index = indexs.get(position);
            if (index >= 0) {
                final E e = getItem(position);
                viewWrapper.getView().onBindViewHolder(e);
                viewWrapper.getView().bind(e);
            } else {
                final Header header = headerItems.get(-index - 1);
                viewWrapper.getView().onBindViewHolder(header);
                viewWrapper.getView().bind(header);
            }
        }
        if (getItemClickable(position)) {
            viewWrapper.getView().onItemClickable(true);
        } else {
            viewWrapper.getView().onItemClickable(false);
        }
    }

    public abstract CellView<Header> onCreateHeaderViewHolder(ViewGroup parent);

    public abstract long getHeaderId(int position);

    public abstract Header getHeader(long headerId);

    @Override
    public void onRefreshIndexs() {
        super.onRefreshIndexs();
        if (headerItems == null) {
            headerItems = new ArrayList<>();
        } else {
            headerItems.clear();
        }
        Integer previous = null;
        for (int index = 0; index < indexs.size(); index++) {
            if (previous == null) {
                headerItems.add(getHeader(getHeaderId(index)));
                indexs.add(index, -headerItems.size());
                index++;
            } else {
                long id = getHeaderId(index);
                if (id != getHeaderId(previous)) {
                    headerItems.add(getHeader(id));
                    indexs.add(index, -headerItems.size());
                    index++;
                }
            }
            previous = index;
        }
    }

    @Override
    public E getItem(int position) {
        final int index = indexs.get(position);
        if (index < 0) {
            return null;
        } else {
            return originalItems.get(index);
        }
    }
}
