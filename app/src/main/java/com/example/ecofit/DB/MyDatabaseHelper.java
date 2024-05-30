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
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_NAME = "Users";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "_name";
    private static final String COLUMN_LASTNAME = "_lastName";
    private static final String COLUMN_PASS = "_pass";
    private static final String COLUMN_PHONE = "_phone";
    private static final String COLUMN_PRICE = "_price";
    private static final String COLUMN_IsGym = "_IsGym";
    private static final String COLUMN_IsHome = "_IsHome";
    private static final String COLUMN_IsHomeAndGym = "_IsHomeAndGym";

    public MyDatabaseHelper(@Nullable Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    // פעולה שיוצרת את הטבלה של המשתמשים
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_LASTNAME + " TEXT, " +
                COLUMN_PASS + " TEXT, " +
                COLUMN_PHONE + " TEXT, " +
                COLUMN_PRICE + " INTEGER, " +
                COLUMN_IsGym + " INTEGER DEFAULT 0, " + // Default value is false
                COLUMN_IsHome + " INTEGER DEFAULT 0, " + // Default value is false
                COLUMN_IsHomeAndGym + " INTEGER DEFAULT 0)"; // Default value is false

        db.execSQL(query);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // מוסיפה משתמש לטבלה
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


    // בודק אם משתמש קיים
    public boolean CheckIfExist(String phone, String pass){

        String query = "SELECT " + COLUMN_ID + " FROM " + TABLE_NAME + " WHERE " + COLUMN_PHONE + " = '" + phone + "' AND " + COLUMN_PASS + " = '" + pass +"'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        return cursor.getCount() == 1;
    }
    // פעולה שמחזירה את ה- ID של המשתמש
    public String getIDByPhone(String phone)
    {
        String query = "SELECT " + COLUMN_ID + " FROM " + TABLE_NAME + " WHERE " + COLUMN_PHONE + " = '" + phone + "'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        return cursor.getString(0);
    }
    // פעולה שמחזירה את השם של המשתמש
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
    // פעולה המחזירה את הסיסמא של המשתמש
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
    // פעולה המחזירה את שם המשפחה של המשתמש
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
    //פעולה שמעדכנת את הפרטים של המשתמש
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
    // פעולה אשר מעדכנת קנייה של תוכנית אימון
    public void updatePlan(String row_id, boolean IsGym, boolean IsHome, boolean IsHomeAndGym)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();


        cv.put(COLUMN_IsGym, IsGym);
        cv.put(COLUMN_IsHome,IsHome);
        cv.put(COLUMN_IsHomeAndGym,IsHomeAndGym);


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
    //פעולה הבודקת אם המשתמש קנה בעבר תוכנית אימון
    public boolean CheckIfPlanBought(String phone, String whichPlan)
    {
        String query = "";
        if(whichPlan.equals("IsGym")){
            query = "SELECT " + COLUMN_ID + " FROM " + TABLE_NAME + " WHERE " + COLUMN_PHONE + " = '" + phone + "' AND " + COLUMN_IsGym + " = 1";

        }
        else if(whichPlan.equals("IsHome")){
            query = "SELECT " + COLUMN_ID + " FROM " + TABLE_NAME + " WHERE " + COLUMN_PHONE + " = '" + phone + "' AND " + COLUMN_IsHome + " = 1";
        }
        else{
            // HomeAndGym
            query = "SELECT " + COLUMN_ID + " FROM " + TABLE_NAME + " WHERE " + COLUMN_PHONE + " = '" + phone + "' AND " + COLUMN_IsHomeAndGym + " = 1";        }
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        return cursor.getCount() == 1;
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
