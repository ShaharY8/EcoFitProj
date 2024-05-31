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

    public void saveAtSharedPreferences(String name, String lname, String pass){
        rep.UpdateSharedPreference(name,lname,pass);
    }
    public void updateUser(String row_id,String name,String Lname,String pass, String phone, int price){
        rep.updateUserSQL(row_id,name,Lname,pass ,phone,0);
    }
    public String getIdByPhoneNumber(String phone){
        return rep.getIDByPhoneSQL(phone);
    }

    public void UpdateDataFB(String phone, String name, String lname, String pass, int price, String whichTask, int idToDel, int toApp, MyFireBaseHelper.whenDone callBck){
        rep.UpdateDataFB(phone,name ,lname ,pass ,price, whichTask,idToDel,toApp,callBck);
    }

    public String GetName()
    {
        return rep.getNameSharedPreferences();
    }
    public void GetNumberOfCoinsByPhone(String phone, MyFireBaseHelper.gotCoin callback)
    {
        rep.GetNumberOfCoinsByPhone(phone, callback);
    }
    public String getPhoneNumber(){
        return rep.getPhoneNumberSharedPreferences();
    }

    public void LogOut(){
        rep.LogOut();
    }
}
