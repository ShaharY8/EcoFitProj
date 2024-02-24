package com.example.ecofit.UI.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.ecofit.R;
import com.example.ecofit.UI.Login.LogInPage;
import com.example.ecofit.UI.Main.MainActivity;
import com.example.ecofit.UI.UpdateUser.UpdateUserInfo;
import com.google.android.material.navigation.NavigationView;

public class HomePage extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener{
    private ModuleHome moduleHome;
    private Button btn1, btn2, btn3;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageView menu;
    private TextView nameOfUser;
    private SharedPreferences sharedPreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);


        btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(this);
        btn2 = findViewById(R.id.btn2);
        btn2.setOnClickListener(this);
        btn3 = findViewById(R.id.btn3);
        btn3.setOnClickListener(this);
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




    }
    public void ChangeName(){
        String name = sharedPreferences.getString("UserName", null);
        if(name != null){
            nameOfUser.setText(name + "");
        }
        else {
            nameOfUser.setText("Error");
        }
    }

    @Override
    public void onClick(View view) {
        if(btn1 == view){
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

        if (id == R.id.profile) {
            Intent intent = new Intent(HomePage.this, UpdateUserInfo.class);
            startActivity(intent);
        } else if (id == R.id.logOut) {
           moduleHome.LogOut();
           Intent intent = new Intent(HomePage.this, MainActivity.class);
           startActivity(intent);
        }

        return false; // Return true to indicate that the item click has been handled
    }
}