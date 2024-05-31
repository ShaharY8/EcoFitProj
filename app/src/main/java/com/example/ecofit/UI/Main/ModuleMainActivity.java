package com.example.ecofit.UI.Main;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.ecofit.Repository.Repository;

public class ModuleMainActivity {
    private Context context;
    Repository rep;

    public ModuleMainActivity(Context c){
        context = c;
        rep = new Repository(context);
    }

    public boolean CheckIfUserLoggedIn(){
        return rep.CheckIfUserLoggedInInSharedPreferences();
    }
}
