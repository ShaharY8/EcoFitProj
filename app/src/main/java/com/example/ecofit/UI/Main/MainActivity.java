package com.example.ecofit.UI.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ecofit.R;
import com.example.ecofit.UI.Home.HomePage;
import com.example.ecofit.UI.Login.LogInPage;
import com.example.ecofit.UI.SignUp.SignUpPage;

public class MainActivity extends AppCompatActivity {

    private Button btnLogIn,btnSignUp;
    private ModuleMainActivity moduleMainActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogIn = findViewById(R.id.btnLogIn);
        btnSignUp = findViewById(R.id.btnSignUp);
        moduleMainActivity = new ModuleMainActivity(this);

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LogInPage.class);
                startActivity(intent);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignUpPage.class);
                startActivity(intent);
            }
        });

        CheckIfUserLoggedIn();
    }

    public void CheckIfUserLoggedIn(){
        if(moduleMainActivity.CheckIfUserLoggedIn()){
            Intent intent = new Intent(MainActivity.this, HomePage.class);
            startActivity(intent);
        }
    }
}