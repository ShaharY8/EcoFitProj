package com.example.ecofit.UI.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecofit.R;
import com.example.ecofit.UI.Home.HomePage;
import com.example.ecofit.UI.SignUp.SignUpPage;

public class LogInPage extends AppCompatActivity {

    private Button btnLogin;
    private TextView tvSignUpLink;
    private SharedPreferences sharedPreferences;
    private  ModuleLogIn moduleLogIn;
    private EditText editTextPhoneNumber,editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_page);

        sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
        tvSignUpLink = findViewById(R.id.tvSignUpLink);
        btnLogin = findViewById(R.id.btnLogIn);
        moduleLogIn = new ModuleLogIn(this);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        if(editTextPhoneNumber.getText().toString().length() == 0){
            editTextPhoneNumber.setError("Please enter your Phone number");

        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean b = moduleLogIn.CheckIfExist(editTextPhoneNumber.getText().toString(), editTextPassword.getText().toString());
                if(b){
                    moduleLogIn.saveAtSharedPreferences(editTextPhoneNumber.getText().toString());
                    Intent intent = new Intent(LogInPage.this, HomePage.class);
                    startActivity(intent);
                }
            }
        });
        tvSignUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogInPage.this, SignUpPage.class);
                startActivity(intent);

            }
        });


    }
}