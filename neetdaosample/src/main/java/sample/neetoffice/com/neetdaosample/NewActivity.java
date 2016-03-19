package sample.neetoffice.com.neetdaosample;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

import library.neetoffice.com.neetannotation.Bean;
import library.neetoffice.com.neetannotation.Click;
import library.neetoffice.com.neetannotation.NActivity;
import library.neetoffice.com.neetannotation.Neet;
import library.neetoffice.com.neetannotation.ViewById;

/**
 * Created by Deo on 2016/3/18.
 */
@NActivity(R.layout.activity_new)
public class NewActivity extends AppCompatActivity {
    @ViewById(R.id.editText1)
    EditText editText1;
    @ViewById(R.id.editText2)
    EditText editText2;
    @Bean
    DaoHelper daoHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Neet.onCreate(this, savedInstanceState);
    }

    @Click(R.id.button1)
    public void onClickSend() {
        final String title = editText1.getText().toString();
        final String message = editText2.getText().toString();
        final Model model = new Model(title, message);
        final long start = Calendar.getInstance().getTimeInMillis();
        final long l = daoHelper.getModelDao().insert(model);
        final long end = Calendar.getInstance().getTimeInMillis();
        Toast.makeText(this, (l > 0 ? "success" : "filed") + " Time : " + (end - start), Toast.LENGTH_SHORT).show();
        finish();
    }
}
