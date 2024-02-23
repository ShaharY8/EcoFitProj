package com.example.ecofit.UI.Main;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

public class ModuleMainActivity {
    private Context context;
    private SharedPreferences sharedPreferences;
    public ModuleMainActivity(Context c){
        context = c;
        sharedPreferences = context.getSharedPreferences("UserInfo", MODE_PRIVATE);
    }

    public boolean CheckIfUserLoggedIn(){
        String PhoneNumber = sharedPreferences.getString("UserPhone",null);
        String UserName = sharedPreferences.getString("UserName",null);

        if(PhoneNumber != null || UserName != null){
            return true;
        }
        return false;
    }
}
