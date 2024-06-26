package com.example.ecofit.UI.Home;

import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.util.Base64;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.ecofit.DB.MyFireBaseHelper;
import com.example.ecofit.Repository.Repository;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;


public class ModuleHome {
    private Context context;

    private Repository rep;
    public ModuleHome(Context c){
        context = c;
        rep = new Repository(context);
    }
    // מאחזר את מספר הטלפון של המשתמש מ-SharedPreferences דרך המודול.
    public String getPhoneNumber(){
        return rep.getPhoneNumberSharedPreferences();
    }
    // מאחזר את שם המשתמש מ-SharedPreferences דרך המודול.
    public String GetName()
    {
        return rep.getNameSharedPreferences();
    }
    // מאחזר את מספר המטבעות של המשתמש לפי מספר הטלפון דרך Firebase.
    public void GetNumberOfCoinsByPhone(String phone, MyFireBaseHelper.gotCoin callback)
    {
         rep.GetNumberOfCoinsByPhone(phone, callback);
    }
    // שומר תמונת Bitmap ב-SharedPreferences לאחר המרתה למחרוזת Base64.
    public void SavePhotoAtSharedPreferences(Bitmap photo)
    {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] byteArray = baos.toByteArray();

        // המרת ה-byte array למחרוזת Base64
        String encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

        // שמירת המחרוזת ב-SharedPreferences
        rep.SaveDataAtSharedPreferences(encodedImage);
    }
    // מאחזר תמונה מקודדת מ-SharedPreferences וממיר אותה ל-Bitmap.
    public Bitmap getImageFromSharedPreferences() {
        String encodedImage = rep.GetEncodedImageSharedPreference();
        if (encodedImage != null) {
            // המרת המחרוזת Base64 ל-byte array
            byte[] byteArray = Base64.decode(encodedImage, Base64.DEFAULT);
            // המרת ה-byte array ל-Bitmap
            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        }
        return null;
    }
    // בודק אם משימה קיימת ב-Firebase לפי סוג המשימה ומספר הטלפון.
    public void checkIfTaskExists(String whichTask, String phone, MyFireBaseHelper.UserExistenceCallback callback){
        rep.checkIfUserExists(whichTask,phone,callback);
    }

    // מציג דיאלוג לבדיקת אם המשתמש רוצה להשתתף במשימה.
    public void Task(String whichTask, String title, String details){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("האם אתה רוצה להגיע?");
        builder.setMessage( title +
                "\n" + details);


        builder.setCancelable(false);

        builder.setPositiveButton("כן", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                String str = "שלום ותודה שבחרת להשתתף במשימה," +
                        " אם אתה מתחרט ואינך רוצה להגיע אתה לא צריך לשנות כלום פשוט אל תגיע.";
                // SmsManager smsManager = SmsManager.getDefault();
                String phone = getPhoneNumber();
                String name = rep.getNameSharedPreferences();

                Map<String, Object> taskUserList = new HashMap<>();
                taskUserList.put("name", name);
                taskUserList.put("phone", phone);




                rep.checkIfUserSignToATask(whichTask, phone, new MyFireBaseHelper.UserExistenceCallback() {
                    @Override
                    public void onUserExistenceChecked(boolean userExists) {
                        if(userExists == false){
                            if (ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.SEND_SMS}, 1);
                                Toast.makeText(context, "כדי להירשם למישמה אתה צריך לאפשר שליחת הודעה", Toast.LENGTH_SHORT).show();
                            } else {
                                sendSmsAndRegisterUser(phone, str, taskUserList, whichTask);
                            }
                        }
                        else{
                            Toast.makeText(context, "כבר נרשמת למשימה הזאת", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


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

    // שולח SMS
    private void sendSmsAndRegisterUser(String phone, String message, Map<String, Object> taskUserList, String whichTask) {
        rep.checkIfUserSignToATask(whichTask, phone, new MyFireBaseHelper.UserExistenceCallback() {
            @Override
            public void onUserExistenceChecked(boolean userExists) {
                if (!userExists) {
                    rep.AddDocument(taskUserList, whichTask);
                    String message = "תודה שנרשמת למשימה אם אתה לא יכול להגיע לא צריך להודיע יום טוב";
                    SmsManager.getDefault().sendTextMessage(phone, null, message, null, null);
                    Toast.makeText(context, "הודעת אישור נשלחה לטלפון הזה: " + phone + " אם זה לא הטלפון שלך אנא פתח משתמש עם הטלפון שלך", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "כבר נרשמת למשימה הזאת", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
// מבצע התנתקות של המשתמש.
    public void LogOut(){
        rep.LogOut();
    }
// מאחזר את כל המשימות מ-Firebase.

    public void GetAllTasks(MyFireBaseHelper.getTasks callback){
        rep.GetAllTasks(callback);
    }

    // מוסיף משימה חדשה ל-Firebase.
    public void AddDocument(Map<String, Object> taskUser, String whichTask) {
        rep.AddDocument(taskUser, whichTask);
    }

    // מעדכן נתונים ב-Firebase.
    public void UpdateDataFB(String phone, String whichTask, int idToDel, int toApp, MyFireBaseHelper.whenDone callBack){
        rep.UpdateDataFB(phone,"" ,"" ,"" ,0, whichTask,idToDel,toApp,callBack);
    }

}

