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

    // פעולה שיוצאת מהמשתמש
    public void LogOut(){
        rep.LogOut();
    }

    // פעולה שלוקחת את כל האנשים שנרשמו למשימה ומביאה את השם ומשפר הטלפון שלהם
    public void ReadDocument(String whichTask, MyFireBaseHelper.gotUser callback) {
        rep.ReadDocument(whichTask, callback);
    }

    // פעולה שמביאה לי את כל המשימות
    public void GetAllTasks(MyFireBaseHelper.getTasks callback){
        rep.GetAllTasks(callback);
    }

    // פעולה שמוחקת משתמש מה FB
    public void DelFromFireStore(String whichTask, int idToDel){
        rep.DelFromFireStore(whichTask,idToDel);
    }
    // פעולה שמעדכנת את מספר המטבות
    public void UpdateDataFB(String phone, String whichTask, int idToDel, int toApp, MyFireBaseHelper.whenDone callBack){
        rep.UpdateDataFB(phone,"" ,"" ,"" ,0, whichTask,idToDel,toApp,callBack);
    }
    // פעולה שמביא לי את השם הפרטי
    public String GetName()
    {
        return rep.getNameSharedPreferences();
    }

    // פעולה שמביא את מספר המטבעות
    public void GetNumberOfCoinsByPhone(String phone, MyFireBaseHelper.gotCoin callback)
    {
        rep.GetNumberOfCoinsByPhone(phone, callback);
    }

    // פעולה שמביאה מספר טלפון
    public String getPhoneNumber(){
        return rep.getPhoneNumberSharedPreferences();
    }
}
