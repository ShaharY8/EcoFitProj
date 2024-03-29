package com.example.ecofit.UI.Home;

import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import com.example.ecofit.Repository.Repository;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class ModuleHome {
    private Context context;
    private SharedPreferences sharedPreferences;
    private FirebaseFirestore db;
    private Repository rep;
    public ModuleHome(Context c){
        db = FirebaseFirestore.getInstance();
        context = c;
        rep = new Repository(context);
        sharedPreferences = context.getSharedPreferences("UserInfo", MODE_PRIVATE);
    }
    public String getPhoneNumber(){
        return sharedPreferences.getString("UserPhone", "0000000");
    }
    public String GetNameByPhone()
    {
        return sharedPreferences.getString("UserPhone", "0000000");
    }
    public void Button1(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("האם אתה רוצה להגיע?");
        builder.setMessage("מישמה: ניקוי יער בן שמן\n" +
                "\n" +
                "תיאור המישמה:\n" +
                "המישמה לניקוי יער בן שמן היא פעילות משמעותית המתבצעת במטרה לשמור על איכות הסביבה ולשמור על בריאותו של היער והמערכת האקולוגית שבו. הפעילות כוללת את הסרת פסולת, פסולת אורגנית ולא אורגנית, אבנים וקרצופים, ענפים ועצים נפלים, פריחת צמחים ממותקים ומשאבי אנרגיה לא אחראיים, כמו גם כל פעילות אחרת המפריעה לאיזון האקולוגי של היער. פעילות זו מתבצעת באמצעות מתנדבים, מארגנים מקומיים, ולעיתים גם על ידי מוסדות וחברות המתעסקות בשימור הסביבה. המישמה מקדמת מודעות סביבתית ומשפיעה ישירות על שיפור איכות האוויר והסביבה הטבעית באזור."
                + "\n" + " בתאריך ה- 23.4.2024"
                + "\n" + "בשעה 8:00");



        builder.setCancelable(false);

        builder.setPositiveButton("כן", new AAA());
        builder.setNegativeButton("לא", new AAA());

        AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(-1).setTextColor(Color.BLUE);
        dialog.getButton(-2).setTextColor(Color.RED);
    }

    private class AAA implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            if(i == -1){
                String str = "שלום ותודה שברחרת להשתתף במשימה ...";
                SmsManager smsManager = SmsManager.getDefault();
                String phone = getPhoneNumber();
                String name = sharedPreferences.getString("UserName","Error");
                smsManager.sendTextMessage(phone,null,str,null,null);

                Map<String, Object> taskUserList = new HashMap<>();
                taskUserList.put("name", name);
                taskUserList.put("phone", phone);
                rep.AddDocument(db,taskUserList,"CleanForest");



            }
            if(i == -2){
                Toast.makeText(context, "לא", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void LogOut(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        Toast.makeText(context, "Log Out successfully", Toast.LENGTH_SHORT).show();
    }

}

