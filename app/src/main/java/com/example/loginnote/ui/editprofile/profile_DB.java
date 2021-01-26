package com.example.loginnote.ui.editprofile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.loginnote.DBHelper;
import com.example.loginnote.DatabaseHelper;
import com.example.loginnote.ui.cetegory.Category_OJ;

public class profile_DB extends DatabaseHelper {
    public profile_DB(Context context) {
        super(context);
    }

    public boolean  updateProfile(ProfileOJ profileOJ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name",profileOJ.getName());
        contentValues.put("Email",profileOJ.getEmail());
        contentValues.put("Password",profileOJ.getPassword());
        long update = db.update("user", contentValues, " ID = " + profileOJ.getId(), null);

        if(update == -1 )
            return false;
        else
            return true;
    }


}
