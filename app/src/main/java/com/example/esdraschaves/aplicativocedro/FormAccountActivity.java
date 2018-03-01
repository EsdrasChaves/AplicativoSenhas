package com.example.esdraschaves.aplicativocedro;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

import com.example.esdraschaves.aplicativocedro.DAO.AccountDAO;
import com.example.esdraschaves.aplicativocedro.Helper.FormHelper;
import com.example.esdraschaves.aplicativocedro.Model.Account;

public class FormAccountActivity extends AppCompatActivity {

    Button buttonDone;
    FormHelper helper;
    Account account = null;
    android.support.v7.widget.Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_account);

        helper = new FormHelper(this);

        account = (Account) getIntent().getSerializableExtra("SELECTED_ACCOUNT");

        if(account != null) {
            helper.setAccount(account);
        }


        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar2);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void finishedForm(View view) {

        Account account = helper.getAccount();

        AccountDAO dao = new AccountDAO(FormAccountActivity.this);

        if(account.getId() == null) {
            dao.register(account);
        }else {
            dao.update(account);
        }

        dao.close();

        finish();
    }

}
