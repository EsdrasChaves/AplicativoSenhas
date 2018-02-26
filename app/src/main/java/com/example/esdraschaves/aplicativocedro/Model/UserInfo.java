package com.example.esdraschaves.aplicativocedro.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Esdras Chaves on 25/02/2018.
 */

public class UserInfo {
    String email = null;
    String name = null;
    String password = null;


    public UserInfo(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;

    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }
}
