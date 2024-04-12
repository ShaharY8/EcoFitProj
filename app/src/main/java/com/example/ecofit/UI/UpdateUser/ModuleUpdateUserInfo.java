package com.example.ecofit.UI.UpdateUser;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

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

    public void saveAtSharedPreferences(String phone){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("UserPhone", phone);
        editor.putString("UserName", rep.GetNameByPhoneSQL(phone));
        editor.putString("UserLname", rep.getlLnameByPhoneSQL(phone));
        editor.putString("UserPass", rep.getPassByPhoneSQL(phone));
        editor.apply();
    }
    public void updateUser(String row_id,String name,String Lname,String pass, String phone, int price){
        rep.updateUserSQL(row_id,name,Lname,pass ,phone,0);
    }
    public String getIdByPhoneNumber(String phone){
        return rep.getIDByPhoneSQL(phone);
    }
}
