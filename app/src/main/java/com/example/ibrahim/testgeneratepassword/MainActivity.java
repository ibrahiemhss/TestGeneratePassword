package com.example.ibrahim.testgeneratepassword;

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
import com.example.ibrahim.testgeneratepassword.model.Passwords;

import java.io.File;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    DBhelper database;
    RecyclerView recyclerView;
    PasswordAdapter passwordAdapter;
    List<Passwords> datamodel;
    private EditText mEtname,mEtpass;
//    String mName,mPassword;
    Locale localelang;

    /*  Permission request code to draw over other apps  */
    private static final int DRAW_OVER_OTHER_APP_PERMISSION_REQUEST_CODE = 1222;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEtname = findViewById (R.id.mEtname);
        mEtpass = findViewById (R.id.mEtpass);
        recyclerView = findViewById (R.id.mRv);
        database = new DBhelper (this);
        changeListSend();
        createFloatingWidget();
        findViewById (R.id.btnAddPass).setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view) {
                //TODO add new pass to Sqlite
                String    mName=mEtname.getText ().toString ();
                String     mPassword=mEtpass.getText ().toString ();
                if(mName.isEmpty ()&&mPassword.isEmpty ()) {
                    Toast.makeText (MainActivity.this,"no value",Toast.LENGTH_SHORT).show();

                } else{
                    database.insertNewPass (mName,mPassword);
                    changeListSend();
                    mEtname.setText ("");
                    mEtpass.setText ("");

                }
                mEtname.clearFocus ();
                mEtpass.clearFocus ();

            }
        });

    }



    //TODO method work with RecyclerView
    private void changeListSend () {
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
