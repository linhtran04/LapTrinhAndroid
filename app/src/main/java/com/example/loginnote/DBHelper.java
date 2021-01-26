package com.example.loginnote;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.loginnote.ui.priority.PriorityOJ;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "notedb.sqlite";
    public   static final int SCHEMA_VERSION=1;
    public static final String TABLE_ACCOUNT = "Account";
    public static final String TABLE_CATEGORY = "Category";
    public static final String TABLE_NOTE = "Note";
    public static final String TABLE_PRIORITY = "Priority";
    public static final String TABLE_STATUS = "Status";
    public static final String COLUMN_PRIORITY_NAME_PRIORITY = "NamePriority";
    public static final String COLUMN_PRIORITY_DATE_PRI = "DatePri";

    public  DBHelper(Context context){
        super(context, DATABASE_NAME, null, SCHEMA_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + TABLE_ACCOUNT + " ( ID    INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, FirstName    TEXT, LastName    TEXT, Email    TEXT UNIQUE, Password    TEXT )"
        );
        db.execSQL(
                "CREATE TABLE " + TABLE_CATEGORY + " ( ID    INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, NameCategory    TEXT, DateCate    TEXT, IDAcc  INTEGER)"
        );
        db.execSQL(
                "CREATE TABLE " + TABLE_NOTE + " ( ID    INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, Priority    TEXT, Category    TEXT, Status    TEXT, Content    TEXT, DateCreate    TEXT,PlanDate    TEXT, IDAcc     INTEGER, FOREIGN KEY(Priority) REFERENCES Priority (ID), FOREIGN KEY(Status) REFERENCES Status(ID), FOREIGN KEY(Category) REFERENCES Category(ID) )"
        );
        db.execSQL(
                "CREATE TABLE " + TABLE_PRIORITY + " ( ID    INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " + COLUMN_PRIORITY_NAME_PRIORITY + "    TEXT, " + COLUMN_PRIORITY_DATE_PRI + "    TEXT, IDAcc INTEGER)"
        );
        db.execSQL(
                "CREATE TABLE " + TABLE_STATUS + " ( ID    INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, NameStatus    TEXT , DateSta    TEXT, IDAcc     INTEGER)"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRIORITY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATUS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        onCreate(db);
    }




}
