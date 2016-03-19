package library.neetoffice.com.genericadapter;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Deo on 2016/3/8.
 */
public class NormalHeaderDecoration<E> extends RecyclerView.ItemDecoration {
    private Map<Long, RecyclerView.ViewHolder> mHeaderCache;

    protected StickyHeaderAdapter mAdapter;

    private boolean mRenderInline;

    /**
     * @param adapter the sticky header adapter to use
     */
    public NormalHeaderDecoration(StickyHeaderAdapter adapter) {
        this(adapter, false);
    }

    /**
     * @param adapter the sticky header adapter to use
     */
    public NormalHeaderDecoration(StickyHeaderAdapter adapter, boolean renderInline) {
        mAdapter = adapter;
        mHeaderCache = new HashMap<>();
        mRenderInline = renderInline;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);

        int headerHeight = 0;
        if (position != RecyclerView.NO_POSITION && hasHeader(parent, position)) {
            Log.d("TAG,", position + " hasHeader");
            View header = getHeader(parent, position).itemView;
            headerHeight = getHeaderHeightForLayout(header);
        }
        outRect.set(0, headerHeight, 0, 0);
    }

    /**
     * Clears the header view cache. Headers will be recreated and
     * rebound on list scroll after this method has been called.
     */
    public void clearHeaderCache() {
        mHeaderCache.clear();
    }

    private boolean hasHeader(RecyclerView parent, int position) {
        if (position == 0) {
            return true;
        }
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            final int spanCount = gridLayoutManager.getSpanCount();
            if (position < spanCount) {
                return true;
            }
            if (spanCount > 1) {
                return false;
            }
            int previous = position - 1;
            return mAdapter.getHeaderId(position) != mAdapter.getHeaderId(previous);
        } else {
            int previous = position - 1;
            return mAdapter.getHeaderId(position) != mAdapter.getHeaderId(previous);
        }
    }

    private class Holder extends RecyclerView.ViewHolder {

        public Holder(View itemView) {
            super(itemView);
        }
    }

    protected RecyclerView.ViewHolder getHeader(RecyclerView parent, int position) {
        final long key = mAdapter.getHeaderId(position);

        if (mHeaderCache.containsKey(key)) {
            return mHeaderCache.get(key);
        } else {
            final View header = mAdapter.onCreateHeaderViewHolder(parent);
            final Holder holder = new Holder(header);
            //noinspection unchecked
            mAdapter.onBindHeaderViewHolder(header, position);

            int widthSpec = View.MeasureSpec.makeMeasureSpec(parent.getWidth(), View.MeasureSpec.EXACTLY);
            int heightSpec = View.MeasureSpec.makeMeasureSpec(parent.getHeight(), View.MeasureSpec.UNSPECIFIED);

            int childWidth = ViewGroup.getChildMeasureSpec(widthSpec,
                    parent.getPaddingLeft() + parent.getPaddingRight(), header.getLayoutParams().width);
            int childHeight = ViewGroup.getChildMeasureSpec(heightSpec,
                    parent.getPaddingTop() + parent.getPaddingBottom(), header.getLayoutParams().height);

            header.measure(childWidth, childHeight);
            header.layout(0, 0, header.getMeasuredWidth(), header.getMeasuredHeight());
            mHeaderCache.put(key, holder);

            return holder;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        final int count = parent.getChildCount();

        for (int layoutPos = 0; layoutPos < count; layoutPos++) {
            final View child = parent.getChildAt(layoutPos);

            final int adapterPos = parent.getChildAdapterPosition(child);
            Log.d("TAG", "adapterPos = " + adapterPos);

            if (adapterPos != RecyclerView.NO_POSITION && (layoutPos == 0 || hasHeader(parent, adapterPos))) {
                View header = getHeader(parent, adapterPos).itemView;
                c.save();
                final int left = 0;//child.getLeft();
                int top = getHeaderTop(parent, child, header, adapterPos, layoutPos);
                c.translate(left, top);
                header.draw(c);
                c.restore();
            }
        }
    }

    protected int getHeaderTop(RecyclerView parent, View child, View header, int adapterPos, int layoutPos) {
        int headerHeight = getHeaderHeightForLayout(header);
        int top = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            top = ((int) child.getY()) - headerHeight;
        } else {
            top = child.getTop() - headerHeight;
        }

        return top;
    }

    protected int getHeaderHeightForLayout(View header) {
        return mRenderInline ? 0 : header.getHeight();
    }

}
