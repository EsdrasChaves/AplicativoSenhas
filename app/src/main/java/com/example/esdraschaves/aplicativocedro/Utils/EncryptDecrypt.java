package com.example.esdraschaves.aplicativocedro.Utils;

import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.animation.Transformation;

import com.example.esdraschaves.aplicativocedro.Model.Account;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * Created by Esdras Chaves on 28/02/2018.
 */

public class EncryptDecrypt {

    //private final String alias = "chavesApp";
    byte[] iv = null;
    List<byte[]> lista = new ArrayList<byte[]>();




    @RequiresApi(api = Build.VERSION_CODES.M)
    public byte[] encryptString(String textToEncrypt, String alias) throws UnsupportedEncodingException {
        byte[] result = null;

        final KeyGenerator keyGenerator;
        try {
            keyGenerator = KeyGenerator
                    .getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
            final KeyGenParameterSpec keyGenParameterSpec = new KeyGenParameterSpec.Builder(alias,
                    KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                    .build();

            keyGenerator.init(keyGenParameterSpec);
            final SecretKey secretKey = keyGenerator.generateKey();

            final Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            iv = cipher.getIV();
            lista.add(iv);

            result = cipher.doFinal(textToEncrypt.getBytes("UTF-8"));


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        return result;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String decryptString(byte[] textToDecrypt, String alias, int pos) {

        String result = null;


        try {
            final KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            final KeyStore.SecretKeyEntry secretKeyEntry = (KeyStore.SecretKeyEntry) keyStore
                    .getEntry(alias, null);

            final SecretKey secretKey = secretKeyEntry.getSecretKey();

            final Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(lista.get(pos)));


            final byte[] decodedData = cipher.doFinal(textToDecrypt);

            result = new String(decodedData, "UTF-8");


        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnrecoverableEntryException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }

        return result;

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public Account encryptAccount(Account account) {
        Account aux = new Account();

        try {
            aux.setOwner(new String(encryptString(account.getOwner(), "teste1"), "ISO-8859-1"));
            aux.setUrl(new String(encryptString(account.getUrl(), "teste1"), "ISO-8859-1"));
            //aux.setUser(new String(encryptString(account.getUser()), "ISO-8859-1"));
            //aux.setPassword(new String(encryptString(account.getPassword()), "ISO-8859-1"));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Log.e("TESTE", aux.getOwner());
        Log.e("TESTE", aux.getUrl());
        //Log.e("TESTE", aux.getUser());
        //Log.e("TESTE", aux.getPassword());


        return aux;

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public Account decryptAccount(Account account) {
        Account aux = new Account();

        try {
            aux.setOwner(decryptString(account.getOwner().getBytes("ISO-8859-1"), "teste1", 0));
            aux.setUrl(decryptString(account.getUrl().getBytes("ISO-8859-1"), "teste1", 1));
            //aux.setUser(decryptString(account.getUser().getBytes("ISO-8859-1")));
            //aux.setPassword(decryptString(account.getPassword().getBytes("ISO-8859-1")));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Log.e("TESTE", aux.getOwner());
        Log.e("TESTE", aux.getUrl());
        //Log.e("TESTE", aux.getUser());
       // Log.e("TESTE", aux.getPassword());

        return aux;

    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }


}
