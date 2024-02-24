package com.example.ecofit.UI.UpdateUser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecofit.R;
import com.example.ecofit.UI.Home.HomePage;
import com.example.ecofit.UI.Login.LogInPage;
import com.example.ecofit.UI.SignUp.ModuleSignUp;

public class UpdateUserInfo extends AppCompatActivity {

    private EditText editTextFirstName , editTextLastName , editTextPassword,editTextPhoneNumber;
    private Button updateButton;
    private TextView tvLoginLink;
    private SharedPreferences sharedPreferences;
    private ModuleUpdateUserInfo moduleUpdateUserInfo;
    private String oldPhoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_info);


        moduleUpdateUserInfo = new ModuleUpdateUserInfo(this);

        sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
        oldPhoneNumber = sharedPreferences.getString("UserPhone",null);


        editTextFirstName = findViewById(R.id.firstNameEditText);
        editTextLastName = findViewById(R.id.lastNameEditText);
        editTextPassword = findViewById(R.id.passwordEditText);
        editTextPhoneNumber = findViewById(R.id.phoneNumberEditText);
        updateButton = findViewById(R.id.updateButton);

        addDefaultUserInfo();

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isValidInput()){
                    if(oldPhoneNumber != null){
                        moduleUpdateUserInfo.updateUser(moduleUpdateUserInfo.getIdByPhoneNumber(oldPhoneNumber),editTextFirstName.getText().toString(),editTextLastName.getText().toString()
                                , editTextPassword.getText().toString()
                                ,editTextPhoneNumber.getText().toString(),0);
                    }
                    else
                    {
                        Toast.makeText(UpdateUserInfo.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                    moduleUpdateUserInfo.saveAtSharedPreferences(editTextPhoneNumber.getText().toString());
                    Intent intent = new Intent(UpdateUserInfo.this, HomePage.class);
                    startActivity(intent);
                }
            }
        });
    }
    public void addDefaultUserInfo(){
        editTextFirstName.setText(sharedPreferences.getString("UserName",null));
        editTextLastName.setText(sharedPreferences.getString("UserLname",null));
        editTextPassword.setText(sharedPreferences.getString("UserPass",null));
        editTextPhoneNumber.setText(sharedPreferences.getString("UserPhone",null));
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
        if (TextUtils.isEmpty(editTextPhoneNumber.getText().toString())) {
            editTextPhoneNumber.setError("Please enter your phone number");
            isValid = false;
        }

        return isValid;
    }
}