package com.example.loginnote.ui.changepass;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.loginnote.DatabaseHelper;

public class ChangePassword_DB extends DatabaseHelper {
    public ChangePassword_DB(Context context){
        super(context);
    }
    public boolean updatePass(ChangePassword_OJ changePasswordOj)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Password",changePasswordOj.getPassword());
        long update = db.update("user", contentValues, " ID = " + changePasswordOj.getId(), null);
        if(update == -1 )
            return false;
        else
            return true;
    }
}
