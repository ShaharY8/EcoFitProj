package com.example.ecofit.UI.Shop;

import static android.content.Intent.getIntent;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ecofit.DB.MyFireBaseHelper;
import com.example.ecofit.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainShopPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainShopPage extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MainShopPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainShopPage.
     */
    // TODO: Rename and change types and number of parameters
    public static MainShopPage newInstance(String param1, String param2) {
        MainShopPage fragment = new MainShopPage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private Button btnGym, btnHome_Gym, btnHome;

    private ModuleShop moduleShop;
    private ImageView img1,img2,img3;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v  = inflater.inflate(R.layout.fragment_main_shop_page, container, false);

        moduleShop = new ModuleShop(getActivity());
        btnGym = v.findViewById(R.id.gym);
        btnGym.setOnClickListener(this);
        btnHome = v.findViewById(R.id.home);
        btnHome.setOnClickListener(this);
        btnHome_Gym = v.findViewById(R.id.home_gym);
        btnHome_Gym.setOnClickListener(this);
        img1 = v.findViewById(R.id.img1);
        img2 = v.findViewById(R.id.img2);
        img3 = v.findViewById(R.id.img3);

        if(moduleShop.CheckIfPlanBought(moduleShop.getPhoneNumber(),"IsHomeAndGym")){
            img2.setImageResource(R.drawable.baseline_lock_open_24);
        }
        if(moduleShop.CheckIfPlanBought(moduleShop.getPhoneNumber(),"IsHome")){
            img1.setImageResource(R.drawable.baseline_lock_open_24);
        }
        if(moduleShop.CheckIfPlanBought(moduleShop.getPhoneNumber(),"IsGym")){
            img3.setImageResource(R.drawable.baseline_lock_open_24);
        }

        return v;
    }


    // פעולות של הכפתורים
    @Override
    public void onClick(View view) {
        if(btnHome_Gym == view){

            if(moduleShop.CheckIfPlanBought(moduleShop.getPhoneNumber(),"IsHomeAndGym")){
                Home_gymWorkoutPlanFragment homeGymWorkoutPlanFragment = new Home_gymWorkoutPlanFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.containerFragment,homeGymWorkoutPlanFragment)
                        .addToBackStack(null).commit();
            }
            else{
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("האם אתה רוצה לקנות תוכנית אימון זו?");
                builder.setMessage("תוכנית זו עולה 35 מטבעות");
                builder.setCancelable(false);

                builder.setPositiveButton("כן", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        moduleShop.GetNumberOfCoinsByPhone(moduleShop.getPhoneNumber(), new MyFireBaseHelper.gotCoin() {
                            @Override
                            public void onGotCoin(int coin) {


                                if(coin < 35){
                                    Toast.makeText(getActivity(), "אין לך מספיק מטבעות צריך לפחות 35", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    moduleShop.UpdateDataFB(moduleShop.getPhoneNumber(), "---", 0, 0, new MyFireBaseHelper.whenDone() {
                                        @Override
                                        public void whenDoneToUpdate() {
                                            moduleShop.updatePlan(moduleShop.getIdByPhoneNumber(), false,false,true);
                                            Activity activity = getActivity();
                                            activity.finish();
                                            activity.startActivity(activity.getIntent());
                                        }
                                    });
                                }


                            }
                        });

                    }
                });

                builder.setNegativeButton("לא", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
                dialog.getButton(-1).setTextColor(Color.BLUE);
                dialog.getButton(-2).setTextColor(Color.RED);
            }


        }
        if(btnHome == view){

            if(moduleShop.CheckIfPlanBought(moduleShop.getPhoneNumber(),"IsHome")){
                WorkoutPlanHomeFragment workoutPlanHomeFragment = new WorkoutPlanHomeFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.containerFragment,workoutPlanHomeFragment)
                        .addToBackStack(null).commit();
            }
            else{
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("האם אתה רוצה לקנות תוכנית אימון זו?");
                builder.setMessage("תוכנית זו עולה 35 מטבעות");
                builder.setCancelable(false);

                builder.setPositiveButton("כן", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        moduleShop.GetNumberOfCoinsByPhone(moduleShop.getPhoneNumber(), new MyFireBaseHelper.gotCoin() {
                            @Override
                            public void onGotCoin(int coin) {
                                if(coin < 35){
                                    Toast.makeText(getActivity(), "אין לך מספיק מטבעות צריך לפחות 35", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    moduleShop.UpdateDataFB(moduleShop.getPhoneNumber(), "---", 0, 0, new MyFireBaseHelper.whenDone() {
                                        @Override
                                        public void whenDoneToUpdate() {
                                            moduleShop.updatePlan(moduleShop.getIdByPhoneNumber(), false,true,false);
                                            Activity activity = getActivity();
                                            activity.finish();
                                            activity.startActivity(activity.getIntent());
                                        }
                                    });
                                }

                            }
                        });

                    }
                });

                builder.setNegativeButton("לא", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                dialog.getButton(-1).setTextColor(Color.BLUE);
                dialog.getButton(-2).setTextColor(Color.RED);
            }



        }
        if(btnGym == view){

            if(moduleShop.CheckIfPlanBought(moduleShop.getPhoneNumber(),"IsGym")){
                GymWorkoutFragment gymWorkoutFragment = new GymWorkoutFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.containerFragment,gymWorkoutFragment)
                        .addToBackStack(null).commit();
            }
            else{
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("האם אתה רוצה לקנות תוכנית אימון זו?");
                builder.setMessage("תוכנית זו עולה 35 מטבעות");
                builder.setCancelable(false);

                builder.setPositiveButton("כן", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        moduleShop.GetNumberOfCoinsByPhone(moduleShop.getPhoneNumber(), new MyFireBaseHelper.gotCoin() {
                            @Override
                            public void onGotCoin(int coin) {


                                if(coin < 35){
                                    Toast.makeText(getActivity(), "אין לך מספיק מטבעות צריך לפחות 35", Toast.LENGTH_SHORT).show();
                                }
                                else{

                                    moduleShop.UpdateDataFB(moduleShop.getPhoneNumber(), "---", 0, 0, new MyFireBaseHelper.whenDone() {
                                        @Override
                                        public void whenDoneToUpdate() {
                                            moduleShop.updatePlan(moduleShop.getIdByPhoneNumber(), true,false,false);
                                            Activity activity = getActivity();
                                            activity.finish();
                                            activity.startActivity(activity.getIntent());

                                        }
                                    });
                                }


                            }
                        });

                    }
                });

                builder.setNegativeButton("לא", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                dialog.getButton(-1).setTextColor(Color.BLUE);
                dialog.getButton(-2).setTextColor(Color.RED);
            }



        }
    }


}