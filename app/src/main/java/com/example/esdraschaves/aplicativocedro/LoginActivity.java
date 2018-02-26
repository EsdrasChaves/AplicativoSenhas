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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private TextView errorMsg;
    private EditText emailField;
    private EditText passwordField;
    private Button loginButton;
    private TextView registerMsg;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        errorMsg = (TextView) findViewById(R.id.r_text_error);
        emailField = (EditText) findViewById(R.id.r_edit_email);
        passwordField = (EditText) findViewById(R.id.r_edit_password);
        loginButton = (Button) findViewById(R.id.button_login);
        registerMsg = (TextView) findViewById(R.id.text_register);

    }


    // Botão Login - Verifica se os campos login e senha estão vazios e chama o método de login
    public void loginApp(View view) {

        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();
        String toastResposta;


        if((email.matches("") || (password.matches("")))){
            loginFailed("Todos os campos devem ser preenchidos");
        } else {
            UserInfo userInfo = new UserInfo(email, null, password);

            validateLogin(userInfo);
        }

    }


    // Verificação de Login via API, recupera token se sucesso, e menssagem de erro se fracasso. Chama métodos para tratar cada caso
    public void validateLogin(final UserInfo userInfo) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        APIService client = retrofit.create(APIService.class);

        Call<CredentialResponse> call = client.login(userInfo);

        call.enqueue(new Callback<CredentialResponse>() {
            @Override
            public void onResponse(Call<CredentialResponse> call, Response<CredentialResponse> response) {

                if((response.code() == 400) || (!response.isSuccessful())) {
                    JSONObject jsonObject = null;

                    try {

                        jsonObject = new JSONObject(response.errorBody().string());
                        String errorMessage = jsonObject.getString("message");
                        loginFailed(errorMessage);

                    }catch (JSONException e) {
                        e.printStackTrace();
                    }catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    CredentialResponse result = (CredentialResponse) response.body();
                    loginSuccess(userInfo.getEmail(), result.getToken());
                }
            }

            @Override
            public void onFailure(Call<CredentialResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void loginSuccess(String email, String token) {

        CurrentSession.getInstance().newSession(email, token);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    public void loginFailed(String message) {
        errorMsg.setText(message);
    }


    // Cadastro do Usuário
    public void registerApp(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }
}
