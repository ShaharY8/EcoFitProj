package com.example.ecofit.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    Context context;
    private static final String DATABASE_NAME = "ComputerList.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "Users";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "_name";
    private static final String COLUMN_LASTNAME = "_lastName";
    private static final String COLUMN_PASS = "_pass";
    private static final String COLUMN_PHONE = "_phone";
    private static final String COLUMN_PRICE = "_price";
//    private static final String COLUMN_BTN1 = "_btn1";
//    private static final String COLUMN_BTN2 = "_btn2";
//    private static final String COLUMN_BTN3 = "_btn3";

    public MyDatabaseHelper(@Nullable Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String query = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_LASTNAME + " TEXT, " +
                COLUMN_PASS + " TEXT, " +
                COLUMN_PHONE+ " TEXT, " +
                COLUMN_PRICE + " INTEGER);";

        db.execSQL(query);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addUser(String name,String Lname,String pass, String phone, int price)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_LASTNAME,Lname);
        cv.put(COLUMN_PASS,pass);
        cv.put(COLUMN_PHONE,phone);
        cv.put(COLUMN_PRICE, price);



        long result = db.insert(TABLE_NAME,null, cv);

        if(result == -1)
        {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor readAllData()
    {
        //String query = "SELECT " + COLUMN_ID + ", " + COLUMN_NAME + ", " + COLUMN_PRICE + " FROM " + TABLE_NAME;
        String query2 = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null)
        {
            cursor = db.rawQuery(query2, null);
        }
        return cursor;
    }

    public boolean CheckIfExist(String id, String pass)
    {
        String query = "SELECT " + COLUMN_ID + " FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = '" + id + "' AND " + COLUMN_PASS + " = '" + pass +"'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        return cursor.getCount() == 1;
    }

    public String getIDByPhone(String phone)
    {
        String query = "SELECT " + COLUMN_ID + " FROM " + TABLE_NAME + " WHERE " + COLUMN_PHONE + " = '" + phone + "'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        return cursor.getString(0);
    }
    public String getNameByPhone(String phone)
    {
        String query = "SELECT " + COLUMN_NAME + " FROM " + TABLE_NAME + " WHERE " + COLUMN_PHONE + " = '" + phone + "'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        if(cursor.getCount() == 0){
            return "";
        }
        return cursor.getString(0);
    }
    public String getPassByPhone(String phone)
    {
        String query = "SELECT " + COLUMN_PASS + " FROM " + TABLE_NAME + " WHERE " + COLUMN_PHONE + " = '" + phone + "'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        if(cursor.getCount() == 0){
            return "";
        }
        return cursor.getString(0);
    }
    public String getlLnameByPhone(String phone)
    {
        String query = "SELECT " + COLUMN_LASTNAME + " FROM " + TABLE_NAME + " WHERE " + COLUMN_PHONE + " = '" + phone + "'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        if(cursor.getCount() == 0){
            return "";
        }
        return cursor.getString(0);
    }

    public void updateUser(String row_id,String name, String Lname, String pass, String phone, int price)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();


        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_LASTNAME,Lname);
        cv.put(COLUMN_PASS,pass);
        cv.put(COLUMN_PHONE,phone);
        cv.put(COLUMN_PRICE, price);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});

        if(result == -1)
        {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }

    }

    public void deleteOneRow(String row_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME,COLUMN_ID+" =?",new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Delete Successfully!", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }
    public void deleteAllData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }
}
