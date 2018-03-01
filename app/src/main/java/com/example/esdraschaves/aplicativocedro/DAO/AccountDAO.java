package com.example.esdraschaves.aplicativocedro.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.esdraschaves.aplicativocedro.LoginActivity;
import com.example.esdraschaves.aplicativocedro.Model.Account;
import com.example.esdraschaves.aplicativocedro.Model.CredentialResponse;
import com.example.esdraschaves.aplicativocedro.Model.CurrentSession;
import com.example.esdraschaves.aplicativocedro.Model.UserInfo;
import com.example.esdraschaves.aplicativocedro.Rest.APIService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Esdras Chaves on 27/02/2018.
 */

public class AccountDAO extends SQLiteOpenHelper {

    private static final int VERSION =  1;
    private static final String TABLE = "Account";
    private static final String DATABASE = "AppCedro";

    private Bitmap foto;


    private static final String TAG = "REGISTER_ACCOUNT";

    public AccountDAO(Context context) {
        super (context, DATABASE, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String ddl = "CREATE TABLE " + TABLE + "("
                + "id INTEGER PRIMARY KEY, "
                + "owner TEXT, url TEXT, user TEXT, password TEXT);";

        db.execSQL(ddl);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE;
        db.execSQL(sql);
        onCreate(db);
    }

    public void register(Account account) {
        ContentValues values = new ContentValues();

        values.put("owner", account.getOwner());
        values.put("url", account.getUrl());
        values.put("user", account.getUser());
        values.put("password", account.getPassword());

        getWritableDatabase().insert(TABLE, null, values);

        Log.i(TAG, "Registered account " + account.getUser());

    }

    public void delete(Account account) {
        String[] args = {account.getId().toString()};

        getWritableDatabase().delete(TABLE, "id=?", args);

        Log.i("Delete", "Conta deletada: " +account.getId());

    }

    public void update(Account account) {
        ContentValues values = new ContentValues();

        values.put("owner", account.getOwner());
        values.put("url", account.getUrl());
        values.put("user", account.getUser());
        values.put("password", account.getPassword());

        String[] args = {account.getId().toString()};

        getWritableDatabase().update(TABLE, values, "id=?", args);
        Log.i("Update", "Account updatad: " + account.getUrl());

    }

    public ArrayList<Account> list(String email) {

        ArrayList<Account> list = new ArrayList<Account>();

        String sql = "SELECT * FROM Account WHERE owner='" + email + "' order by user ";

        Cursor cursor = getReadableDatabase().rawQuery(sql, null);

        try {
            while(cursor.moveToNext()) {

                // Decrypt Stuff

                Account account = new Account();

                account.setId(cursor.getLong(0));
                account.setOwner(cursor.getString(1));

                // Set picture according to API

                account.setUrl(cursor.getString(2));
                account.setUser(cursor.getString(3));
                account.setPassword(cursor.getString(4));


                account.setSiteLogo(foto);

                list.add(account);


            }
        }catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }finally {
            cursor.close();
        }

        return list;

    }

}
