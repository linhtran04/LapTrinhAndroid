package com.example.loginnote.ui.note;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.loginnote.DBHelper;
import com.example.loginnote.ui.cetegory.Category_OJ;
import com.example.loginnote.ui.priority.PriorityOJ;
import com.example.loginnote.ui.status.StatusViewModel;
import com.example.loginnote.ui.status.Status_OJ;

import java.util.ArrayList;
import java.util.List;

import static com.example.loginnote.LoginActivity.AccInfo;
public class note_DB extends DBHelper {
    public note_DB(Context context){super(context);}

    public boolean insertCategory(noteOJ noteOJ){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Content",noteOJ.getName());
        contentValues.put("Category",noteOJ.getCategory());
        contentValues.put("Priority",noteOJ.getPriority());
        contentValues.put("Status",noteOJ.getStatus());
        contentValues.put("PlanDate",noteOJ.getPlanDate());
        contentValues.put("DateCreate",noteOJ.getCreateDate());
        contentValues.put("IDAcc",AccInfo.getId());
        long inserted = db.insert(TABLE_NOTE,null,contentValues);
        if(inserted == -1 )
            return false;
        else
            return true;
    }
    public List<noteOJ> getNote(){
        List<noteOJ> list = new ArrayList<noteOJ>();
        String queryString = "SELECT * FROM  Note Where IDAcc = "+ AccInfo.getId()+ " ORDER BY id DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);
        if(cursor.moveToFirst()){
            do {
                noteOJ  noteOJ = new noteOJ(cursor.getString(4),cursor.getString(2),cursor.getString(1),cursor.getString(3),cursor.getString(6),cursor.getString(5),cursor.getInt(0));
                list.add(noteOJ);
            }while (cursor.moveToNext());
        }else{}
        cursor.close();
        db.close();
        return list;
    }

    public List<Category_OJ> getSpinnerCategory (){
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
    public List<PriorityOJ> getSpinnerPriority (){
        List<PriorityOJ> listPri = new ArrayList<PriorityOJ>();
        String queryString = "SELECT * FROM " + TABLE_PRIORITY + " WHERE IDAcc = " + AccInfo.getId();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);
        if(cursor.moveToFirst()){
            do {
                int priorityId = cursor.getInt(0);
                String priorityName = cursor.getString(1);
                String priorityDatetime = cursor.getString(2);
                PriorityOJ newPriority = new PriorityOJ(priorityId,priorityName,priorityDatetime);
                listPri.add(newPriority);
            }while (cursor.moveToNext());
        }else{}
        cursor.close();
        db.close();
        return listPri;
    }
    public List<Status_OJ> getSpinnerStatus(){
        List<Status_OJ> lstStatus = new ArrayList<Status_OJ>();
        String queryString = "SELECT * FROM " + TABLE_STATUS +" Where IDAcc = "+ AccInfo.getId();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);
        if (cursor.moveToFirst()){
            do{
                int Id = cursor.getInt(0);
                String StatusName = cursor.getString(1);
                String DateSta = cursor.getString(2);
                Status_OJ n = new Status_OJ(Id, StatusName,DateSta);
                lstStatus.add(n);
            }while (cursor.moveToNext());
        }else {}
        cursor.close();
        db.close();
        return lstStatus;
    }
    public boolean  deleteNote(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        long inserted = db.delete("Note","id = "+id,null);
        if(inserted == -1 )
            return false;
        else
            return true;
    }

    public boolean  updateNote(noteOJ noteOJ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Status",noteOJ.getStatus());
        contentValues.put("Category",noteOJ.getCategory());
        contentValues.put("IDAcc",AccInfo.getId());
        contentValues.put("ID",noteOJ.getId());
        contentValues.put("Priority",noteOJ.getPriority());
        contentValues.put("PlanDate",noteOJ.getPlanDate());
        contentValues.put("DateCreate",noteOJ.getCreateDate());
        contentValues.put("Content",noteOJ.getName());
        long inserted = db.update("Note",contentValues,"ID = "+ noteOJ.getId(),null);
        if(inserted == -1 )
            return false;
        else
            return true;
    }

}
