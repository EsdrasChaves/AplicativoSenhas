package com.example.esdraschaves.aplicativocedro.Helper;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.EditText;
import android.widget.TextView;

import com.example.esdraschaves.aplicativocedro.FormAccountActivity;
import com.example.esdraschaves.aplicativocedro.Model.Account;
import com.example.esdraschaves.aplicativocedro.Model.CurrentSession;
import com.example.esdraschaves.aplicativocedro.R;
import com.example.esdraschaves.aplicativocedro.Utils.EncryptDecrypt;

import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;

/**
 * Created by Esdras Chaves on 27/02/2018.
 */

public class FormHelper {
    private EditText webURL;
    private EditText userName;
    private EditText passWord;
    private TextView errorMsg;
    private CurrentSession session;
    private Context context;

    private Account account;


    public FormHelper(FormAccountActivity activity) {
        webURL = (EditText) activity.findViewById(R.id.edit_url);
        userName = (EditText) activity.findViewById(R.id.edit_userName);
        passWord = (EditText) activity.findViewById(R.id.edit_userPassword);
        errorMsg = (TextView) activity.findViewById(R.id.f_text_error);

        this.context = activity;

        session = CurrentSession.getInstance();

        account = new Account();
    }

    public void setAccount(Account account) {
        webURL.setText(account.getUrl());
        userName.setText(account.getUser());
        passWord.setText(account.getPassword());

        this.account = account;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public Account getAccount() {

        if(webURL.getText().toString().matches("") || userName.getText().toString().matches("") || passWord.getText().toString().matches("")) {
            errorMsg.setText("Todos os campos devem ser preenchidos");
            return null;
        }


        account.setOwner(session.getEmail());
        account.setUrl(webURL.getText().toString());
        account.setUser(userName.getText().toString());
        account.setPassword(passWord.getText().toString());

        return account;

    }


}
