package com.example.esdraschaves.aplicativocedro;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.esdraschaves.aplicativocedro.Adapter.AdapterAccounts;
import com.example.esdraschaves.aplicativocedro.DAO.AccountDAO;
import com.example.esdraschaves.aplicativocedro.Model.Account;
import com.example.esdraschaves.aplicativocedro.Model.CurrentSession;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<Account> listAccounts;
    RecyclerView recyclerAccounts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        CurrentSession c = CurrentSession.getInstance();


        listAccounts = new ArrayList<Account>();
        recyclerAccounts = findViewById(R.id.RecyclerId);
        recyclerAccounts.setLayoutManager(new LinearLayoutManager(this));

        recoverData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.recoverData();
    }

    public void newAccount(View view) {
        Intent intent = new Intent(this, FormAccountActivity.class);
        startActivity(intent);
    }

    public void recoverData() {
        // Adiciona as contas

        AccountDAO dao = new AccountDAO(this);
        this.listAccounts = dao.list(CurrentSession.getInstance().getEmail());

        AdapterAccounts adapter = new AdapterAccounts(listAccounts);
        recyclerAccounts.setAdapter(adapter);


    }

}
