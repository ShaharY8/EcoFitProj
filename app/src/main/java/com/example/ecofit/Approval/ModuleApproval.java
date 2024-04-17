package com.example.ecofit.Approval;


import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.ecofit.DB.MyFireBaseHelper;
import com.example.ecofit.Repository.Repository;
import com.google.firebase.firestore.FirebaseFirestore;

public class ModuleApproval {

    private Context context;
    Repository rep;
    private SharedPreferences sharedPreferences;
    public ModuleApproval(Context c){
        context = c;
        rep = new Repository(context);
        sharedPreferences = context.getSharedPreferences("UserInfo", MODE_PRIVATE);
    }

    public void LogOut(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        Toast.makeText(context, "Log Out successfully", Toast.LENGTH_SHORT).show();
    }

    public void ReadDocument(String whichTask, MyFireBaseHelper.gotUser callback) {
        rep.ReadDocument(whichTask, callback);
    }
    public void GetAllTasks(MyFireBaseHelper.getTasks callback){
        rep.GetAllTasks(callback);
    }
    public void DelFromFireStore(String whichTask, int idToDel){
        rep.DelFromFireStore(whichTask,idToDel);
    }

    public void UpdateDataFB(String phone, String whichTask, int idToDel, int toApp, MyFireBaseHelper.whenDone callBack){
        rep.UpdateDataFB(phone,"" ,"" ,"" ,0, whichTask,idToDel,toApp,callBack);
    }
    public String GetNameByPhone()
    {
        return sharedPreferences.getString("UserName", "0000000");
    }
    public void GetNumberOfCoinsByPhone(String phone, MyFireBaseHelper.gotCoin callback)
    {
        rep.GetNumberOfCoinsByPhone(phone, callback);
    }
    public String getPhoneNumber(){
        return sharedPreferences.getString("UserPhone", "0000000");
    }
}
