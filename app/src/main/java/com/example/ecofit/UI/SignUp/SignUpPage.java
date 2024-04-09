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
import com.example.ecofit.DB.MyFireBaseHelper;
import com.example.ecofit.R;
import com.example.ecofit.UI.Home.HomePage;
import com.example.ecofit.UI.Login.LogInPage;
import com.google.common.base.MoreObjects;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class SignUpPage extends AppCompatActivity {

    private EditText editTextFirstName , editTextLastName , editTextPassword , editTextConfirmPassword,editTextPhoneNumber   ;
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
//        btnSignUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (isValidInput()) {
//
//
//                    moduleSignUp.addUser(editTextFirstName.getText().toString().trim(),editTextLastName.getText().toString().trim()
//                            , editTextPassword.getText().toString().trim()
//                            ,editTextPhoneNumber.getText().toString().trim(),0);
//
//                    moduleSignUp.saveAtSharedPreferences(editTextPhoneNumber.getText().toString().trim());
//
//                    moduleSignUp.AddDocument(editTextFirstName.getText().toString().trim(),editTextLastName.getText().toString().trim()
//                            , editTextPassword.getText().toString().trim()
//                            ,editTextPhoneNumber.getText().toString().trim(),0);
//
//                    Intent intent = new Intent(SignUpPage.this, HomePage.class);
//                    startActivity(intent);
//                }
//                else{
//                   Toast.makeText(SignUpPage.this, "something wrong", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateInputAndCheckUserExistence(new ValidationCallback() {
                    @Override
                    public void onValidationResult(boolean isValid) {
                        // Handle the final result of validation here
                        if (isValid) {
                            moduleSignUp.addUser(editTextFirstName.getText().toString().trim(),editTextLastName.getText().toString().trim()
                            , editTextPassword.getText().toString().trim()
                            ,editTextPhoneNumber.getText().toString().trim(),0);

                            moduleSignUp.saveAtSharedPreferences(editTextPhoneNumber.getText().toString().trim());

                            moduleSignUp.AddDocument(editTextFirstName.getText().toString().trim(),editTextLastName.getText().toString().trim()
                            , editTextPassword.getText().toString().trim()
                            ,editTextPhoneNumber.getText().toString().trim(),0);

                            Intent intent = new Intent(SignUpPage.this, HomePage.class);
                            startActivity(intent);
                        } else {
                           Toast.makeText(SignUpPage.this, "something wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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

    boolean isValid = true;

    interface ValidationCallback {
        void onValidationResult(boolean isValid);
    }

    private void validateInputAndCheckUserExistence(ValidationCallback callback) {
        String name = editTextFirstName.getText().toString().trim();
        String lname = editTextLastName.getText().toString().trim();
        String pass = editTextPassword.getText().toString().trim();
        String confirmPass = editTextConfirmPassword.getText().toString().trim();
        String phone = editTextPhoneNumber.getText().toString().trim();

        // Reset isValid to true before performing checks
        isValid = true;
        // Check if user exists
        moduleSignUp.checkIfUserExists(phone, new MyFireBaseHelper.UserExistenceCallback() {
            @Override
            public void onUserExistenceChecked(boolean userExists) {
                if (userExists) {
                    editTextPhoneNumber.setError("מספר טלפון זה כבר קיים במערכת");
                    isValid = false; // Set isValid to false if user exists
                }

                // Perform other validations
                if (name.isEmpty()) {
                    editTextFirstName.setError("תמלא שם פרטי");
                    isValid = false;
                }
                if (name.length() > 10) {
                    editTextFirstName.setError("שם פרטי ארוך מידי");
                    isValid = false;
                }
                if (lname.isEmpty()) {
                    editTextLastName.setError("תמלא שם משפחה");
                    isValid = false;
                }
                if (lname.length() > 10) {
                    editTextLastName.setError("שם משפחה ארוך מידי");
                    isValid = false;
                }
                if (pass.length() < 3 || pass.length() > 20) {
                    editTextPassword.setError("סיסמא צריכה להיות בין 3-20 תווים");
                    isValid = false;
                }
                if (!pass.equals(confirmPass)) {
                    editTextConfirmPassword.setError("הסיסמאות לא תואמות");
                    isValid = false;
                }
                if (phone.length() == 0) {
                    editTextPhoneNumber.setError("תכניס מספר טלפון");
                    isValid = false;
                }

                // Pass the validation result to the callback
                callback.onValidationResult(isValid);
            }
        });
    }

    // Call this method to initiate the validation process
    private void initiateValidation() {
        validateInputAndCheckUserExistence(new ValidationCallback() {
            @Override
            public void onValidationResult(boolean isValid) {
                // Handle the final result of validation here
                if (isValid) {
                    // Proceed with your application logic here
                } else {
                    // User input is not valid, take appropriate action
                }
            }
        });
    }

//    private boolean isValidInput() {
//        // Add your validation checks here
//
//
//        String name = editTextFirstName.getText().toString().trim();
//        String lname = editTextLastName.getText().toString().trim();
//        String pass = editTextPassword.getText().toString().trim();
//        String confirmPass = editTextConfirmPassword.getText().toString().trim();
//        String phone = editTextPhoneNumber.getText().toString().trim();
//
//
//        moduleSignUp.checkIfUserExists(phone, new MyFireBaseHelper.UserExistenceCallback() {
//            @Override
//            public void onUserExistenceChecked(boolean userExists) {
//                if (userExists) {editTextPhoneNumber.setError("מספר טלפון זה כבר קיים במערכת");
//                    isValid = false;
//                }
//            }
//        });
//
//
//        if (name.isEmpty()) {
//            editTextFirstName.setError("תמלא שם פרטי");
//            isValid = false;
//        }
//        if (name.length() >10) {
//            editTextFirstName.setError("שם פרטי ארוך מידי");
//            isValid = false;
//        }
//
//        if (lname.isEmpty()) {
//            editTextLastName.setError("תמלא שם משפחה");
//            isValid = false;
//        }
//        if (lname.length() >10) {
//            editTextLastName.setError("שם משפחה ארוך מידי");
//            isValid = false;
//        }
//        if (pass.length() < 3 || pass.length() > 20) {
//            editTextPassword.setError("סיסמא צריכה להיות בין 3-20 תווים");
//            isValid = false;
//        }
//
//        if (pass.equals(confirmPass) == false) {
//            editTextConfirmPassword.setError("הסיסמאות לא תואמות");
//            isValid = false;
//        }
//
//
//        if (phone.length() == 0) {
//            editTextPhoneNumber.setError("תכניס מספר טלפון");
//            isValid = false;
//        }
//
//
//        return isValid;
//    }

}