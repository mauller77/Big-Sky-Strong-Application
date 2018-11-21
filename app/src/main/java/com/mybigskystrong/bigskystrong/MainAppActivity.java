package com.mybigskystrong.bigskystrong;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import java.io.IOException;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;


public class MainAppActivity extends AppCompatActivity{

    MainAppFragment mainAppFragment;
    private final static String TRAINING_PREFERENCES = "Training Preferences";
    SharedPreferences trainingSettings;
    SharedPreferences.Editor trainingSettingsEditor;
    SharedPreferences calorieTrackerSharedPreferences;
    SharedPreferences.Editor calorieTrackerSettingsEditor;

    String armWorkout1String;
    String armWorkout2String;
    String armWorkout3String;
    String armWorkout4String;

    String backWorkout1String;
    String backWorkout2String;
    String backWorkout3String;

    String chestWorkout1String;
    String chestWorkout2String;
    String chestWorkout3String;
    String chestWorkout4String;
    String chestWorkout5String;

    ArrayList<String> shoulderWorkout1;

    String shoulderWorkout1String;
    String shoulderWorkout2String;
    String shoulderWorkout3String;

    String legWorkout1String;
    String backWorkoutsString;
    String chestWorkoutsString;
    String shoulderWorkoutsString;
    String legWorkoutsString;
    String armWorkoutListString;
    String calorieTrackerArrayListString;
    int allTrackedDaysSize;
    final String CALORIE_TRACKER_SHARED_PREFERENCES = "calorieTrackerSharedPreferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_app_activity);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        if(savedInstanceState==null){
            mainAppFragment = new MainAppFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.mainAppFragmentContainer,mainAppFragment,"MainAppFragment");
            transaction.addToBackStack(null);

            transaction.commit();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        MainAppFragment myFragment = (MainAppFragment) getSupportFragmentManager().findFragmentByTag("MainAppFragment");
        if (myFragment != null && myFragment.isVisible()) {
            finishAffinity();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }




}
