package com.example.ecofit.UI.UpdateUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecofit.Approval.ApprovalPage;
import com.example.ecofit.DB.MyFireBaseHelper;
import com.example.ecofit.R;
import com.example.ecofit.UI.Home.HomePage;
import com.example.ecofit.UI.Login.LogInPage;
import com.example.ecofit.UI.Main.MainActivity;
import com.example.ecofit.UI.Shop.Shop;
import com.example.ecofit.UI.SignUp.ModuleSignUp;
import com.google.android.material.navigation.NavigationView;

public class UpdateUserInfo extends AppCompatActivity implements View.OnClickListener , NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageView menu;
    private EditText editTextFirstName , editTextLastName , editTextPassword;
    private Button updateButton;

    private SharedPreferences sharedPreferences;
    private ModuleUpdateUserInfo moduleUpdateUserInfo;
    private String oldPhoneNumber;

    private TextView tvCoinNumber,tvNameOfUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_info);


        tvCoinNumber = findViewById(R.id.coinNumber);
        tvNameOfUser = findViewById(R.id.nameOfUser);

        moduleUpdateUserInfo = new ModuleUpdateUserInfo(this);

        sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
        oldPhoneNumber = sharedPreferences.getString("UserPhone",null);


        editTextFirstName = findViewById(R.id.firstNameEditText);
        editTextLastName = findViewById(R.id.lastNameEditText);
        editTextPassword = findViewById(R.id.passwordEditText);

        updateButton = findViewById(R.id.updateButton);



        drawerLayout = findViewById(R.id.drawerLayout);


        menu = findViewById(R.id.hamburgerIcon);
        menu.setOnClickListener(this);


        navigationView = findViewById(R.id.nav_drawer);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        addDefaultUserInfo();

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isValidInput()){
                    if(oldPhoneNumber != null){
                        if(isValidInput()){

                            moduleUpdateUserInfo.GetNumberOfCoinsByPhone(moduleUpdateUserInfo.getPhoneNumber(), new MyFireBaseHelper.gotCoin() {
                                @Override
                                public void onGotCoin(int coin) {

                                    moduleUpdateUserInfo.updateUser(moduleUpdateUserInfo.getIdByPhoneNumber(oldPhoneNumber),editTextFirstName.getText().toString(),editTextLastName.getText().toString()
                                            , editTextPassword.getText().toString()
                                            ,moduleUpdateUserInfo.getPhoneNumber(),coin);

                                    moduleUpdateUserInfo.saveAtSharedPreferences(editTextFirstName.getText().toString(),editTextLastName.getText().toString()
                                            , editTextPassword.getText().toString());


                                    moduleUpdateUserInfo.UpdateDataFB(oldPhoneNumber, editTextFirstName.getText().toString(), editTextLastName.getText().toString(),
                                            editTextPassword.getText().toString(), coin, "UsersList", 0, 1, new MyFireBaseHelper.whenDone() {
                                                @Override
                                                public void whenDoneToUpdate() {
                                                    Intent intent = new Intent(UpdateUserInfo.this, HomePage.class);
                                                    startActivity(intent);
                                                }
                                            });
                                }
                            });
                        }
                    }
                    else
                    {
                        Toast.makeText(UpdateUserInfo.this, "Error", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });


        changeNumberOfCoins();
        ChangeName();
    }

    public void onClick(View view) {
        if (menu == view)
        {
            drawerLayout.openDrawer(GravityCompat.START);

        }
    }
    public void addDefaultUserInfo(){
        editTextFirstName.setText(sharedPreferences.getString("UserName",null));
        editTextLastName.setText(sharedPreferences.getString("UserLname",null));
        editTextPassword.setText(sharedPreferences.getString("UserPass",null));
    }
    private boolean isValidInput() {
        // Add your validation checks here
        boolean isValid = true;

        if (TextUtils.isEmpty(editTextFirstName.getText().toString())) {
            editTextFirstName.setError("Please enter your first name");
            isValid = false;
        }

        if (TextUtils.isEmpty(editTextLastName.getText().toString())) {
            editTextLastName.setError("Please enter your last name");
            isValid = false;
        }

        if (TextUtils.isEmpty(editTextPassword.getText().toString())) {
            editTextPassword.setError("Please enter a password");
            isValid = false;
        }

        return isValid;
    }


    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id._homePage){
            Intent intent = new Intent(UpdateUserInfo.this, HomePage.class);
            startActivity(intent);
        }
        else if (id == R.id._profile) {
            Intent intent = new Intent(UpdateUserInfo.this, UpdateUserInfo.class);
            startActivity(intent);
        } else if (id == R.id._Shop) {
            Intent intent = new Intent(UpdateUserInfo.this, Shop.class);
            startActivity(intent);
        }
        else if (id == R.id._logOut) {
            moduleUpdateUserInfo.LogOut();
            Intent intent = new Intent(UpdateUserInfo.this, MainActivity.class);
            startActivity(intent);
        }

        return false;
    }

    public void ChangeName(){

        String name = moduleUpdateUserInfo.GetName();
        if(name != null){
            tvNameOfUser.setText(name + "");
        }
        else {
            tvNameOfUser.setText("Error");
        }
    }
    public void changeNumberOfCoins(){
        moduleUpdateUserInfo.GetNumberOfCoinsByPhone(moduleUpdateUserInfo.getPhoneNumber(), new MyFireBaseHelper.gotCoin() {
            @Override
            public void onGotCoin(int coin) {
                tvCoinNumber.setText("מספר המטבעות \n שלך הוא: " + coin);
            }
        });
    }


}