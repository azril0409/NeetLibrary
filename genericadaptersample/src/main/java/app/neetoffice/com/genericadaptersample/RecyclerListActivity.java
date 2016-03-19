package app.neetoffice.com.genericadaptersample;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

import library.neetoffice.com.genericadapter.NormalHeaderDecoration;
import library.neetoffice.com.genericadapter.StickyHeaderAdapter;
import library.neetoffice.com.genericadapter.StickyHeaderDecoration;
import library.neetoffice.com.genericadapter.base.Filter;
import library.neetoffice.com.genericadapter.CellView;
import library.neetoffice.com.genericadapter.GenericRecyclerAdapter;

/**
 * Created by Deo on 2016/2/25.
 */
public class RecyclerListActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener, TextWatcher, Filter<String> {

    private Comparator<String> desc = new Comparator<String>() {
        @Override
        public int compare(String lhs, String rhs) {
            return Integer.valueOf(lhs) - Integer.valueOf(rhs);
        }
    };
    private Comparator<String> asc = new Comparator<String>() {
        @Override
        public int compare(String lhs, String rhs) {
            return Integer.valueOf(rhs) - Integer.valueOf(lhs);
        }
    };
    private Adapter adapter;
    private String text;

    protected RecyclerView.LayoutManager getLayoutManager(){
        return new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerlist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        EditText editText = (EditText) findViewById(R.id.editText);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        toolbar.inflateMenu(R.menu.menu_list);
        toolbar.setOnMenuItemClickListener(this);
        adapter = new Adapter(this, Arrays.asList(getResources().getStringArray(R.array.items)));
        adapter.setFilter(this);
        recyclerView.setLayoutManager(getLayoutManager());
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new NormalHeaderDecoration<String>(adapter));
        editText.addTextChangedListener(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.desc) {
            if (adapter != null) {
                adapter.setSort(desc);
                return true;
            }
        } else if (item.getItemId() == R.id.asc) {
            if (adapter != null) {
                adapter.setSort(asc);
                return true;
            }
        }
        return false;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        text = s.toString();
        adapter.refresh();
    }

    @Override
    public boolean filter(String item) {
        if (text == null) {
            return true;
        }
        return item.contains(text);
    }

    private static class SrtingCell extends CellView<String> {
        private TextView textView;

        public SrtingCell(Context context) {
            super(context);
            inflate(context, R.layout.cell_string, this);
            textView = (TextView) findViewById(R.id.textView);
        }

        @Override
        public void bind(String s) {
            super.bind(s);
            textView.setText(s);
        }
    }

    private static class Adapter extends GenericRecyclerAdapter<String> implements StickyHeaderAdapter {

        public Adapter(Context context, Collection<String> items) {
            super(context, items);
        }

        @Override
        public CellView<String> onCreateItemView(ViewGroup parent, int viewType) {
            return new SrtingCell(getContext());
        }

        @Override
        public long getHeaderId(int position) {
            return getItem(position).charAt(0);
        }

        @Override
        public View onCreateHeaderViewHolder(ViewGroup parent) {
            SrtingCell cell = new SrtingCell(getContext());
            return cell;
        }

        @Override
        public void onBindHeaderViewHolder(View viewholder, int position) {
            long id = getHeaderId(position);
            SrtingCell cell = (SrtingCell) viewholder;
            cell.setBackgroundColor(Color.BLUE);
            cell.bind(String.valueOf((char)id));
        }
    }
}
