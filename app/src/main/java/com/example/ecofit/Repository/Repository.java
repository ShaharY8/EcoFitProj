package com.example.ecofit.Repository;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ecofit.DB.MyDatabaseHelper;
import com.example.ecofit.DB.MyFireBaseHelper;
import com.google.firebase.firestore.FirebaseFirestore;

import java.net.ContentHandler;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class Repository {


    ////////////////////////   SQLite
    MyDatabaseHelper myDatabaseHelper;
    MyFireBaseHelper myFireBaseHelper;

    public Repository(Context context) {
        myDatabaseHelper = new MyDatabaseHelper(context);
        myFireBaseHelper = new MyFireBaseHelper(context);
    }

    public void addUser(String name, String Lname, String pass, String phone, int price) {
        myDatabaseHelper.addUser(name, Lname, pass, phone, price);
    }

    public boolean CheckIfExist(String phone, String pass) {
        return myDatabaseHelper.CheckIfExist(myDatabaseHelper.getIDByPhone(phone), pass);
    }

    public String GetNameByPhone(String phone) {
        return myDatabaseHelper.getNameByPhone(phone);
    }

    public String getPassByPhone(String phone) {
        return myDatabaseHelper.getPassByPhone(phone);
    }

    public String getlLnameByPhone(String phone) {
        return myDatabaseHelper.getlLnameByPhone(phone);
    }

    public void updateUser(String row_id, String name, String Lname, String pass, String phone, int price) {
        myDatabaseHelper.updateUser(row_id, name, Lname, pass, phone, price);
    }

    public String getIDByPhone(String phone) {
        return myDatabaseHelper.getIDByPhone(phone);
    }


    //////////////////////////  FireBase
    public void AddDocument(Map<String, Object> taskUser, String whichTask) {
        myFireBaseHelper.AddDocument(taskUser, whichTask);
    }

    public void ReadDocument(String whichTask, MyFireBaseHelper.gotUser callback) {
        myFireBaseHelper.ReadDocument(whichTask, callback);
    }

    public void DelFromFireStore(String whichTask, int idToDel){
        myFireBaseHelper.DelFromFireStore(whichTask,idToDel);
    }

    public void checkIfUserExists(String phone,MyFireBaseHelper.UserExistenceCallback callback) {
        myFireBaseHelper.checkIfUserExists(phone, callback);
    }

//    public int GetNumberOfCoinsByPhone(String phone) {
//
//    }
}
