package com.example.esdraschaves.aplicativocedro.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Esdras Chaves on 25/02/2018.
 */

public class CredentialResponse {
    private String type;
    private String message;
    private String token;
    private List<String> errors;

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }

    public List<String> getErrors() {
        return errors;
    }

    public CredentialResponse(String type, String message, String token, List<String> errors) {
        this.type = type;
        this.message = message;
        this.token = token;
        this.errors = errors;

    }

    public void printALL() {
        System.out.println("Type: " + type + "\nMessage: " + message + "\nToken: " + token);
    }

}
