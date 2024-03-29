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

    public void DelFromFireStor(String whichTask, int idToDel){
        rep.DelFromFireStor(whichTask,idToDel);
    }
}
