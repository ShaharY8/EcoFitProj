package com.example.ecofit.Repository;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.ecofit.DB.MyDatabaseHelper;
import com.example.ecofit.DB.MyFireBaseHelper;
import com.google.firebase.firestore.FirebaseFirestore;

import java.net.ContentHandler;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class Repository {


    ////////////////////////   SQLite
    MyDatabaseHelper myDatabaseHelper;
    MyFireBaseHelper myFireBaseHelper;
    SharedPreferences sharedPreferences;
    Context context;
    public Repository(Context context) {
        this.context = context;
        myDatabaseHelper = new MyDatabaseHelper(context);
        myFireBaseHelper = new MyFireBaseHelper(context);
        sharedPreferences = context.getSharedPreferences("UserInfo", MODE_PRIVATE);
    }

    // מוסיף משתמש ל SQLite
    public void addUser(String name, String Lname, String pass, String phone, int price) {
        myDatabaseHelper.addUser(name, Lname, pass, phone, price);
    }
    // בודק אם משתמש קיים
    public boolean CheckIfExist(String phone, String pass) {
        return myDatabaseHelper.CheckIfExist(phone, pass);
    }
    // מקבל את השם של משתמש לפי הטלפון מהטבלת SQLite
    public String GetNameByPhoneSQL(String phone) {
        return myDatabaseHelper.getNameByPhone(phone);
    }
    // מקבל את הסיסמא של משתמש לפי הטלפון מהטבלת SQLite
    public String getPassByPhoneSQL(String phone) {
        return myDatabaseHelper.getPassByPhone(phone);
    }
    // מקבל את שם המשפחה של משתמש לפי הטלפון מהטבלת SQLite
    public String getlLnameByPhoneSQL(String phone) {
        return myDatabaseHelper.getlLnameByPhone(phone);
    }
    // מעדכן את הטבלת SQLite
    public void updateUserSQL(String row_id, String name, String Lname, String pass, String phone, int price) {
        myDatabaseHelper.updateUser(row_id, name, Lname, pass, phone, price);
    }
    // מקבל את ה- ID
    public String getIDByPhoneSQL(String phone) {
        return myDatabaseHelper.getIDByPhone(phone);
    }
    // מעדכן את תוכניות האימון אם נקנו
    public void updatePlan(String row_id, boolean IsGym, boolean IsHome, boolean IsHomeAndGym){ myDatabaseHelper.updatePlan(row_id,IsGym,IsHome,IsHomeAndGym);}
    // בודק אם תוענית מסויימת נקנת
    public boolean CheckIfPlanBought(String phone, String whichPlan)
    {return myDatabaseHelper.CheckIfPlanBought(phone,whichPlan);}
    ///////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////// /////////////////////////////////// FireBase
    /////////////////////////////////////////////////////////////////////////////////////////

    // מוסיף משתמש לטבלה לפי הצורך
    public void AddDocument(Map<String, Object> taskUser, String whichTask) {
        myFireBaseHelper.AddDocument(taskUser, whichTask);
    }
    // פעולה אשר מקבלת מה FB רשימה של האנשים שנרשמו למשימה שהמנהל בחר
    public void ReadDocument(String whichTask, MyFireBaseHelper.gotUser callback) {
        myFireBaseHelper.ReadDocument(whichTask, callback);
    }
    // פעולה שמוחקת מה FB
    public void DelFromFireStore(String whichTask, int idToDel){
        myFireBaseHelper.DelFromFireStore(whichTask,idToDel);
    }
    // פעולה שבודקת אם משתמש עם אותו מספר טלפון נוצר כבר או אם משימה באותו הם נוצרה כבר
    public void checkIfUserExists(String whichTask,String phone,MyFireBaseHelper.UserExistenceCallback callback) {
        myFireBaseHelper.checkIfUserExists(whichTask,phone, callback);
    }
    // בודק אם משתמש נרשם לאחת המשימות
    public void checkIfUserSignToATask(String whichTask,String phone,MyFireBaseHelper.UserExistenceCallback callback) {
        myFireBaseHelper.checkIfUserSignToATask(whichTask,phone, callback);
    }
    // מקבל את מספר המטבות של המשתמש
    public void GetNumberOfCoinsByPhone(String phone, MyFireBaseHelper.gotCoin callback) {
        myFireBaseHelper.GetNumberOfCoinsByPhone( phone, callback);
    }
    // מעדכן נתונים ב FB
    public void UpdateDataFB(String phone , String name, String lname, String pass, int price, String whichTask, int idToDel,int toApp, MyFireBaseHelper.whenDone callBack){
        myFireBaseHelper.GetDataToUpdate(phone,name,lname,pass,price,whichTask,idToDel,toApp,callBack);
    }
    // מקבל את כל המשימות שיש באפליקציה
    public void GetAllTasks(MyFireBaseHelper.getTasks callback){
        myFireBaseHelper.GetAllTasks(callback);
    }

    //////////////////////////////////////////////////
    //////////////////////////////////////////////////  SharedPreferences
    /////////////////////////////////////////////////

    // שומר מחרוזת תמונה מקודדת ב-SharedPreferences.
    public void SaveDataAtSharedPreferences(String encodedImage){
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("Photo", encodedImage);
        editor.apply();
    }

    // מאחזר את מספר הטלפון של המשתמש מ-SharedPreferences.
    public String getPhoneNumberSharedPreferences(){
        return sharedPreferences.getString("UserPhone", "0000000");
    }
    // מאחזר את שם המשתמש מ-SharedPreferences.
    public String getNameSharedPreferences(){
        return sharedPreferences.getString("UserName", "0000000");
    }
    // מאחזר את מחרוזת התמונה המקודדת מ-SharedPreferences.
    public String GetEncodedImageSharedPreference(){
        return sharedPreferences.getString("Photo", null);
    }
    // מבצע התנתקות על ידי ניקוי כל הנתונים ב-SharedPreferences ומציג הודעת התנתקות.
    public void LogOut(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        Toast.makeText(context, "Log Out successfully", Toast.LENGTH_SHORT).show();
    }

    // שומר פרטי משתמש ב-SharedPreferences בזמן ההתחברות.
    public void saveAtSharedPreferencesFromLogIn(String phone){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("UserPhone", phone);
        editor.putString("UserName", GetNameByPhoneSQL(phone));
        editor.putString("UserLname", getlLnameByPhoneSQL(phone));
        editor.putString("UserPass", getPassByPhoneSQL(phone));
        editor.apply();
    }
    // בודק אם משתמש מחובר על ידי אימות אם מספר הטלפון או שם המשתמש שמורים ב-SharedPreferences.
    public boolean CheckIfUserLoggedInInSharedPreferences(){
        String PhoneNumber = sharedPreferences.getString("UserPhone",null);
        String UserName = sharedPreferences.getString("UserName",null);

        if(PhoneNumber != null || UserName != null){
            return true;
        }
        return false;
    }
    // מעדכן את שם המשתמש, שם המשפחה והסיסמה ב-SharedPreferences.
    public void UpdateSharedPreference(String name, String lname, String pass){
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("UserName", name);
        editor.putString("UserLname", lname);
        editor.putString("UserPass", pass);
        editor.apply();
    }
}
