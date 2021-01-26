package com.example.loginnote.ui.status;

import com.example.loginnote.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.loginnote.DBHelper;
import com.example.loginnote.ui.priority.PriorityOJ;

import java.util.ArrayList;
import java.util.List;

import static com.example.loginnote.LoginActivity.AccInfo;
public class Status_DB extends DBHelper {
    public Status_DB(Context context) {
        super(context);
    }


    public boolean  insetStatus(Status_OJ status_oj){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("NameStatus",status_oj.getName());
        contentValues.put("DateSta",status_oj.getCreatedate());
        contentValues.put("IDAcc",AccInfo.getId());
        long inserted = db.insert("Status",null,contentValues);
        if(inserted == -1 )
            return false;
        else
            return true;
    }
    public boolean  deleteStatus(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        long inserted = db.delete("Status","id = "+id,null);
        if(inserted == -1 )
            return false;
        else
            return true;
    }
    public boolean  updateStatus(Status_OJ status_oj){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("NameStatus",status_oj.getName());
        contentValues.put("DateSta",status_oj.getCreatedate());
        contentValues.put("ID",status_oj.getId());
        contentValues.put("IDAcc",AccInfo.getId());
        long inserted = db.update("Status",contentValues,"ID = "+ status_oj.getId(),null);
        if(inserted == -1 )
            return false;
        else
            return true;
    }
    public List<Status_OJ> getListStatus (){
        List<Status_OJ> list = new ArrayList<Status_OJ>();
        String queryString = "SELECT * FROM  Status Where IDAcc = " + AccInfo.getId()+" ORDER BY id DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);
        if(cursor.moveToFirst()){
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String Datetime = cursor.getString(2);
                Status_OJ  status_oj = new Status_OJ(id,name,Datetime);
                list.add(status_oj);
            }while (cursor.moveToNext());
        }else{}
        cursor.close();
        db.close();
        return list;
    }

}
