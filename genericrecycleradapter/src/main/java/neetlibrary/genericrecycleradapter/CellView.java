package neetlibrary.genericrecycleradapter;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by Deo-chainmeans on 2015/6/8.
 */
public abstract class CellView<E> extends FrameLayout {
    protected E e;

    public CellView(Context context) {
        super(context);
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }

    public void bind(E e) {
        this.e = e;
    }

    public void onItemClickable() {
        setOnClickListener(new Listener(e));
    }

    public void onItemClick(View cellView, E e) {
    }

    private class Listener implements OnClickListener {
        private E e;

        private Listener(E e) {
            this.e = e;
        }

        @Override
        public void onClick(View v) {
            onItemClick(v, e);
        }
    }
}
