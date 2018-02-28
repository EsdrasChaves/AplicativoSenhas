package com.example.esdraschaves.aplicativocedro.Helper;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.EditText;

import com.example.esdraschaves.aplicativocedro.FormAccountActivity;
import com.example.esdraschaves.aplicativocedro.Model.Account;
import com.example.esdraschaves.aplicativocedro.Model.CurrentSession;
import com.example.esdraschaves.aplicativocedro.R;
import com.example.esdraschaves.aplicativocedro.Utils.EncryptDecrypt;

import java.io.UnsupportedEncodingException;

/**
 * Created by Esdras Chaves on 27/02/2018.
 */

public class FormHelper {
    private EditText webURL;
    private EditText userName;
    private EditText passWord;
    private CurrentSession session;

    private Account account;


    public FormHelper(FormAccountActivity activity) {
        webURL = (EditText) activity.findViewById(R.id.edit_url);
        userName = (EditText) activity.findViewById(R.id.edit_userName);
        passWord = (EditText) activity.findViewById(R.id.edit_userPassword);


        session = CurrentSession.getInstance();

        account = new Account();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public Account getAccount() {

        EncryptDecrypt teste = new EncryptDecrypt();


        // Criptografar AKI.

        account.setOwner(session.getEmail());
        account.setUrl(webURL.getText().toString());
        account.setUser(userName.getText().toString());
        account.setPassword(passWord.getText().toString());


        //Account aux = teste.encryptAccount(account);
        //Account aux2 = teste.decryptAccount(aux);


        return account;

    }


}
