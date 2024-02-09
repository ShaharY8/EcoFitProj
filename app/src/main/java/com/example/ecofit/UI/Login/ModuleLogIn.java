package com.example.ecofit.UI.Login;

import android.content.Context;

import com.example.ecofit.Repository.Repository;

public class ModuleLogIn {
    private Repository rep;

    private Context context;
    public ModuleLogIn(Context c){
        context = c;
        rep = new Repository(context);
    }

    public boolean CheckIfExist(String phone, String pass){
        return rep.CheckIfExist(phone,pass);
    }


}
