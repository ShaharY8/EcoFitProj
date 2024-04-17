package com.example.ecofit.Approval;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecofit.DB.MyFireBaseHelper;
import com.example.ecofit.R;
import com.example.ecofit.UI.Home.HomePage;
import com.example.ecofit.UI.Home.ModuleHome;
import com.example.ecofit.UI.Main.MainActivity;
import com.example.ecofit.UI.Shop.Shop;
import com.example.ecofit.UI.UpdateUser.UpdateUserInfo;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
public class ApprovalPage extends AppCompatActivity implements View.OnClickListener , NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageView menu;
    private ModuleApproval moduleApproval;
    private ScrollView scrollView;
    private RelativeLayout mainRelativeLayout;
    private String whichTask;
    private TextView textView;
    private int indexForDel;
    private  LinkedList<String> UsersPhone;
    private LinkedList<String> TaskNameFromFb;
    private TextView tvCoinNumber,tvNameOfUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval_page);

        tvCoinNumber = findViewById(R.id.coinNumber);
        tvNameOfUser = findViewById(R.id.nameOfUser);

        indexForDel = 1;
        scrollView = findViewById(R.id.UserList);

        mainRelativeLayout = findViewById(R.id.mainRelativeLayout);

        mainRelativeLayout.removeView(scrollView);

        moduleApproval = new ModuleApproval(this);

        textView = findViewById(R.id.headText);
        AddTasks();



        drawerLayout = findViewById(R.id.drawerlayout);


        menu = findViewById(R.id.hamburgerIcon);
        menu.setOnClickListener(this);


        navigationView = findViewById(R.id.nav_drawer);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        ChangeName();
        changeNumberOfCoins();

    }

    public void ChangeName(){

        String name = moduleApproval.GetNameByPhone();
        if(name != null){
            tvNameOfUser.setText(name + "");
        }
        else {
            tvNameOfUser.setText("Error");
        }
    }
    public void changeNumberOfCoins(){
        moduleApproval.GetNumberOfCoinsByPhone(moduleApproval.getPhoneNumber(), new MyFireBaseHelper.gotCoin() {
            @Override
            public void onGotCoin(int coin) {
                tvCoinNumber.setText("your coins \n number is: " + coin);
            }
        });
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
        if (menu == view)
        {
            drawerLayout.openDrawer(GravityCompat.START);

        }
        for (int i = 0; i < btnDelete.size(); i++) {
            if(btnDelete.get(i) == view){
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
                if(i == btnUpdateCoin.size()-indexForDel){
                    btnDelete.remove(i);
                    btnUpdateCoin.remove(i);
                    Toast.makeText(this, "" + UsersPhone.get(i), Toast.LENGTH_SHORT).show();
                    moduleApproval.UpdateDataFB(UsersPhone.get(i), whichTask, i, -1, new MyFireBaseHelper.whenDone() {
                        @Override
                        public void whenDoneToUpdate() {

                        }
                    });
                    UsersPhone.remove(i);
                    AddRow("","-1",i);
                    indexForDel++;

                }
                else{
                    Toast.makeText(this, "תתחיל מהבנאדם האחרון", Toast.LENGTH_SHORT).show();
                }

            }

        }

        for (int i = 0; i < TaskNameFromFb.size(); i++) {
            if(findViewById(customIdMap.get(TaskNameFromFb.get(i).toString())) == view){
                whichTask = TaskNameFromFb.get(i).toString();
                textView.setText("הרשימה שבחרת היא "+ whichTask);
                TakeListOfUsers(whichTask);
                mainRelativeLayout.addView(scrollView);
                removeAllTask();
                if(UsersPhone == null){
                    Toast.makeText(this, "אין משתמשים שנרשמו", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    private Map<String, Integer> customIdMap = new HashMap<>();

    public void removeAllTask() {
        for (int i = 0; i < TaskNameFromFb.size(); i++) {
            Integer buttonId = customIdMap.get(TaskNameFromFb.get(i).toString());
            if (buttonId != null) {
                View buttonToRemove = mainRelativeLayout.findViewById(buttonId);
                if (buttonToRemove != null) {
                    mainRelativeLayout.removeView(buttonToRemove);
                } else {
                    // Handle case when view is not found
                    Log.e("Error", "View with ID " + buttonId + " not found");
                }
            }
        }
    }
    public void AddTasks(){
        moduleApproval.GetAllTasks(new MyFireBaseHelper.getTasks() {
            @Override
            public void onGetTasks(LinkedList<String> TaskName, LinkedList<String> title, LinkedList<String> details) {
                TaskNameFromFb = TaskName;

                for (int i = 0; i <title.size() ; i++) {
                    Button button = new Button(ApprovalPage.this);
                    button.setLayoutParams(new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.MATCH_PARENT,
                            getResources().getDimensionPixelSize(R.dimen.button_height))); // Set height dynamically
                    //button.setId(View.generateViewId()); // Generate unique ID for the button

                    // Generate unique ID for the button based on task title
                    int buttonId = View.generateViewId();
                    button.setId(buttonId);

                    // Add task title and button ID to customIdMap
                    customIdMap.put(TaskName.get(i), buttonId);


                    // Set text for each button

                    button.setText(title.get(i).toString());


                    button.setBackgroundResource(R.drawable.fix_button); // Set background drawable

                    // Set layout rules for positioning
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) button.getLayoutParams();
                    if (i == 0) {
                        params.addRule(RelativeLayout.BELOW, R.id.headText);
                    } else {
                        params.addRule(RelativeLayout.BELOW, customIdMap.get(TaskNameFromFb.get(i-1).toString()));
                    }
                    params.topMargin = getResources().getDimensionPixelSize(R.dimen.button_margin_top); // Set top margin dynamically
                    button.setLayoutParams(params);
                    button.setOnClickListener(ApprovalPage.this);
                    // Add button to layout container
                    mainRelativeLayout.addView(button);

                }

            }
        });
    }
    public void TakeListOfUsers(String task){
        moduleApproval.ReadDocument(task, new MyFireBaseHelper.gotUser() {
            @Override
            public void onGotUser(LinkedList<String> name, LinkedList<String> phone) {
                tableLayout = findViewById(R.id.tableLayout);
                UsersPhone = phone;
                for (int i = 0; i < name.size(); i++) {

                    AddRow(name.get(i),phone.get(i),i);
                }
            }
        });
    }
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id._homePage){
            Intent intent = new Intent(ApprovalPage.this, HomePage.class);
            startActivity(intent);
        }
        else if (id == R.id._profile) {
            Intent intent = new Intent(ApprovalPage.this, UpdateUserInfo.class);
            startActivity(intent);
        } else if (id == R.id._Shop) {
            Intent intent = new Intent(ApprovalPage.this, Shop.class);
            startActivity(intent);
        }
        else if (id == R.id._logOut) {
            moduleApproval.LogOut();
            Intent intent = new Intent(ApprovalPage.this, MainActivity.class);
            startActivity(intent);
        }

        return false; // Return true to indicate that the item click has been handled
    }

}