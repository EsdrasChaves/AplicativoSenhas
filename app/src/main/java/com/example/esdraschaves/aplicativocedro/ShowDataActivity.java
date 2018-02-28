package com.example.esdraschaves.aplicativocedro;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
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

import org.w3c.dom.Text;

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

        passwordVisible = false;

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


}
