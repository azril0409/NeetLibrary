package sample.neetoffice.com.neetdaosample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import library.neetoffice.com.neetdao.Where;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private ListView listView;
    private DaoHelper daoHelper;
    private ModelAdapter modelAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.editText);
        listView = (ListView) findViewById(R.id.listView);
        daoHelper = new DaoHelper(this);
        modelAdapter = new ModelAdapter(this);
        listView.setAdapter(modelAdapter);
    }

    public void onClickSave(View view) {
        final String text = editText.getText().toString();
        final Model model = new Model(text);
        final long l = daoHelper.getModelDao().insert(model);
        Toast.makeText(this, l > 0 ? "success" : "filed", Toast.LENGTH_SHORT).show();
        editText.setText("");
    }

    public void onClickSearch(View view) {
        final String text = editText.getText().toString();
        if (text.isEmpty()) {
            final List<Model> list = daoHelper.getModelDao().loadAll();
            modelAdapter.setAll(list);
        } else {
            final List<Model> list = daoHelper.getModelDao().queryBuilder().where(Where.like(Model.TEXT, text)).list();
            modelAdapter.setAll(list);
        }
    }
}
