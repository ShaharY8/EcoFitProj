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

    public void LogOut(){
        rep.LogOut();
    }


    public void GetNumberOfCoinsByPhone(String phone, MyFireBaseHelper.gotCoin callback)
    {
        rep.GetNumberOfCoinsByPhone(phone, callback);
    }
    public String getPhoneNumber(){
        return  rep.getPhoneNumberSharedPreferences();
    }
    public String GetNameByPhone()
    {
        return rep.getNameSharedPreferences();
    }

    public void UpdateDataFB(String phone, String whichTask, int idToDel, int toApp, MyFireBaseHelper.whenDone callBack){
        rep.UpdateDataFB(phone,"" ,"" ,"" ,0, whichTask,idToDel,toApp,callBack);
    }
    public void updatePlan(String row_id, boolean IsGym, boolean IsHome, boolean IsHomeAndGym){ rep.updatePlan(row_id,IsGym,IsHome,IsHomeAndGym);}
    public String getIdByPhoneNumber(){
        return rep.getIDByPhoneSQL(rep.getPhoneNumberSharedPreferences());
    }
    public boolean CheckIfPlanBought(String phone, String whichPlan)
    {return rep.CheckIfPlanBought(phone,whichPlan);}
}
