package library.neetoffice.com.genericadapter;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Deo on 2015/12/7.
 */
public interface StickyHeaderAdapter {
    /**
     * Returns the header id for the item at the given position.
     *
     * @param position the item position
     * @return the header id
     */
    long getHeaderId(int position);

    /**
     * Creates a new header ViewHolder.
     *
     * @param parent the header's view parent
     * @return a view holder for the created view
     */
    View onCreateHeaderViewHolder(ViewGroup parent);

    /**
     * Updates the header view to reflect the header data for the given position
     * @param viewholder the header view holder
     * @param position the header's item position
     */
    void onBindHeaderViewHolder(View viewholder, int position);
}
