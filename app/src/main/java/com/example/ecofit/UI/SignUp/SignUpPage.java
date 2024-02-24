package com.example.ecofit.UI.SignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecofit.DB.MyDatabaseHelper;
import com.example.ecofit.R;
import com.example.ecofit.UI.Home.HomePage;
import com.example.ecofit.UI.Login.LogInPage;

public class SignUpPage extends AppCompatActivity {

    private EditText editTextFirstName , editTextLastName , editTextPassword , editTextConfirmPassword,editTextID,editTextPhoneNumber   ;
    private Button btnSignUp;
    private TextView tvLoginLink;

    private ModuleSignUp moduleSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        moduleSignUp = new ModuleSignUp(this);
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        btnSignUp = findViewById(R.id.btnSignUp);
        tvLoginLink = findViewById(R.id.tvLoginLink);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isValidInput()) {
                    Toast.makeText(SignUpPage.this, "123", Toast.LENGTH_SHORT).show();
                    // If all checks pass, proceed with sign-up logic

                    moduleSignUp.addUser(editTextFirstName.getText().toString(),editTextLastName.getText().toString()
                            , editTextPassword.getText().toString()
                            ,editTextPhoneNumber.getText().toString(),0);

                    moduleSignUp.saveAtSharedPreferences(editTextPhoneNumber.getText().toString());
                    Intent intent = new Intent(SignUpPage.this, HomePage.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(SignUpPage.this, "something wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });


        tvLoginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Add logic to navigate to the login activity
                Intent intent = new Intent(SignUpPage.this, LogInPage.class);
                startActivity(intent);
            }
        });


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

        if (TextUtils.isEmpty(editTextConfirmPassword.getText().toString())) {
            editTextConfirmPassword.setError("Please confirm your password");
            isValid = false;
        }

        if (!editTextPassword.getText().toString().equals(editTextConfirmPassword.getText().toString())) {
            editTextConfirmPassword.setError("Passwords do not match");
            isValid = false;
        }

        if (TextUtils.isEmpty(editTextPhoneNumber.getText().toString())) {
            editTextPhoneNumber.setError("Please enter your phone number");
            isValid = false;
        }

        return isValid;
    }

    private void performSignUp() {
        // Implement your sign-up logic here
        // For simplicity, you can print the values for now
//        System.out.println("First Name: " + editTextFirstName.getText().toString());
//        System.out.println("Last Name: " + editTextLastName.getText().toString());
//        System.out.println("Password: " + editTextPassword.getText().toString());
//        System.out.println("Confirm Password: " + editTextConfirmPassword.getText().toString());
//        System.out.println("ID: " + editTextID.getText().toString());
//        System.out.println("Phone Number: " + editTextPhoneNumber.getText().toString());

    }


}