package com.example.ecofit.UI.Home;

import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.util.Base64;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import com.example.ecofit.DB.MyFireBaseHelper;
import com.example.ecofit.Repository.Repository;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class ModuleHome {
    private Context context;
    private SharedPreferences sharedPreferences;
    private Repository rep;
    public ModuleHome(Context c){
        context = c;
        rep = new Repository(context);
        sharedPreferences = context.getSharedPreferences("UserInfo", MODE_PRIVATE);
    }
    public String getPhoneNumber(){
        return sharedPreferences.getString("UserPhone", "0000000");
    }
    public String GetName()
    {
        return sharedPreferences.getString("UserName", "0000000");
    }
    public void GetNumberOfCoinsByPhone(String phone, MyFireBaseHelper.gotCoin callback)
    {
         rep.GetNumberOfCoinsByPhone(phone, callback);
    }
    public void SavePhotoAtSharedPreferences(Bitmap photo)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] byteArray = baos.toByteArray();

        // המרת ה-byte array למחרוזת Base64
        String encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

        // שמירת המחרוזת ב-SharedPreferences
        editor.putString("Photo", encodedImage);
        editor.apply();
    }
    public Bitmap getImageFromSharedPreferences() {
        String encodedImage = sharedPreferences.getString("Photo", null);
        if (encodedImage != null) {
            // המרת המחרוזת Base64 ל-byte array
            byte[] byteArray = Base64.decode(encodedImage, Base64.DEFAULT);
            // המרת ה-byte array ל-Bitmap
            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        }
        return null;
    }

    public void checkIfTaskExists(String whichTask, String phone, MyFireBaseHelper.UserExistenceCallback callback){
        rep.checkIfUserExists(whichTask,phone,callback);
    }

    public void Button1(String whichTask, String title, String details){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("האם אתה רוצה להגיע?");
        builder.setMessage( title +
                "\n" + details);


        builder.setCancelable(false);

        builder.setPositiveButton("כן", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                String str = "שלום ותודה שברחרת להשתתף במשימה ...";
                SmsManager smsManager = SmsManager.getDefault();
                String phone = getPhoneNumber();
                String name = sharedPreferences.getString("UserName","Error");
                smsManager.sendTextMessage(phone,null,str,null,null);

                Map<String, Object> taskUserList = new HashMap<>();
                taskUserList.put("name", name);
                taskUserList.put("phone", phone);
                rep.AddDocument(taskUserList,whichTask);


            }
        });

        builder.setNegativeButton("לא", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(-1).setTextColor(Color.BLUE);
        dialog.getButton(-2).setTextColor(Color.RED);
    }
    public void LogOut(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        Toast.makeText(context, "Log Out successfully", Toast.LENGTH_SHORT).show();
    }

    public void GetAllTasks(MyFireBaseHelper.getTasks callback){
        rep.GetAllTasks(callback);
    }
    public void AddDocument(Map<String, Object> taskUser, String whichTask) {
        rep.AddDocument(taskUser, whichTask);
    }


}

