package com.example.ecofit.UI.SignUp;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.ecofit.DB.MyFireBaseHelper;
import com.example.ecofit.Repository.Repository;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ModuleSignUp {
    private Context context;
    private SharedPreferences sharedPreferences;
    private Repository rep;
    public ModuleSignUp(Context c){
        context = c;
        sharedPreferences = context.getSharedPreferences("UserInfo", MODE_PRIVATE);
        rep = new Repository(c);
    }

    public void saveAtSharedPreferences(String phone){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("UserPhone", phone);
        editor.putString("UserName", rep.GetNameByPhone(phone));
        editor.putString("UserLname", rep.getlLnameByPhone(phone));
        editor.putString("UserPass", rep.getPassByPhone(phone));
        editor.apply();
    }
    public void addUser(String name,String Lname,String pass, String phone, int price){
        rep.addUser(name,Lname,pass ,phone,0);
    }
    public void AddDocument(String name,String Lname,String pass, String phone, int price) {

        ////////Add to fireStore
        Map<String, Object> UsersList = new HashMap<>();
        UsersList.put("name", name);
        UsersList.put("Lname", Lname);
        UsersList.put("pass", pass);
        UsersList.put("phone", phone);
        UsersList.put("price", 0);
        rep.AddDocument(UsersList,"UsersList");
    }
    public void checkIfUserExists(String phone, MyFireBaseHelper.UserExistenceCallback callback){
        rep.checkIfUserExists(phone,callback);
    }

    public void ReadDocument(String whichTask, MyFireBaseHelper.gotUser callback) {
        rep.ReadDocument(whichTask, callback);
    }
}
