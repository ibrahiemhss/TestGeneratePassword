package com.example.ibrahim.testgeneratepassword.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ibrahim.testgeneratepassword.model.Passwords;

import java.util.ArrayList;
import java.util.List;

import static com.example.ibrahim.testgeneratepassword.data.Contracts.Entry.DB_PASSWORD;
import static com.example.ibrahim.testgeneratepassword.data.Contracts.Entry.ID;
import static com.example.ibrahim.testgeneratepassword.data.Contracts.Entry.NAME_COLUMN;
import static com.example.ibrahim.testgeneratepassword.data.Contracts.Entry.PASSWORD_COLUMN;
import static com.example.ibrahim.testgeneratepassword.data.Contracts.Entry.TABLE_PASSWORD;

/**
 * Created by ibrahim on 08/04/18.
 */

public class DBhelper extends SQLiteOpenHelper

    {

        private static final int SCHEMA = 1;
        private static final String TAG = DBhelper.class.getSimpleName ();

    public DBhelper (Context context) {
        super (context, DB_PASSWORD, null, SCHEMA);
    }

        @Override
        public void onCreate (SQLiteDatabase sqLiteDatabase){
        final String CREATE_TABLE_PASSWORD  =
                "CREATE TABLE " + TABLE_PASSWORD + "(" +
                        ID + " INTEGER  PRIMARY KEY AUTOINCREMENT , " +
                        NAME_COLUMN + " VARCHAR(60) , " +
                        PASSWORD_COLUMN + " VARCHAR(60)  " +
                        ")";

        sqLiteDatabase.execSQL (CREATE_TABLE_PASSWORD);
    }

        @Override
        public void onUpgrade (SQLiteDatabase sqLiteDatabase,int i, int i1){
        throw new UnsupportedOperationException ("This shouldn't happen yet!");

    }
        public List<Passwords> getdata() {
            List<Passwords> data = new ArrayList<> ();
            SQLiteDatabase db = this.getWritableDatabase ();
            Cursor cursor = db.rawQuery ("select * from " + TABLE_PASSWORD + " ;", null);
            StringBuffer stringBuffer = new StringBuffer ();
            Passwords dataModel = null;
            while (cursor.moveToNext ()) {


                long id = cursor.getLong (cursor.getColumnIndexOrThrow (ID));
                String name = cursor.getString (cursor.getColumnIndexOrThrow (NAME_COLUMN));
                String password = cursor.getString (cursor.getColumnIndexOrThrow (PASSWORD_COLUMN));


                dataModel = new Passwords (id, name, password);

                dataModel.setId (id);
                dataModel.setName (name);
                dataModel.setPassword (password);


                stringBuffer.append (dataModel);
                // stringBuffer.append(dataModel);
                data.add (dataModel);
            }

            for (Passwords mo : data) {

            }
            return data;

        }

        public void insertNewPass(String name,String password){

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues= new ContentValues();


            contentValues.put(NAME_COLUMN, name);
            contentValues.put(PASSWORD_COLUMN, password);

            db.insert(TABLE_PASSWORD,null,contentValues);


        }
        public void delete(Long Id) {
            SQLiteDatabase db = this.getWritableDatabase();

            try {

                db.delete(TABLE_PASSWORD, ID+" = "+Id, null);
            }
            catch(Exception e) {
                e.printStackTrace ();
            }
        }

}
