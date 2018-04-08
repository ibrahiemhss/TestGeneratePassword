package com.example.ibrahim.testgeneratepassword.data;

import android.content.Context;
import android.net.Uri;

import java.io.InputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by ibrahim on 08/04/18.
 */

public class PasswordGenerator {


    private final Context mContext;
    private final ArrayList<String> mList;
    private int mId;
    private MessageDigest mMessageDigest;
    private byte[] mSum;

    public PasswordGenerator(Context context) {

        mContext = context;
        mId = 20;
        mList = new ArrayList<>();
    }

    public ArrayList<String> generate(Uri uri, String account, int number, int length) throws Exception {

        initialize(uri);

        for (int i = 0; i < number; i++) {
            String pass = generate(account, length);
            mList.add(pass);
        }

        return mList;
    }

    private void initialize(Uri uri) throws Exception {

        mMessageDigest = MessageDigest.getInstance("SHA1");
        InputStream inputStream = mContext.getContentResolver().openInputStream(uri);
        byte[] data = new byte[1024];
        int read;

        while ((read = inputStream.read(data)) != -1) mMessageDigest.update(data, 0, read);
    }

    private String generate(String account, int length) throws Exception {

        String digits = "0123456789";
        String uppers = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowers = "abcdefghijklmnopqrstuvwxyz";
         String PUNCTUATION = "!@#$%&*()_+-=[]|,./?><";
        String chars = digits + uppers + lowers+PUNCTUATION;
        String pass = "";

        for (int i = 0; i < length; i++) {
            if (mId > 19) {
                mId = 0;
                mMessageDigest.update(account.getBytes());
                MessageDigest mdc = (MessageDigest) mMessageDigest.clone(); // mdc = md cloned
                mSum = mdc.digest();
            }

            int index = ((mSum[mId++] & 0xff) * chars.length()) / 256; // & 0xff for get an unsigned
            pass += chars.charAt(index);
        }

        if (check(pass)) return pass;
        else return generate(account, length);
    }

    private boolean check(String pass) {

        boolean uppers = false;
        boolean digits = false;

        for (int i = 0; i < pass.length(); i++) {
            if (Character.isUpperCase(pass.charAt(i))) uppers = true;
            if (Character.isDigit(pass.charAt(i))) digits = true;
        }

        return !((!uppers) || (!digits));
    }

}