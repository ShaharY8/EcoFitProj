package com.example.ecofit.UI.UpdateUser;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.ecofit.DB.MyFireBaseHelper;
import com.example.ecofit.Repository.Repository;

public class ModuleUpdateUserInfo {
    private Context context;
    private Repository rep;
    public ModuleUpdateUserInfo(Context c){
        context = c;
        rep = new Repository(c);
    }

    // שומר את פרטי המשתמש ב-SharedPreferences.
    public void saveAtSharedPreferences(String name, String lname, String pass){
        rep.UpdateSharedPreference(name,lname,pass);
    }
    // מעדכן את פרטי המשתמש ב-SQL לפי מזהה שורה.
    public void updateUser(String row_id,String name,String Lname,String pass, String phone, int price){
        rep.updateUserSQL(row_id,name,Lname,pass ,phone,0);
    }
    // מאחזר מזהה משתמש לפי מספר הטלפון דרך SQL.
    public String getIdByPhoneNumber(String phone){
        return rep.getIDByPhoneSQL(phone);
    }
    // מעדכן נתונים ב-Firebase.
    public void UpdateDataFB(String phone, String name, String lname, String pass, int price, String whichTask, int idToDel, int toApp, MyFireBaseHelper.whenDone callBck){
        rep.UpdateDataFB(phone,name ,lname ,pass ,price, whichTask,idToDel,toApp,callBck);
    }
    // מאחזר את שם המשתמש מ-SharedPreferences
    public String GetName()
    {
        return rep.getNameSharedPreferences();
    }
    // מאחזר את מספר המטבעות של המשתמש לפי מספר הטלפון דרך Firebase.
    public void GetNumberOfCoinsByPhone(String phone, MyFireBaseHelper.gotCoin callback)
    {
        rep.GetNumberOfCoinsByPhone(phone, callback);
    }
    // מאחזר את מספר הטלפון של המשתמש מ-SharedPreferences.
    public String getPhoneNumber(){
        return rep.getPhoneNumberSharedPreferences();
    }

    // מבצע התנתקות של המשתמש.
    public void LogOut(){
        rep.LogOut();
    }
}
