package com.example.ecofit.UI.UpdateUser;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.ecofit.DB.MyFireBaseHelper;
import com.example.ecofit.Repository.Repository;

public class ModuleUpdateUserInfo {
    private Context context;
    private SharedPreferences sharedPreferences;
    private Repository rep;
    public ModuleUpdateUserInfo(Context c){
        context = c;
        rep = new Repository(c);
        sharedPreferences = context.getSharedPreferences("UserInfo", MODE_PRIVATE);
    }

    public void saveAtSharedPreferences(String name, String lname, String pass){
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("UserName", name);
        editor.putString("UserLname", lname);
        editor.putString("UserPass", pass);
        editor.apply();
    }
    public void updateUser(String row_id,String name,String Lname,String pass, String phone, int price){
        rep.updateUserSQL(row_id,name,Lname,pass ,phone,0);
    }
    public String getIdByPhoneNumber(String phone){
        return rep.getIDByPhoneSQL(phone);
    }

    public void UpdateDataFB(String phone, String name, String lname, String pass, int price, String whichTask, int idToDel, boolean toApp){
        rep.UpdateDataFB(phone,name ,lname ,pass ,price, whichTask,idToDel,toApp);
    }

    public String GetNameByPhone()
    {
        return sharedPreferences.getString("UserName", "0000000");
    }
    public void GetNumberOfCoinsByPhone(String phone, MyFireBaseHelper.gotCoin callback)
    {
        rep.GetNumberOfCoinsByPhone(phone, callback);
    }
    public String getPhoneNumber(){
        return sharedPreferences.getString("UserPhone", "0000000");
    }

    public void LogOut(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        Toast.makeText(context, "Log Out successfully", Toast.LENGTH_SHORT).show();
    }
}
