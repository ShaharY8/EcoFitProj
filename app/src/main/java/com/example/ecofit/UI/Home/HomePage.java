package com.example.ecofit.UI.Home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ecofit.R;

public class HomePage extends AppCompatActivity implements View.OnClickListener {
    private ModuleHome moduleHome;
    private Button btn1, btn2, btn3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(this);
        btn2 = findViewById(R.id.btn2);
        btn2.setOnClickListener(this);
        btn3 = findViewById(R.id.btn3);
        btn3.setOnClickListener(this);

        moduleHome = new ModuleHome(this);

    }

    @Override
    public void onClick(View view) {
        if(btn1 == view){
            moduleHome.Button1();
        }
    }
}