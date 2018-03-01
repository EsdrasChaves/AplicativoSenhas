package com.example.esdraschaves.aplicativocedro.Utils;

import android.content.Context;
import android.nfc.Tag;
import android.os.Build;
import android.security.KeyPairGeneratorSpec;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.annotation.RequiresApi;
import android.util.Base64;
import android.util.Log;
import android.view.animation.Transformation;
import android.widget.Toast;

import com.example.esdraschaves.aplicativocedro.Model.Account;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.AlgorithmParameterSpec;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.security.auth.x500.X500Principal;

/**
 * Created by Esdras Chaves on 28/02/2018.
 */

public class EncryptDecrypt {

    private final String alias = "chavesApp";
    private Context context;
    private KeyStore keyStore = null;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public EncryptDecrypt(Context context) {
        this.context = context;

        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);

            // Create new key if needed
            if (!keyStore.containsAlias(alias)) {
                Calendar start = Calendar.getInstance();
                Calendar end = Calendar.getInstance();
                end.add(Calendar.YEAR, 1);
                KeyPairGeneratorSpec spec = new KeyPairGeneratorSpec.Builder(context)
                        .setAlias(alias)
                        .setSubject(new X500Principal("CN=Sample Name, O=Android Authority"))
                        .setSerialNumber(BigInteger.ONE)
                        .setStartDate(start.getTime())
                        .setEndDate(end.getTime())
                        .build();
                KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "AndroidKeyStore");
                generator.initialize(spec);

                KeyPair keyPair = generator.generateKeyPair();
            }
        } catch (Exception e) {
            Toast.makeText(context, "Exception " + e.getMessage() + " occured", Toast.LENGTH_LONG).show();
            Log.e("InitEncryptLog", Log.getStackTraceString(e));
        }

    }


    public String encryptString(String textToEncrypt){

        String result = null;

        try {
            KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry)keyStore.getEntry(alias, null);
            PublicKey publicKey = privateKeyEntry.getCertificate().getPublicKey();

            Cipher input = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            input.init(Cipher.ENCRYPT_MODE, publicKey);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            CipherOutputStream cipherOutputStream = new CipherOutputStream(
                    outputStream, input);
            cipherOutputStream.write(textToEncrypt.getBytes("UTF-8"));
            cipherOutputStream.close();

            byte [] vals = outputStream.toByteArray();
            result = Base64.encodeToString(vals, Base64.DEFAULT);
        } catch (Exception e) {
            Toast.makeText(context, "Exception " + e.getMessage() + " occured", Toast.LENGTH_LONG).show();
            Log.e("EncryptLOG", Log.getStackTraceString(e));
        }

        return result;

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String decryptString(String textToDecrypt) {

        String result = null;

        try {
            KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry)keyStore.getEntry(alias, null);
            PrivateKey privateKey =  privateKeyEntry.getPrivateKey();

            Cipher output = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            output.init(Cipher.DECRYPT_MODE, privateKey);

            String cipherText = textToDecrypt;
            CipherInputStream cipherInputStream = new CipherInputStream(
                    new ByteArrayInputStream(Base64.decode(cipherText, Base64.DEFAULT)), output);
            ArrayList<Byte> values = new ArrayList<>();
            int nextByte;
            while ((nextByte = cipherInputStream.read()) != -1) {
                values.add((byte)nextByte);
            }

            byte[] bytes = new byte[values.size()];
            for(int i = 0; i < bytes.length; i++) {
                bytes[i] = values.get(i).byteValue();
            }

            result = new String(bytes, 0, bytes.length, "UTF-8");

        } catch (Exception e) {
            Toast.makeText(context, "Exception " + e.getMessage() + " occured", Toast.LENGTH_LONG).show();
            Log.e("DecryptionLOG", Log.getStackTraceString(e));
        }

        return result;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public Account encryptAccount(Account account) {
        Account aux = new Account();

        aux.setUrl(encryptString(account.getUrl()));
        aux.setUser(encryptString(account.getUser()));
        aux.setPassword(encryptString(account.getPassword()));
        aux.setOwner(account.getOwner());
        aux.setId(account.getId());

        return aux;

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public Account decryptAccount(Account account) {
        Account aux = new Account();

        aux.setUrl(decryptString(account.getUrl()));
        aux.setUser(decryptString(account.getUser()));
        aux.setPassword(decryptString(account.getPassword()));
        aux.setOwner(account.getOwner());
        aux.setId(account.getId());

        return aux;

    }


}
