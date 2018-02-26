package com.example.esdraschaves.aplicativocedro.Rest;

import com.example.esdraschaves.aplicativocedro.Model.CredentialResponse;
import com.example.esdraschaves.aplicativocedro.Model.UserInfo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Esdras Chaves on 25/02/2018.
 */

public interface APIService {

    String BASE_URL = "https://dev.people.com.ai/mobile/api/v2/";

    @Headers("Content-Type: application/json")
    @POST("login")
    Call<CredentialResponse> login(@Body UserInfo userInfo);


    @Headers("Content-Type: application/json")
    @POST("register")
    Call<CredentialResponse> register(@Body UserInfo userInfo);


}
