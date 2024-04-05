package com.example.ecofit.Approval;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecofit.DB.MyFireBaseHelper;
import com.example.ecofit.R;
import com.example.ecofit.UI.Home.ModuleHome;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ApprovalPage extends AppCompatActivity implements View.OnClickListener {

    private ModuleHome moduleHome;
    private Button task1, task2, task3;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageView menu;
    private ModuleApproval moduleApproval;
    private ScrollView scrollView;
    private RelativeLayout mainRelativeLayout;
    private String whichTask;
    private TextView textView;
    private int indexForDel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval_page);

        indexForDel = 1;
        scrollView = findViewById(R.id.UserList);
        mainRelativeLayout = findViewById(R.id.mainRelativeLayout);
        mainRelativeLayout.removeView(scrollView);

        moduleApproval = new ModuleApproval(this);
        task1 = findViewById(R.id.task1);
        task1.setOnClickListener(this);
        task2 = findViewById(R.id.task2);
        task2.setOnClickListener(this);
        task3 = findViewById(R.id.task3);
        task3.setOnClickListener(this);
        textView = findViewById(R.id.headText);


    }



    private TableLayout tableLayout;
    private List<Button> btnUpdateCoin = new ArrayList<>();
    private List<Button> btnDelete = new ArrayList<>();

    public void AddRow(String name, String phone, int id) {

        TableRow existingRow = tableLayout.findViewWithTag(id);

        TextView _name = new TextView(this);
        TextView _price = new TextView(this);

        TableRow tbRow = new TableRow(this);
        tbRow.setTag(id);

        TableRow.LayoutParams params = new TableRow.LayoutParams(-2, 150);
        TableRow.LayoutParams paramsForText = new TableRow.LayoutParams(-2, 75);
        TableRow.LayoutParams paramsForButton = new TableRow.LayoutParams(-2, -2);
        paramsForText.setMargins(0,0,30,0);
        paramsForButton.setMargins(0,0,50,0);

        _price.setLayoutParams(paramsForText);
        _name.setLayoutParams(paramsForText);
        tbRow.setLayoutParams(params);

        Button btnDel = new Button(this);
        btnDel.setText("לא מאושר");
        btnDel.setOnClickListener(this);
        btnDel.setId(id);
        btnDelete.add(btnDel);
        btnDel.setLayoutParams(paramsForButton);

        Button btnUp = new Button(this);
        btnUp.setText("מאושר");
        btnUp.setId(id);
        btnUp.setOnClickListener(this);
        btnUpdateCoin.add(btnUp);
        btnUp.setLayoutParams(paramsForButton);

        _name.setText(name);
        _price.setText(phone);

        tbRow.addView(_name);
        tbRow.addView(_price);
        tbRow.addView(btnUp);
        tbRow.addView(btnDel);

        if(phone == "-1"){
            TableRow tbRowToDel = existingRow.findViewWithTag(id);
            tableLayout.removeView(tbRowToDel);
        }
        else{
            tableLayout.addView(tbRow);
        }

    }


    @Override
    public void onClick(View view) {
        if(task1 == view){
            TakeListOfUsers("CleanForest");
            whichTask = "CleanForest";
            mainRelativeLayout.removeView(task1);
            mainRelativeLayout.removeView(task2);
            mainRelativeLayout.removeView(task3);
            mainRelativeLayout.addView(scrollView);
            textView.setText("הרשימה שבחרת היא"+ whichTask);
        }
        for (int i = 0; i < btnDelete.size(); i++) {
            if(btnDelete.get(i) == view){
                Toast.makeText(this, "" + btnDelete.size(), Toast.LENGTH_SHORT).show();
                if(i == btnDelete.size()-indexForDel){
                    btnDelete.remove(i);
                    btnUpdateCoin.remove(i);
                    moduleApproval.DelFromFireStore(whichTask,i);

                    AddRow("","-1",i);
                    indexForDel++;

                }
                else{
                    Toast.makeText(this, "תתחיל מהבנאדם האחרון", Toast.LENGTH_SHORT).show();
                }

            }
        }
        for (int i = 0; i < btnUpdateCoin.size(); i++) {

            if(btnUpdateCoin.get(i) == view){
                Toast.makeText(this, "" + btnDelete.size(), Toast.LENGTH_SHORT).show();
                if(i == btnDelete.size()-indexForDel){
                    btnDelete.remove(i);
                    btnUpdateCoin.remove(i);
                    moduleApproval.DelFromFireStore(whichTask,i);
                    AddRow("","-1",i);
                    indexForDel++;

                }
                else{
                    Toast.makeText(this, "תתחיל מהבנאדם האחרון", Toast.LENGTH_SHORT).show();
                }

            }

        }
    }
    public void TakeListOfUsers(String task){
        moduleApproval.ReadDocument(task, new MyFireBaseHelper.gotUser() {
            @Override
            public void onGotUser(LinkedList<String> name, LinkedList<String> phone) {
                tableLayout = findViewById(R.id.tableLayout);
                for (int i = 0; i < name.size(); i++) {
                    AddRow(name.get(i),phone.get(i),i);
                }
            }
        });
    }

}