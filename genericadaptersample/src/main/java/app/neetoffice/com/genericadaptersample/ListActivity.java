package app.neetoffice.com.genericadaptersample;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

import library.neetoffice.com.genericadapter.base.Filter;
import library.neetoffice.com.genericadapter.base.GenericAdapter;

/**
 * Created by Deo on 2016/2/25.
 */
public class ListActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener, TextWatcher, Filter<String> {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        EditText editText = (EditText) findViewById(R.id.editText);
        ListView listView = (ListView) findViewById(R.id.listView);
        toolbar.inflateMenu(R.menu.menu_list);
        toolbar.setOnMenuItemClickListener(this);
        adapter = new Adapter(this, Arrays.asList(getResources().getStringArray(R.array.items)));
        adapter.setFilter(this);
        listView.setAdapter(adapter);
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
        if(text==null){
            return true;
        }
        return item.contains(text);
    }

    private static class Tag {
        TextView textView;
    }

    private static class Adapter extends GenericAdapter<String, Tag> {

        public Adapter(Context context, Collection<String> items) {
            super(context, items, R.layout.cell_string);
        }

        @Override
        public Tag onCreateTag(View convertView) {
            final Tag tag = new Tag();
            tag.textView = (TextView) convertView.findViewById(R.id.textView);
            return tag;
        }

        @Override
        public void onBind(Tag tag, int position) {
            final String text = getItem(position);
            tag.textView.setText(text);
        }
    }
}
