package com.example.esdraschaves.aplicativocedro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.esdraschaves.aplicativocedro.Model.CredentialResponse;
import com.example.esdraschaves.aplicativocedro.Model.CurrentSession;
import com.example.esdraschaves.aplicativocedro.Model.UserInfo;
import com.example.esdraschaves.aplicativocedro.Rest.APIService;
import com.example.esdraschaves.aplicativocedro.Utils.CredentialsValidation;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    private TextView errorMsg;
    private EditText nameField;
    private EditText emailField;
    private EditText passwordField;
    private Button registerButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        errorMsg = (TextView) findViewById(R.id.r_text_error);
        nameField = (EditText) findViewById(R.id.r_edit_name);
        emailField = (EditText) findViewById(R.id.r_edit_email);
        passwordField = (EditText) findViewById(R.id.r_edit_password);
        registerButton = (Button) findViewById(R.id.r_button_register);

    }

    public void registerApp(View view) {

        CredentialsValidation valid;

        String name = nameField.getText().toString();
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();


        if((name.matches("")) || (email.matches("")) || password.matches("")) {
            registerFailed("Todos os campos devem ser preenchidos");
        }else {

            valid = new CredentialsValidation();

            if(valid.validateEmail(email) &&  valid.validatePassword(password)) {
                UserInfo userInfo = new UserInfo(email, name, password);

                validateRegister(userInfo);
            } else {
                registerFailed("Email ou Senha Inválidos");
            }
        }

    }

    public void validateRegister(final UserInfo userInfo) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService client = retrofit.create(APIService.class);

        Call<CredentialResponse> call = client.register(userInfo);

        call.enqueue(new Callback<CredentialResponse>() {
            @Override
            public void onResponse(Call<CredentialResponse> call, Response<CredentialResponse> response) {
                if((response.code() == 400) || (!response.isSuccessful())) {
                    JSONObject jsonObject = null;

                    try {

                        jsonObject = new JSONObject(response.errorBody().string());
                        String errorMessage = jsonObject.getString("message");
                        registerFailed("Usuário já cadastrado");

                    }catch (JSONException e) {
                        e.printStackTrace();
                    }catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    CredentialResponse result = (CredentialResponse) response.body();
                    registerSuccess(userInfo.getEmail(), result.getToken());
                }
            }

            @Override
            public void onFailure(Call<CredentialResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void registerSuccess(String email, String token) {

        CurrentSession.getInstance().newSession(email, token);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void registerFailed(String message) {
        errorMsg.setText(message);
    }



}
