package sample.neetoffice.com.neetdaosample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import library.neetoffice.com.neetannotation.Bean;
import library.neetoffice.com.neetannotation.Click;
import library.neetoffice.com.neetannotation.ItemClick;
import library.neetoffice.com.neetannotation.NActivity;
import library.neetoffice.com.neetannotation.AnnotationHelp;
import library.neetoffice.com.neetannotation.Pref;
import library.neetoffice.com.neetannotation.RestService;
import library.neetoffice.com.neetannotation.ViewById;
import library.neetoffice.com.neetdao.Where;
import library.nettoffice.com.restapi.ResponseCallBack;

@NActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    @ViewById(value = R.id.editText1)
    EditText editText;
    @ViewById(value = R.id.listView)
    ListView listView;
    @Bean
    DaoHelper daoHelper;
    @Bean
    ModelAdapter modelAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AnnotationHelp.onCreate(this, savedInstanceState);
        listView.setAdapter(modelAdapter);
    }

    @Click(R.id.button1)
    void onClickNew(View view) {
        final Intent intent = new Intent(this, NewActivity.class);
        startActivity(intent);
    }

    @Click(R.id.button2)
    void onClickSearch() {
        final String text = editText.getText().toString();
        if (text.isEmpty()) {
            final long start = Calendar.getInstance().getTimeInMillis();
            final List<Model> list = daoHelper.getModelDao().list();
            final long end = Calendar.getInstance().getTimeInMillis();
            Toast.makeText(this, "Time : " + (end - start) + " msec", Toast.LENGTH_SHORT).show();
            modelAdapter.setAll(list);
        } else {
            final long start = Calendar.getInstance().getTimeInMillis();
            final List<Model> list = daoHelper.getModelDao().queryBuilder().where(Where.like(Model.TITLE, text)).list();
            final long end = Calendar.getInstance().getTimeInMillis();
            Toast.makeText(this, "Time : " + (end - start) + " msec", Toast.LENGTH_SHORT).show();
            modelAdapter.setAll(list);
        }
    }

    @ItemClick(R.id.listView)
    public void onItemClick(Model model) {
        final Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("MODEL", model);
        startActivity(intent);
    }

}
