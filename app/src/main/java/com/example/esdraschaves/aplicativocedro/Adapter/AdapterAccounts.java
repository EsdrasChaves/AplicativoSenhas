package com.example.esdraschaves.aplicativocedro.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.esdraschaves.aplicativocedro.Model.Account;
import com.example.esdraschaves.aplicativocedro.Model.CurrentSession;
import com.example.esdraschaves.aplicativocedro.R;
import com.example.esdraschaves.aplicativocedro.ShowDataActivity;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Esdras Chaves on 27/02/2018.
 */

public class AdapterAccounts extends RecyclerView.Adapter<AdapterAccounts.ViewHolderAccounts>{

    ArrayList<Account> accountList;
    Context ctx;

    public AdapterAccounts(ArrayList<Account> accountList, Context ctx) {
        this.accountList = accountList;
        this.ctx = ctx;
    }

    @Override
    public AdapterAccounts.ViewHolderAccounts onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_accounts, null, false);

        return new ViewHolderAccounts(view, ctx, accountList);
    }

    @Override
    public void onBindViewHolder(AdapterAccounts.ViewHolderAccounts holder, int position) {


        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request newRequest = chain.request().newBuilder()
                                .addHeader("authorization", CurrentSession.getInstance().getToken())
                                .build();
                        return chain.proceed(newRequest);
                    }
                })
                .build();

        Picasso picasso = new Picasso.Builder(holder.webLogo.getContext())
                .downloader(new OkHttp3Downloader(client))
                .build();

        String PATH = "https://dev.people.com.ai/mobile/api/v2/logo/" + accountList.get(position).getUrl();

        picasso.load(PATH).into(holder.webLogo);

        holder.urlText.setText(accountList.get(position).getUrl());
        holder.usernameText.setText(accountList.get(position).getUser());
    }

    @Override
    public int getItemCount() {
        return accountList.size();
    }

    public class ViewHolderAccounts extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView webLogo;
        TextView urlText;
        TextView usernameText;
        ArrayList<Account> accounts = new ArrayList<Account>();
        Context ctx;


        public ViewHolderAccounts(View itemView, Context ctx, ArrayList<Account> accounts) {
            super(itemView);
            itemView.setOnClickListener(this);

            this.accounts = accounts;
            this.ctx = ctx;

            webLogo = (ImageView) itemView.findViewById(R.id.image_website_logo);
            urlText = (TextView) itemView.findViewById(R.id.text_url);
            usernameText = (TextView) itemView.findViewById(R.id.text_userName);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();

            Account account = this.accounts.get(position);

            Intent intent = new Intent(this.ctx, ShowDataActivity.class);
            intent.putExtra("Selected_Account", account);

            this.ctx.startActivity(intent);

        }
    }
}
