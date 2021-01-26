package com.example.loginnote.ui.cetegory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.loginnote.DBHelper;
import com.example.loginnote.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

import static com.example.loginnote.LoginActivity.AccInfo;

public class category_DB extends DBHelper {
    public category_DB(Context context) {
        super(context);
    }

    public boolean  insetCategory(Category_OJ categoryOJ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("NameCategory",categoryOJ.getName());
        contentValues.put("DateCate",categoryOJ.getCreateDate());
        contentValues.put("IDAcc",AccInfo.getId());
        long inserted = db.insert("Category",null,contentValues);
        if(inserted == -1 )
            return false;
        else
            return true;
    }

    public List<Category_OJ> getListCategory (){
        List<Category_OJ> list = new ArrayList<Category_OJ>();
        String queryString = "SELECT * FROM  Category Where IDAcc = "+ AccInfo.getId();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);
        if(cursor.moveToFirst()){
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String Datetime = cursor.getString(2);
                Category_OJ  categoryOJ = new Category_OJ(id,name,Datetime);
                list.add(categoryOJ);
            }while (cursor.moveToNext());
        }else{}
        cursor.close();
        db.close();
        return list;
    }

    public boolean  updateCategory(Category_OJ category_OJ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("NameCategory",category_OJ.getName());
        contentValues.put("DateCate", category_OJ.getCreateDate());
        contentValues.put("ID",category_OJ.getId());
        //contentValues.put("IDAcc",AccInfo.getId());
        long inserted = db.update("Category",contentValues,"ID = "+ category_OJ.getId(),null);
        if(inserted == -1 )
            return false;
        else
            return true;
    }

    public boolean  deleteCategory(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        long inserted = db.delete("Category","id = "+id,null);
        if(inserted == -1 )
            return false;
        else
            return true;
    }
}
