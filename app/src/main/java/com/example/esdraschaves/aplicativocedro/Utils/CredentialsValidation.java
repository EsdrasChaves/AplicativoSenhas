package com.example.esdraschaves.aplicativocedro.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Esdras Chaves on 26/02/2018.
 */

public class CredentialsValidation {

    private Pattern patternEmail;
    private Pattern patternPassword;
    private Matcher matcherEmail;
    private Matcher matcherPassword;


    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{10,}$";



    public CredentialsValidation () {
        patternEmail = Pattern.compile(EMAIL_PATTERN);
        patternPassword = Pattern.compile(PASSWORD_PATTERN);
    }

    public Boolean validateEmail(String email) {

        matcherEmail = patternEmail.matcher(email);
        return matcherEmail.matches();

    }

    public  Boolean validatePassword(String password) {

        matcherPassword = patternPassword.matcher(password);
        return matcherPassword.matches();
    }

}
