package com.example.ecofit.UI.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.ecofit.Approval.ApprovalPage;
import com.example.ecofit.DB.MyFireBaseHelper;
import com.example.ecofit.R;
import com.example.ecofit.Repository.Repository;
import com.example.ecofit.UI.Login.LogInPage;
import com.example.ecofit.UI.Main.MainActivity;
import com.example.ecofit.UI.Shop.Shop;
import com.example.ecofit.UI.SignUp.SignUpPage;
import com.example.ecofit.UI.UpdateUser.UpdateUserInfo;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.Firebase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class HomePage extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener{
    private ModuleHome moduleHome;
    private Button helpBtn1, btnAdd;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageView menu;
    private TextView nameOfUser,tvCoinNumber;
    private RelativeLayout homePageId;

    private LinkedList<String> TaskNameFromFb;
    private LinkedList<String> titleTasks;
    private LinkedList<String> detailOfTasks;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);
        tvCoinNumber = findViewById(R.id.tvCoinNumber);
        homePageId = findViewById(R.id.homePageId);
        nameOfUser = findViewById(R.id.nameOfUser);

        moduleHome = new ModuleHome(this);

        drawerLayout = findViewById(R.id.drawerlayout);


        menu = findViewById(R.id.hamburgerIcon);
        menu.setOnClickListener(this);


        navigationView = findViewById(R.id.nav_drawer);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);


        // מתחיל את הפעולות
        AddTasks();

        ChangeName();
        changeNumberOfCoins();

        AddApprovalButton();


    }
    @Override
    public void onClick(View view) {
        if (menu == view)
        {
            drawerLayout.openDrawer(GravityCompat.START);

        }
        if(helpBtn1 == view){
            Intent intent = new Intent(HomePage.this, ApprovalPage.class);
            startActivity(intent);
        }
        if(btnAdd == view){
            moduleHome.GetNumberOfCoinsByPhone(moduleHome.getPhoneNumber(), new MyFireBaseHelper.gotCoin() {
                @Override
                public void onGotCoin(int coin) {
                    if(coin >= 15){
                        Dialog dialog = new Dialog(HomePage.this);
                        dialog.setCancelable(true);

                        dialog.setContentView(R.layout.creat_new_task);

                        EditText etTaskName = dialog.findViewById(R.id.TaskName);
                        EditText etTitle = dialog.findViewById(R.id.title);
                        EditText etDetails = dialog.findViewById(R.id.title);
                        Button btnSend = dialog.findViewById(R.id.btnSend);

                        btnSend.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                validateInputAndCheckUserExistence(etTaskName.getText().toString().trim(),new HomePage.ValidationCallback() {
                                    @Override
                                    public void onValidationResult(boolean isValid) {

                                        if (isValid) {
                                            String TaskName = etTaskName.getText().toString().trim();
                                            String Title = etTitle.getText().toString().trim();
                                            String Details =  etDetails.getText().toString().trim();

                                            Map<String,Object> taskInfo = new HashMap<>();
                                            taskInfo.put("TaskName", TaskName);
                                            taskInfo.put("title", Title);
                                            taskInfo.put("details", Details);

                                            //moduleHome.AddDocument(taskInfo,"AllTasks");
                                            dialog.dismiss();

                                        } else {
                                            Toast.makeText(HomePage.this, "המשימה קיימת כבר", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });




                            }
                        });

                        dialog.show();
                    }
                    else{
                        Toast.makeText(HomePage.this, "צריך לפחות 15 מטבעות כדי ליצור משימה משלך", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
        for (int i = 0; i < TaskNameFromFb.size(); i++) {
            if(findViewById(customIdMap.get(TaskNameFromFb.get(i).toString())) == view){
                ActivityCompat.requestPermissions((Activity) this, new String[]{Manifest.permission.SEND_SMS},1);
                moduleHome.Button1(TaskNameFromFb.get(i).toString(),titleTasks.get(i).toString(),detailOfTasks.get(i).toString());
            }
        }
    }
    boolean isValid;
    public  interface ValidationCallback {
        void onValidationResult(boolean isValid);
    }
    private void validateInputAndCheckUserExistence(String taskName, HomePage.ValidationCallback callback) {


        // Reset isValid to true before performing checks
        isValid = true;
        // Check if user exists
        moduleHome.checkIfTaskExists("AllTasks",taskName, new MyFireBaseHelper.UserExistenceCallback() {
            @Override
            public void onUserExistenceChecked(boolean userExists) {

                Toast.makeText(HomePage.this, "" + userExists, Toast.LENGTH_SHORT).show();

                // Pass the validation result to the callback
                if(userExists == true){
                    isValid = false;
                }
                callback.onValidationResult(isValid);
            }
        });
    }

    private Map<String, Integer> customIdMap = new HashMap<>();
    public void AddTasks(){
        moduleHome.GetAllTasks(new MyFireBaseHelper.getTasks() {
            @Override
            public void onGetTasks(LinkedList<String> TaskName, LinkedList<String> title, LinkedList<String> details) {
                TaskNameFromFb = TaskName;
                titleTasks = title;
                detailOfTasks = details;

                for (int i = 0; i <title.size() ; i++) {
                    Button button = new Button(HomePage.this);
                    button.setLayoutParams(new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.MATCH_PARENT,
                            getResources().getDimensionPixelSize(R.dimen.button_height))); // Set height dynamically
                    //button.setId(View.generateViewId()); // Generate unique ID for the button

                    // Generate unique ID for the button based on task title
                    int buttonId = View.generateViewId();
                    button.setId(buttonId);

                    // Add task title and button ID to customIdMap
                    customIdMap.put(TaskName.get(i), buttonId);


                    // Set text for each button

                    button.setText(title.get(i).toString());


                    button.setBackgroundResource(R.drawable.fix_button); // Set background drawable

                    // Set layout rules for positioning
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) button.getLayoutParams();
                    if (i == 0) {
                        params.addRule(RelativeLayout.BELOW, R.id.Top);
                    } else {
                        params.addRule(RelativeLayout.BELOW, customIdMap.get(TaskNameFromFb.get(i-1).toString()));
                    }
                    params.topMargin = getResources().getDimensionPixelSize(R.dimen.button_margin_top); // Set top margin dynamically
                    button.setLayoutParams(params);
                    button.setOnClickListener(HomePage.this);
                    // Add button to layout container
                    homePageId.addView(button);

                }

            }
        });
    }
    public void AddApprovalButton(){
        String phone = moduleHome.getPhoneNumber();

        if(phone.equals("0549044534")){
            helpBtn1 = new Button(this);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(300, 250);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            helpBtn1.setLayoutParams(layoutParams);
            helpBtn1.setText("אישור משתמשים");
            helpBtn1.setTextSize(20);
            helpBtn1.setTextColor(Color.BLACK);
            helpBtn1.setOnClickListener(this);
            homePageId.addView(helpBtn1);
        }
    }
    public void ChangeName(){

        String name = moduleHome.GetNameByPhone();
        if(name != null){
            nameOfUser.setText(name + "");
        }
        else {
            nameOfUser.setText("Error");
        }
    }
    public void changeNumberOfCoins(){
        moduleHome.GetNumberOfCoinsByPhone(moduleHome.getPhoneNumber(), new MyFireBaseHelper.gotCoin() {
            @Override
            public void onGotCoin(int coin) {
                tvCoinNumber.setText("your coins \n number is: " + coin);
            }
        });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id._profile) {
            Intent intent = new Intent(HomePage.this, UpdateUserInfo.class);
            startActivity(intent);
        } else if (id == R.id._Shop) {
            Intent intent = new Intent(HomePage.this, Shop.class);
            startActivity(intent);
        }
        else if (id == R.id._logOut) {
           moduleHome.LogOut();
           Intent intent = new Intent(HomePage.this, MainActivity.class);
           startActivity(intent);
        }

        return false; // Return true to indicate that the item click has been handled
    }
}