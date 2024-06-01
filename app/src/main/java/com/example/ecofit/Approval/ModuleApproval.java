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

    public ModuleApproval(Context c){
        context = c;
        rep = new Repository(context);
    }

    public void LogOut(){
        rep.LogOut();
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
    public String GetName()
    {
        return rep.getNameSharedPreferences();
    }
    public void GetNumberOfCoinsByPhone(String phone, MyFireBaseHelper.gotCoin callback)
    {
        rep.GetNumberOfCoinsByPhone(phone, callback);
    }
    public String getPhoneNumber(){
        return rep.getPhoneNumberSharedPreferences();
    }
}
