package com.example.ecofit.UI.Login;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.ecofit.Repository.Repository;

public class ModuleLogIn {
    private Repository rep;
    private SharedPreferences sharedPreferences;
    private Context context;
    public ModuleLogIn(Context c){
        context = c;
        sharedPreferences = context.getSharedPreferences("UserInfo", MODE_PRIVATE);
        rep = new Repository(context);
    }

    public void saveAtSharedPreferences(String phone){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("UserPhone", phone);
        editor.putString("UserName", rep.GetNameByPhoneSQL(phone));
        editor.putString("UserLname", rep.getlLnameByPhoneSQL(phone));
        editor.putString("UserPass", rep.getPassByPhoneSQL(phone));
        editor.apply();
    }
    public boolean CheckIfExist(String phone, String pass){
        return rep.CheckIfExist(phone,pass);
    }

    public String GetName(String phone) { return rep.GetNameByPhoneSQL(phone);}

}
