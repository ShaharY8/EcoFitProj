package com.example.ecofit.UI.Login;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.ecofit.Repository.Repository;

public class ModuleLogIn {
    private Repository rep;
    private Context context;
    public ModuleLogIn(Context c){
        context = c;
        rep = new Repository(context);
    }

    public void saveAtSharedPreferences(String phone){
        rep.saveAtSharedPreferencesFromLogIn(phone);
    }
    public boolean CheckIfExist(String phone, String pass){
        return rep.CheckIfExist(phone,pass);
    }

}
