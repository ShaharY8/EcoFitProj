package com.example.ecofit.UI.SignUp;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.ecofit.Repository.Repository;

public class ModuleSignUp {
    private Context context;
    private SharedPreferences sharedPreferences;
    private Repository rep;
    public ModuleSignUp(Context c){
        context = c;
        sharedPreferences = context.getSharedPreferences("UserInfo", MODE_PRIVATE);
        rep = new Repository(c);
    }

    public void saveAtSharedPreferences(String phone){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("UserPhone", phone);
        editor.putString("UserName", rep.GetNameByPhone(phone));
        editor.putString("UserLname", rep.getlLnameByPhone(phone));
        editor.putString("UserPass", rep.getPassByPhone(phone));
        editor.apply();
    }
    public void addUser(String name,String Lname,String pass, String phone, int price){
        rep.addUser(name,Lname,pass ,phone,0);
    }
}
