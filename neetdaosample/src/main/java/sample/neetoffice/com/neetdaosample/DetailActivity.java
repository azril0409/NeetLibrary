package sample.neetoffice.com.neetdaosample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

import library.neetoffice.com.neetannotation.Bean;
import library.neetoffice.com.neetannotation.Click;
import library.neetoffice.com.neetannotation.Extra;
import library.neetoffice.com.neetannotation.NActivity;
import library.neetoffice.com.neetannotation.AnnotationHelp;
import library.neetoffice.com.neetannotation.SaveInstance;
import library.neetoffice.com.neetannotation.ViewById;

/**
 * Created by Deo on 2016/3/18.
 */
@NActivity(R.layout.activity_new)
public class DetailActivity extends AppCompatActivity {
    @ViewById(R.id.editText1)
    EditText editText1;
    @ViewById(R.id.editText2)
    EditText editText2;
    @Bean
    DaoHelper daoHelper;
    @Extra("MODEL")
    @SaveInstance
    Model model;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AnnotationHelp.onCreate(this, savedInstanceState);
        editText1.setText(model.getTitle());
        editText2.setText(model.getMessage());
        Toast.makeText(this,model.getMessage(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        AnnotationHelp.onSaveInstanceState(this, outState);
        Toast.makeText(this,model.getMessage(),Toast.LENGTH_SHORT).show();
    }

    @Click(R.id.button1)
    public void onClickSend() {
        final String title = editText1.getText().toString();
        final String message = editText2.getText().toString();
        model.setTitle(title);
        model.setMessage(message);
        final long start = Calendar.getInstance().getTimeInMillis();
        final long l = daoHelper.getModelDao().insertOrReplace(model);
        final long end = Calendar.getInstance().getTimeInMillis();
        Toast.makeText(this, (l > 0 ? "success" : "filed") + " Time : " + (end - start), Toast.LENGTH_SHORT).show();
        finish();
    }
}
