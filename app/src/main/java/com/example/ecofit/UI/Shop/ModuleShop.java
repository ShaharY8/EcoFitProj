package com.example.ecofit.UI.Shop;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.ecofit.DB.MyFireBaseHelper;
import com.example.ecofit.Repository.Repository;

public class ModuleShop {

    private Context context;
    private SharedPreferences sharedPreferences;
    private Repository rep;
    public ModuleShop(Context c){
        context = c;
        rep = new Repository(c);
        sharedPreferences = context.getSharedPreferences("UserInfo", MODE_PRIVATE);
    }

    public void LogOut(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        Toast.makeText(context, "Log Out successfully", Toast.LENGTH_SHORT).show();
    }


    public void GetNumberOfCoinsByPhone(String phone, MyFireBaseHelper.gotCoin callback)
    {
        rep.GetNumberOfCoinsByPhone(phone, callback);
    }
    public String getPhoneNumber(){
        return sharedPreferences.getString("UserPhone", "0000000");
    }
    public String GetNameByPhone()
    {
        return sharedPreferences.getString("UserName", "0000000");
    }

    public void UpdateDataFB(String phone, String whichTask, int idToDel, int toApp, MyFireBaseHelper.whenDone callBack){
        rep.UpdateDataFB(phone,"" ,"" ,"" ,0, whichTask,idToDel,toApp,callBack);
    }
    public void updatePlan(String row_id, boolean IsGym, boolean IsHome, boolean IsHomeAndGym){ rep.updatePlan(row_id,IsGym,IsHome,IsHomeAndGym);}
    public String getIdByPhoneNumber(){
        return rep.getIDByPhoneSQL(sharedPreferences.getString("UserPhone", "0000000"));
    }
    public boolean CheckIfPlanBought(String phone, String whichPlan)
    {return rep.CheckIfPlanBought(phone,whichPlan);}
}
