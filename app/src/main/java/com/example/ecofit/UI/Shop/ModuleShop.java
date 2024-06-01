package com.example.ecofit.UI.Shop;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.ecofit.DB.MyFireBaseHelper;
import com.example.ecofit.Repository.Repository;

public class ModuleShop {

    private Context context;
    private Repository rep;
    public ModuleShop(Context c){
        context = c;
        rep = new Repository(c);
    }

    // מבצע התנתקות של המשתמש.
    public void LogOut(){
        rep.LogOut();
    }

    // מאחזר את מספר המטבעות של המשתמש לפי מספר הטלפון דרך Firebase.
    public void GetNumberOfCoinsByPhone(String phone, MyFireBaseHelper.gotCoin callback)
    {
        rep.GetNumberOfCoinsByPhone(phone, callback);
    }
    // מאחזר את מספר הטלפון של המשתמש מ-SharedPreferences.
    public String getPhoneNumber(){
        return  rep.getPhoneNumberSharedPreferences();
    }
    // מאחזר את שם המשתמש מ-SharedPreferences
    public String GetNameByPhone()
    {
        return rep.getNameSharedPreferences();
    }

    // מעדכן נתונים ב-Firebase.
    public void UpdateDataFB(String phone, String whichTask, int idToDel, int toApp, MyFireBaseHelper.whenDone callBack){
        rep.UpdateDataFB(phone,"" ,"" ,"" ,0, whichTask,idToDel,toApp,callBack);
    }
    // מעדכן תוכנית לפי מזהה, אם היא מכילה חדר כושר, בית או שניהם.
    public void updatePlan(String row_id, boolean IsGym, boolean IsHome, boolean IsHomeAndGym){ rep.updatePlan(row_id,IsGym,IsHome,IsHomeAndGym);}
    // מאחזר ID של משתמש לפי מספר הטלפון דרך SQL.
    public String getIdByPhoneNumber(){
        return rep.getIDByPhoneSQL(rep.getPhoneNumberSharedPreferences());
    }
    // בודק אם תוכנית נרכשה לפי מספר הטלפון וסוג התוכנית.
    public boolean CheckIfPlanBought(String phone, String whichPlan)
    {return rep.CheckIfPlanBought(phone,whichPlan);}
}
