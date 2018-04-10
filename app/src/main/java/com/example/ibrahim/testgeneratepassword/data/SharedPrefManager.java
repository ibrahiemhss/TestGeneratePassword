package com.example.ibrahim.testgeneratepassword.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ibrahim on 30/12/17.
 */

public class SharedPrefManager {

    private static final String KEY_IS_USER_LOGGEDIN = "isUserLoggedIn";
    private static final String FIRT_TRY_KEY = "first_try";
    private static final String SECOND_TRY_KEY = "second_try";
    private static final String THIRD_TRY_KEY = "third_try";

    private static final String ON_KEY="on" ;


    private static final String SHARED_PREF_NAME = "save_contents";
    private static SharedPrefManager mInstance;
    private static Context mCtx;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    public SharedPrefManager (Context context) {
        mCtx = context;
        pref=mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);

        }
        return mInstance;
    }



    public  boolean saveFirstLock( String name) {
        editor = pref.edit();
        editor.putString( FIRT_TRY_KEY, name );
        editor.apply();
        editor.apply();
        return true;
    }
    public String getFirstLeck() {

        return pref.getString( FIRT_TRY_KEY, null );

    }
    public  boolean saveSeconLock( String name) {
        editor = pref.edit();
        editor.putString( SECOND_TRY_KEY, name );
        editor.apply();
        editor.apply();
        return true;
    }
    public String getSeconLock() {

        return pref.getString( SECOND_TRY_KEY, null );

    }
    public  boolean saveThirdLock( String name) {
        editor = pref.edit();
        editor.putString( THIRD_TRY_KEY, name );
        editor.apply();
        editor.apply();
        return true;
    }
    public String getThirdLock() {

        return pref.getString( THIRD_TRY_KEY, null );

    }

    public void setLoginUser(boolean isLoggedIn) {
        editor = pref.edit();
        editor.putBoolean( KEY_IS_USER_LOGGEDIN, isLoggedIn );
        editor.apply();
        editor.commit();

    }


    public boolean isUserLoggedIn() {
        return pref.getBoolean( KEY_IS_USER_LOGGEDIN, false );

    }
}