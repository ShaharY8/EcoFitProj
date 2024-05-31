package com.example.ecofit.Repository;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.ecofit.DB.MyDatabaseHelper;
import com.example.ecofit.DB.MyFireBaseHelper;
import com.google.firebase.firestore.FirebaseFirestore;

import java.net.ContentHandler;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class Repository {


    ////////////////////////   SQLite
    MyDatabaseHelper myDatabaseHelper;
    MyFireBaseHelper myFireBaseHelper;
    SharedPreferences sharedPreferences;
    Context context;
    public Repository(Context context) {
        this.context = context;
        myDatabaseHelper = new MyDatabaseHelper(context);
        myFireBaseHelper = new MyFireBaseHelper(context);
        sharedPreferences = context.getSharedPreferences("UserInfo", MODE_PRIVATE);
    }

    public void addUser(String name, String Lname, String pass, String phone, int price) {
        myDatabaseHelper.addUser(name, Lname, pass, phone, price);
    }

    public boolean CheckIfExist(String phone, String pass) {
        return myDatabaseHelper.CheckIfExist(phone, pass);
    }

    public String GetNameByPhoneSQL(String phone) {
        return myDatabaseHelper.getNameByPhone(phone);
    }

    public String getPassByPhoneSQL(String phone) {
        return myDatabaseHelper.getPassByPhone(phone);
    }

    public String getlLnameByPhoneSQL(String phone) {
        return myDatabaseHelper.getlLnameByPhone(phone);
    }

    public void updateUserSQL(String row_id, String name, String Lname, String pass, String phone, int price) {
        myDatabaseHelper.updateUser(row_id, name, Lname, pass, phone, price);
    }

    public String getIDByPhoneSQL(String phone) {
        return myDatabaseHelper.getIDByPhone(phone);
    }
    public void updatePlan(String row_id, boolean IsGym, boolean IsHome, boolean IsHomeAndGym){ myDatabaseHelper.updatePlan(row_id,IsGym,IsHome,IsHomeAndGym);}
    public boolean CheckIfPlanBought(String phone, String whichPlan)
    {return myDatabaseHelper.CheckIfPlanBought(phone,whichPlan);}
    ///////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////// /////////////////////////////////// FireBase
    /////////////////////////////////////////////////////////////////////////////////////////
    public void AddDocument(Map<String, Object> taskUser, String whichTask) {
        myFireBaseHelper.AddDocument(taskUser, whichTask);
    }

    public void ReadDocument(String whichTask, MyFireBaseHelper.gotUser callback) {
        myFireBaseHelper.ReadDocument(whichTask, callback);
    }

    public void DelFromFireStore(String whichTask, int idToDel){
        myFireBaseHelper.DelFromFireStore(whichTask,idToDel);
    }

    public void checkIfUserExists(String whichTask,String phone,MyFireBaseHelper.UserExistenceCallback callback) {
        myFireBaseHelper.checkIfUserExists(whichTask,phone, callback);
    }

    public void checkIfUserSignToATask(String whichTask,String phone,MyFireBaseHelper.UserExistenceCallback callback) {
        myFireBaseHelper.checkIfUserSignToATask(whichTask,phone, callback);
    }
    public void GetNumberOfCoinsByPhone(String phone, MyFireBaseHelper.gotCoin callback) {
        myFireBaseHelper.GetNumberOfCoinsByPhone( phone, callback);
    }

    public void UpdateDataFB(String phone , String name, String lname, String pass, int price, String whichTask, int idToDel,int toApp, MyFireBaseHelper.whenDone callBack){
        myFireBaseHelper.GetDataToUpdate(phone,name,lname,pass,price,whichTask,idToDel,toApp,callBack);
    }
    public void GetAllTasks(MyFireBaseHelper.getTasks callback){
        myFireBaseHelper.GetAllTasks(callback);
    }

    //////////////////////////////////////////////////
    //////////////////////////////////////////////////  SharedPreferences
    /////////////////////////////////////////////////

    public void SaveDataAtSharedPreferences(String encodedImage){
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("Photo", encodedImage);
        editor.apply();
    }
    public String getPhoneNumberSharedPreferences(){
        return sharedPreferences.getString("UserPhone", "0000000");
    }
    public String getNameSharedPreferences(){
        return sharedPreferences.getString("UserName", "0000000");
    }
    public String GetEncodedImageSharedPreference(){
        return sharedPreferences.getString("Photo", null);
    }
    public void LogOut(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        Toast.makeText(context, "Log Out successfully", Toast.LENGTH_SHORT).show();
    }

    public void saveAtSharedPreferencesFromLogIn(String phone){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("UserPhone", phone);
        editor.putString("UserName", GetNameByPhoneSQL(phone));
        editor.putString("UserLname", getlLnameByPhoneSQL(phone));
        editor.putString("UserPass", getPassByPhoneSQL(phone));
        editor.apply();
    }
    public boolean CheckIfUserLoggedInInSharedPreferences(){
        String PhoneNumber = sharedPreferences.getString("UserPhone",null);
        String UserName = sharedPreferences.getString("UserName",null);

        if(PhoneNumber != null || UserName != null){
            return true;
        }
        return false;
    }
    public void UpdateSharedPreference(String name, String lname, String pass){
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("UserName", name);
        editor.putString("UserLname", lname);
        editor.putString("UserPass", pass);
        editor.apply();
    }
}
