package library.neetoffice.com.genericadapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Deo on 2015/12/7.
 */
public class StickyHeaderDecoration<E> extends NormalHeaderDecoration<E> {

    public StickyHeaderDecoration(StickyHeaderAdapter adapter) {
        super(adapter);
    }

    public StickyHeaderDecoration(StickyHeaderAdapter adapter, boolean renderInline) {
        super(adapter, renderInline);
    }

    @Override
    protected int getHeaderTop(RecyclerView parent, View child, View header, int adapterPos, int layoutPos) {
        int top = super.getHeaderTop(parent, child, header, adapterPos, layoutPos);
        int headerHeight = getHeaderHeightForLayout(header);
        if (layoutPos == 0) {
            final int count = parent.getChildCount();
            final long currentId = mAdapter.getHeaderId(adapterPos);
            // find next view with header and compute the offscreen push if needed
            for (int i = 1; i < count; i++) {
                int adapterPosHere = parent.getChildAdapterPosition(parent.getChildAt(i));
                if (adapterPosHere != RecyclerView.NO_POSITION) {
                    long nextId = mAdapter.getHeaderId(adapterPosHere);
                    if (nextId != currentId) {
                        final View next = parent.getChildAt(i);
                        final int offset;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
                            offset = ((int) next.getY()) - (headerHeight + getHeader(parent, adapterPosHere).itemView.getHeight());
                        } else {
                            offset = next.getTop() - (headerHeight + getHeader(parent, adapterPosHere).itemView.getHeight());
                        }
                        if (offset < 0) {
                            return offset;
                        } else {
                            break;
                        }
                    }
                }
            }

            top = Math.max(0, top);
        }
        return top;

    }
}
