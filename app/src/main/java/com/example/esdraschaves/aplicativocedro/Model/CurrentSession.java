package com.example.esdraschaves.aplicativocedro.Model;

/**
 * Created by Esdras Chaves on 25/02/2018.
 */

public class CurrentSession {
    private volatile static CurrentSession instance;

    private String email;
    private String token;

    private CurrentSession() {}

    public static CurrentSession getInstance() {
        if(instance == null) {
            synchronized (CurrentSession.class) {
                if(instance == null) {
                    instance = new CurrentSession();
                }
            }
        }
        return instance;
    }

    public void newSession(String email, String token) {
        this.setEmail(email);
        this.setToken(token);
    }

    private void setEmail(String email) {
        this.email = email;
    }

    private void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return this.email;
    }

    public String getToken() {
        return this.token;
    }
}
