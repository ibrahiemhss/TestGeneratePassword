package com.example.ibrahim.testgeneratepassword.adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ibrahim.testgeneratepassword.MainActivity;
import com.example.ibrahim.testgeneratepassword.R;
import com.example.ibrahim.testgeneratepassword.data.DBhelper;
import com.example.ibrahim.testgeneratepassword.model.Passwords;

import java.util.List;

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

    public PasswordAdapter (Context context) {
        this.context=context;

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

        //    holder.id.setText (String.valueOf (SH.getId ()));
        holder.name.setText (SH.getName ());
        holder.password.setText (SH.getPassword ());
        holder.checkbox.setOnCheckedChangeListener(null);

        holder.copyImage.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view) {
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("text", SH.getPassword ());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(context, "Copied to Clipboard!", Toast.LENGTH_SHORT).show();
            }
        });
        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                passwords.get(holder.getAdapterPosition()).setSelected(isChecked);
            }
        });
      /*  holder.imgRemove.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view) {



                final Dialog dialog = new Dialog (context, R.style.AppTheme_Dark_Dialog);
                dialog.setContentView (R.layout.simple_dialog);

                dialog.findViewById (R.id.cancelD)
                        .setOnClickListener (new View.OnClickListener () {
                            @Override
                            public void onClick (View v) {

                                dialog.dismiss ();

                            }
                        });
                dialog.findViewById (R.id.yesD)
                        .setOnClickListener (new View.OnClickListener () {
                            @Override
                            public void onClick (View v) {
                                removeAt(position);

                                DBhelper dBhelper=new DBhelper (context);
                                dBhelper.delete (Long.parseLong (SH.getId ()));
                                passwords = dBhelper.getdata ();
                                dialog.dismiss ();

                            }
                        });

                dialog.show ();

            }
        });*/
    }
    public void removeAt(int position) {
        passwords.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, passwords.size());
    }
    @Override
    public int getItemCount()
    {
        if(passwords!=null){
            return passwords.size();

        }
        return 0 ;

    }



    public class MyHolder extends RecyclerView.ViewHolder {

        TextView id;
        TextView name;
        TextView password;
        ImageView copyImage;//;,imgRemove;
        RecyclerView   mListView;
        private CheckBox checkbox;

        MyHolder(View itemView) {
            super(itemView);

            //  id = (TextView) itemView.findViewById( R.id.txtId);
            name = (TextView) itemView.findViewById( R.id.txtName);
            password = (TextView) itemView.findViewById( R.id.txtPassword);
            copyImage = (ImageView) itemView.findViewById( R.id.copyImage);
           // imgRemove = (ImageView) itemView.findViewById( R.id.imgRemove);
            checkbox = (CheckBox) itemView.findViewById( R.id.checkbox);

            mListView =itemView.findViewById(R.id.mRv);


        }




    }}