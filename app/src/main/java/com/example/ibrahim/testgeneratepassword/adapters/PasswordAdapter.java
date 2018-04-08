package com.example.ibrahim.testgeneratepassword.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ibrahim.testgeneratepassword.R;
import com.example.ibrahim.testgeneratepassword.model.Passwords;

import java.util.List;
import java.util.Locale;

/**
 * Created by ibrahim on 08/04/18.
 */

public class PasswordAdapter extends RecyclerView.Adapter<PasswordAdapter.MyHolder> {

    List<Passwords> passwords;
    Context context;
    AlertDialog.Builder builder;

    public PasswordAdapter(List<Passwords> passwords, Context context) {

        this.context=context;
        this.passwords=passwords;
    }


    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.list_item_password, parent, false);
        view.setLayoutParams(new RecyclerView.LayoutParams( RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));

        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {
        final Passwords SH = passwords.get (position);

        holder.id.setText (String.valueOf (SH.getId ()));
        holder.name.setText (SH.getName ());
        holder.password.setText (SH.getPassword ());
    }
    @Override
    public int getItemCount()
    {
        if(passwords!=null){
            return passwords.size();

        }
        return 0 ;

    }



    public class MyHolder extends RecyclerView.ViewHolder{

        TextView id;
        TextView name;
        TextView password;


        MyHolder(View itemView) {
            super(itemView);

            id = (TextView) itemView.findViewById( R.id.txtId);
            name = (TextView) itemView.findViewById( R.id.txtName);
            password = (TextView) itemView.findViewById( R.id.txtPassword);

        }

    }}