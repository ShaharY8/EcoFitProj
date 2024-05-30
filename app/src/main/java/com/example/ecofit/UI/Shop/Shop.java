package com.example.ecofit.UI.Shop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecofit.DB.MyFireBaseHelper;
import com.example.ecofit.R;
import com.example.ecofit.UI.Home.HomePage;
import com.example.ecofit.UI.Home.ModuleHome;
import com.example.ecofit.UI.Main.MainActivity;
import com.example.ecofit.UI.UpdateUser.UpdateUserInfo;
import com.google.android.material.navigation.NavigationView;

public class Shop extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageView menu;
    private ModuleShop moduleShop;

    private TextView tvNameOfUser,tvCoinNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        drawerLayout = findViewById(R.id.drawerlayout);
        moduleShop = new ModuleShop(this);

        menu = findViewById(R.id.hamburgerIcon);
        menu.setOnClickListener(this);


        navigationView = findViewById(R.id.nav_drawer);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        tvNameOfUser = findViewById(R.id.nameOfUser);
        tvCoinNumber = findViewById(R.id.tvCoinNumber);

        ChangeName();
        changeNumberOfCoins();


    }

    @Override
    public void onClick(View view) {
        if (menu == view)
        {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id._profile) {
            Intent intent = new Intent(Shop.this, UpdateUserInfo.class);
            startActivity(intent);
        } else if (id == R.id._homePage) {
            Intent intent = new Intent(Shop.this, HomePage.class);
            startActivity(intent);
        } else if (id == R.id._logOut) {
            moduleShop.LogOut();
            Intent intent = new Intent(Shop.this, MainActivity.class);
            startActivity(intent);
        }
        return false;
    }
    public void ChangeName(){

        String name = moduleShop.GetNameByPhone();
        if(name != null){
            tvNameOfUser.setText(name + "");
        }
        else {
            tvNameOfUser.setText("Error");
        }
    }
    public void changeNumberOfCoins(){
        moduleShop.GetNumberOfCoinsByPhone(moduleShop.getPhoneNumber(), new MyFireBaseHelper.gotCoin() {
            @Override
            public void onGotCoin(int coin) {
                tvCoinNumber.setText("מספר המטבעות \n שלך הוא: " + coin);
            }
        });
    }
}