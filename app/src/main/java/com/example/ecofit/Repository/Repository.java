package com.example.ecofit.Repository;

import android.content.Context;
import android.database.Cursor;

import com.example.ecofit.DB.MyDatabaseHelper;

import java.net.ContentHandler;

public class Repository {
    MyDatabaseHelper myDatabaseHelper;

    public Repository(Context context)
    {
        myDatabaseHelper = new MyDatabaseHelper(context);
    }

    public void addUser(  String name, String Lname, String pass, String phone, int price) { myDatabaseHelper.addUser(name, Lname, pass, phone, price);}

    public boolean CheckIfExist(String phone, String pass)
    {
        return myDatabaseHelper.CheckIfExist(myDatabaseHelper.getIDByPhone(phone), pass);
    }
    public String GetNameByPhone(String phone)
    {
        return myDatabaseHelper.getNameByPhone(phone);
    }
}
