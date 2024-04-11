package com.example.ecofit.UI.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.example.ecofit.UI.UpdateUser.UpdateUserInfo;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.Firebase;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomePage extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener{
    private ModuleHome moduleHome;
    private Button task1, task2, task3;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageView menu;
    private TextView nameOfUser,tvCoinNumber;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


        tvCoinNumber = findViewById(R.id.tvCoinNumber);
        task1 = findViewById(R.id.task1);
        task1.setOnClickListener(this);
        task2 = findViewById(R.id.task2);
        task2.setOnClickListener(this);
        task3 = findViewById(R.id.task3);
        task3.setOnClickListener(this);
        nameOfUser = findViewById(R.id.nameOfUser);

        moduleHome = new ModuleHome(this);

        drawerLayout = findViewById(R.id.drawerlayout);


        menu = findViewById(R.id.hamburgerIcon);
        menu.setOnClickListener(this);


        navigationView = findViewById(R.id.nav_drawer);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);


        // מתחיל את הפעולות
        ChangeName();
        changeNumberOfCoins();

        //AddApprovalButton();



    }
    public void AddApprovalButton(){
        String phone = moduleHome.GetNameByPhone();
        Intent intent = new Intent(HomePage.this, ApprovalPage.class);
        startActivity(intent);

        if("0549044534" == "0549044534"){
            Button helpBtn1 = new Button(this);

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
    public void onClick(View view) {
        if(task1 == view){
            ActivityCompat.requestPermissions((Activity) this, new String[]{Manifest.permission.SEND_SMS},1);
            moduleHome.Button1();
        }
        if (menu == view)
        {
            drawerLayout.openDrawer(GravityCompat.START);

        }
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