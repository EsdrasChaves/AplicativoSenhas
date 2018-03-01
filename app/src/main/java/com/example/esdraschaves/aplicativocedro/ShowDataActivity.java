package com.example.esdraschaves.aplicativocedro;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.esdraschaves.aplicativocedro.DAO.AccountDAO;
import com.example.esdraschaves.aplicativocedro.Model.Account;
import com.example.esdraschaves.aplicativocedro.Model.CurrentSession;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.Serializable;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ShowDataActivity extends AppCompatActivity {

    ImageView image_logo;
    TextView text_url;
    TextView text_user;
    TextView text_password;
    ImageButton hide_password;
    ImageButton copy_password;
    Button edit_account;
    Button delete_account;
    Boolean passwordVisible;

    Account account;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);

        image_logo = (ImageView) findViewById(R.id.image_web_logo);
        text_url = (TextView) findViewById(R.id.text_url_data);
        text_user = (TextView) findViewById(R.id.text_user_data);
        text_password = (TextView) findViewById(R.id.text_password_data);
        hide_password = (ImageButton) findViewById(R.id.image_viewPassword);
        copy_password = (ImageButton) findViewById(R.id.image_copyPassword);
        edit_account = (Button) findViewById(R.id.button_edit_account);
        delete_account= (Button) findViewById(R.id.button_delete_account);

        account = (Account) getIntent().getSerializableExtra("Selected_Account");

        passwordVisible = false;

        if(account != null) {
            setDataOnScreen();
        }

    }


    public void setDataOnScreen() {
        text_url.setText(account.getUrl());
        text_user.setText(account.getUser());
        text_password.setText(account.getPassword());

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request newRequest = chain.request().newBuilder()
                                .addHeader("authorization", CurrentSession.getInstance().getToken())
                                .build();
                        return chain.proceed(newRequest);
                    }
                })
                .build();

        Picasso picasso = new Picasso.Builder(this)
                .downloader(new OkHttp3Downloader(client))
                .build();

        String PATH = "https://dev.people.com.ai/mobile/api/v2/logo/" + account.getUrl();

        picasso.load(PATH).into(image_logo);
    }

    public void setAccount(Account account) {
        this.account = account;
    }



    public void viewPassword(View view) {
        if (!passwordVisible) {
            text_password
                    .setTransformationMethod(SingleLineTransformationMethod
                            .getInstance());
            passwordVisible = true;
        } else {
            text_password
                    .setTransformationMethod(PasswordTransformationMethod
                            .getInstance());
            passwordVisible = false;
        }
    }



    public void copyToClipboard(View view) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("password", text_password.getText().toString());
        clipboard.setPrimaryClip(clip);

        Toast toast = Toast.makeText(this, "Senha copiada para Área de Transferência", Toast.LENGTH_SHORT);
        toast.show();

    }

    public void updateAccount(View view) {
        Intent form = new Intent(ShowDataActivity.this, FormAccountActivity.class);

        form.putExtra("SELECTED_ACCOUNT", account);

        startActivity(form);

        finish();
    }

    public void deleteAccount(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Confirma a exclusão de: " + account.getUrl());

        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AccountDAO dao = new AccountDAO(ShowDataActivity.this);
                dao.delete(account);
                dao.close();
                finish();
            }
        });

        builder.setNegativeButton("Não", null);
        AlertDialog dialog = builder.create();
        dialog.setTitle("Confirmação de Operação");
        dialog.show();
    }


}
