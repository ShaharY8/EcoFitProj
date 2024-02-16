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


public class ModuleHome {
    private Context context;
    private SharedPreferences sharedPreferences;
    public ModuleHome(Context c){
        context = c;
        sharedPreferences = context.getSharedPreferences("UserInfo", MODE_PRIVATE);
    }
    public String getPhoneNumber(){
        return sharedPreferences.getString("UserPhone", "0000000");
    }

    public void Button1(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("האם אתה רוצה להגיע?");
        builder.setMessage("האירוע מתקיים .......");
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
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.SEND_SMS},1);
                String str = "שלום ותודה שברחרת להשתתף במשימה ...";
                SmsManager smsManager = SmsManager.getDefault();
                String phone = getPhoneNumber();
                smsManager.sendTextMessage(phone,null,str,null,null);

            }
            if(i == -2){
                Toast.makeText(context, "לא", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

