package com.example.ibrahim.testgeneratepassword;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ibrahim.testgeneratepassword.adapters.PasswordAdapter;
import com.example.ibrahim.testgeneratepassword.data.DBhelper;
import com.example.ibrahim.testgeneratepassword.data.PasswordGenerator;
import com.example.ibrahim.testgeneratepassword.model.Passwords;

import java.io.File;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    DBhelper database;
    RecyclerView recyclerView;
    PasswordAdapter passwordAdapter;
    List<Passwords> datamodel;
    EditText     mEtname;
    TextView   mTxtPass;
    String    mName,pass;

    //    String mName,mPassword;
    Locale localelang;

    /*  Permission request code to draw over other apps  */
    private static final int DRAW_OVER_OTHER_APP_PERMISSION_REQUEST_CODE = 1222;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById (R.id.mRv);
        database = new DBhelper (this);
        changeListSend();
        createFloatingWidget();
        findViewById (R.id.btnDelete).setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view) {
                StringBuilder stringBuilder = new StringBuilder();

                for (Passwords datamodels : datamodel) {
                    if (datamodels.isSelected()) {
                        if (stringBuilder.length() > 0)
                            stringBuilder.append(", ");
                        stringBuilder.append(datamodels.getName ());
                        database.deleteRec (new String[] { datamodels.getId () });
                        changeListSend();

                    }
                }
                Toast.makeText(MainActivity.this, stringBuilder.toString(), Toast.LENGTH_LONG).show();
            }
        });

        findViewById (R.id.btnAddPass).setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view) {

                final Dialog dialog = new Dialog (MainActivity.this, R.style.AppTheme_Dark_Dialog);
                dialog.setContentView (R.layout.custom_dialog_box);
                 mTxtPass =dialog. findViewById (R.id.mTxtPass);
                mEtname =dialog. findViewById (R.id.mEtname);

                dialog.findViewById (R.id.mGeneratePass)
                        .setOnClickListener (new View.OnClickListener () {
                            @Override
                            public void onClick (View v) {

                                mTxtPass.setText (getSaltString());

                                changeListSend();

                            }
                        });
                dialog.findViewById (R.id.mAddNew)
                        .setOnClickListener (new View.OnClickListener () {
                            @Override
                            public void onClick (View v) {
                                mName=mEtname.getText ().toString ();
                                pass=mTxtPass.getText ().toString ();

                                if(mName.isEmpty ()) {
                                    Toast.makeText (MainActivity.this,"no value",Toast.LENGTH_SHORT).show();

                                } else if(pass.isEmpty ()){
                                    Toast.makeText (MainActivity.this,"no value",Toast.LENGTH_SHORT).show();

                                }

                                else{
                                    //TODO add new pass to Sqlite

                                    database.insertNewPass (mName,pass);
                                    changeListSend();
                                    Toast.makeText (MainActivity.this,"Success",Toast.LENGTH_SHORT).show();
                                    mName="";
                                    pass="";
                                }
                                mEtname.clearFocus ();

                            }
                        });
                dialog.findViewById (R.id.mClose)
                        .setOnClickListener (new View.OnClickListener () {
                                                 @Override
                                                 public void onClick (View v) {
                                                     dialog.dismiss ();
                                                 }
                                             });
                dialog.show ();



            }
        });

    }

    protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890!@#$%&*()_+-=[]|,./?><";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

    //TODO method work with RecyclerView
    public void changeListSend () {
        database = new DBhelper (MainActivity.this);
        datamodel = database.getdata ();
        RecyclerView.LayoutManager reLayoutManager = new LinearLayoutManager (getApplicationContext ());
        recyclerView.setLayoutManager (reLayoutManager);
        recyclerView.setItemAnimator (new DefaultItemAnimator ());
        passwordAdapter = new PasswordAdapter (datamodel,this);
        recyclerView.setAdapter (passwordAdapter);

        recyclerView.getLayoutManager().scrollToPosition(recyclerView.getAdapter().getItemCount()-1);

    }


    //TODO method work with foating Window
    /*  start floating widget service  */
    public void createFloatingWidget() {
        //Check if the application has draw over other apps permission or not?
        //This permission is by default available for API<23. But for API > 23
        //you have to ask for the permission in runtime.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ! Settings.canDrawOverlays(this)) {
            //If the draw over permission is not available open the settings screen
            //to grant the permission.
            Intent intent = new Intent (Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, DRAW_OVER_OTHER_APP_PERMISSION_REQUEST_CODE);
        } else
            //If permission is granted start floating widget service
            startFloatingWidgetService();

    }

    /*  Start Floating widget service and finish current activity */
    private void startFloatingWidgetService() {
        startService(new Intent (MainActivity.this, FloatingWidgetService.class));
     //   finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DRAW_OVER_OTHER_APP_PERMISSION_REQUEST_CODE) {
            //Check if the permission is granted or not.
            if (resultCode == RESULT_OK)
                //If permission granted start floating widget service
                startFloatingWidgetService();
            else
                //Permission is not available then display toast
                Toast.makeText(this,
                        getResources().getString(R.string.draw_other_app_permission_denied),
                        Toast.LENGTH_SHORT).show();

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
