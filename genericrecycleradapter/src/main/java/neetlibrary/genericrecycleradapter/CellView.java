package neetlibrary.genericrecycleradapter;

import android.content.Context;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.FrameLayout;

import library.neetoffice.com.adapters.base.GenericAdapterInterface;

/**
 * Created by Deo-chainmeans on 2015/6/8.
 */
public abstract class CellView<E> extends FrameLayout {
    protected E data;
    private GenericAdapterInterface<E> genericAdapter;

    public CellView(Context context) {
        super(context);
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }

    public void onItemClickable(boolean clickable) {
        if (clickable) {
            setOnClickListener(new Listener(data));
        } else {
            setOnClickListener(null);
        }
    }

    public void onItemClick(View cellView, E data) {
    }

    public final GenericAdapterInterface<E> getGenericAdapter() {
        return genericAdapter;
    }

    final void setGenericAdapter(GenericAdapterInterface<E> genericAdapter) {
        this.genericAdapter = genericAdapter;
    }

    public final E getData() {
        return data;
    }

    final void onBindViewHolder(E data) {
        this.data = data;
    }

    public void bind(E e) {
    }

    public String getString(@StringRes int resId) {
        return getContext().getString(resId);
    }

    public String getString(@StringRes int resId, Object... formatArgs) {
        return getContext().getString(resId, formatArgs);
    }

    private class Listener implements OnClickListener {
        private E data;

        private Listener(E data) {
            this.data = data;
        }

        @Override
        public void onClick(View v) {
            onItemClick(v, data);
        }
    }
}
