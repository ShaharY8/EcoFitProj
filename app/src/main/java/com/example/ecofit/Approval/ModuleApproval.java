package com.example.ecofit.Approval;


import android.content.Context;

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

    public void TakeListOfUsers(String task){

    }

    public void ReadDocument(String whichTask, MyFireBaseHelper.gotUser callback) {
        rep.ReadDocument(whichTask, callback);
    }

    public void DelFromFireStore(String whichTask, int idToDel){
        rep.DelFromFireStore(whichTask,idToDel);
    }

    public void UpdateDataFB(String phone, String whichTask, int idToDel, boolean toApp){
        rep.UpdateDataFB(phone,"" ,"" ,"" ,0, whichTask,idToDel,toApp);
    }
}
