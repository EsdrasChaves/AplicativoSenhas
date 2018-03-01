package com.example.esdraschaves.aplicativocedro.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.Serializable;

/**
 * Created by Esdras Chaves on 27/02/2018.
 */

public class Account implements Serializable{


    private Long id;
    private String owner;
    private Bitmap siteLogo;
    private String url;
    private String user;
    private String password;


    public Account() {

    }

    public Account(Bitmap siteLogo, String url, String user, String password) {
        this.siteLogo = siteLogo;
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setSiteLogo(Bitmap foto) {
        this.siteLogo = siteLogo;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getOwner() {
        return owner;
    }

    public Bitmap getSiteLogo() { return siteLogo; }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() { return password; }

    public Long getId() { return id; }
}
