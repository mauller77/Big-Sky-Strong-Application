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


        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yy");
        String formattedDate = df.format(c.getTime());

        trainingSettings = getApplicationContext().getSharedPreferences(TRAINING_PREFERENCES, MODE_PRIVATE);
        trainingSettingsEditor = trainingSettings.edit();

        calorieTrackerSharedPreferences = getApplicationContext().getSharedPreferences(CALORIE_TRACKER_SHARED_PREFERENCES,MODE_PRIVATE);

        if (trainingSettings.contains("ArmWorkoutSet")) {
            Log.e("MAA","shared preferences already contains presets");
        }else{
            Log.e(this.toString(),"Date of Download: " + formattedDate);

//--------------------------------------------ARM WORKOUTS-------------------------------------------------------//

        //--------------------------------bss arm workout-----------------------------------------------------
            ArrayList<String> armWorkoutList = new ArrayList<>();
            armWorkoutList.add("BSS Arm Workout");

            ArrayList<String> armWorkout1 = new ArrayList<>();
            armWorkout1.add(getString(R.string.P1A1));
            armWorkout1.add(getString(R.string.P1A2));
            armWorkout1.add(getString(R.string.P1A3));
            armWorkout1.add(getString(R.string.P1A4));
            armWorkout1.add(getString(R.string.P1A5));
//------------------------------------------------
        //----------------------------------gerardo gabriel arm workout----------------------------------------------

            armWorkoutList.add("Gerardo Gabrial Arms");

            ArrayList<String> armWorkout2 = new ArrayList<>();
            armWorkout2.add(getString(R.string.P2A1));
            armWorkout2.add(getString(R.string.P2A2));
            armWorkout2.add(getString(R.string.P2A3));
            armWorkout2.add(getString(R.string.P2A4));
            armWorkout2.add(getString(R.string.P2A5));
            armWorkout2.add(getString(R.string.P2A6));
            armWorkout2.add(getString(R.string.P2A7));
            armWorkout2.add(getString(R.string.P2A8));

        //-----------------------------------SUPERSETS WITH SETH FORCE-------------------------------------------------

            armWorkoutList.add("Supersets with Seth Force");

            ArrayList<String> armWorkout3 = new ArrayList<>();
            armWorkout3.add("Superset: \n Cable Bicep Curls \n Rope Tricep Extensions");
            armWorkout3.add("Superset: \n EZ Bar Bicep Curls \n Single Arm DB Bicep Curls");
            armWorkout3.add("Superset: \n Close Grip BB Benge Press \n Rope Trice Extensions (no lock)");
            armWorkout3.add("Superset: \n Hammer Curls \n Close Grip Preacher Curls");
            armWorkout3.add("Superset: \n EZ Bar Skullcrushers to Close Grip Bench Press \n DB (or Cable) Kickpbacks");
            armWorkout3.add("Triset: \n DB Tricep Overhead Extensions \n Bicep Cable Curls \n Tricep Pushdown");


        //---------------------------------Lex Fitness- Arm Workout---------------------------------------------------
            armWorkoutList.add("Lex Fitness - ARM DAY");
            ArrayList<String> armWorkout4 = new ArrayList<>();
            armWorkout4.add("DB Bicep Curl");
            armWorkout4.add("Rope Hammer Curl");
            armWorkout4.add("Cable Reverse Grip Curls");
            armWorkout4.add("Zottman Curls");
            armWorkout4.add("Cable Pushdown with V-Bar");
            armWorkout4.add("Rope Tricep Extensions");
            armWorkout4.add("DB Kickback");
            armWorkout4.add("Superset: \n DB Skull Crusher \n DB Trice Press");


//----------------------------------------BACK WORKOUTS--------------------------------------------------//

        //--------------------------------BSS Back Workout------------------------------------------
            ArrayList<String> backWoroutsArrayList = new ArrayList<>();
            backWoroutsArrayList.add("BSS Back Workout");


            ArrayList<String> backWorkout1 = new ArrayList<>();
            backWorkout1.add(getString(R.string.P1B1));
            backWorkout1.add(getString(R.string.P1B2));
            backWorkout1.add(getString(R.string.P1B3));
            backWorkout1.add(getString(R.string.P1B4));


        //--------------------------------Physiques of Greatness Back Workout----------------------------

            backWoroutsArrayList.add("Physiques of Greatness Back Workout");

            ArrayList<String> backWorkout2 = new ArrayList<>();
            backWorkout2.add(getString(R.string.P2B1));
            backWorkout2.add(getString(R.string.P2B2));
            backWorkout2.add(getString(R.string.P2B3));
            backWorkout2.add(getString(R.string.P2B4));
            backWorkout2.add(getString(R.string.P2B5));
            backWorkout2.add(getString(R.string.P2B6));
            backWorkout2.add(getString(R.string.P2B7));
            backWorkout2.add(getString(R.string.P2B8));
            backWorkout2.add(getString(R.string.P2B9));


        //-----------------------------Steve Cook Swole Series: Episode 4----------------------------------

            backWoroutsArrayList.add("Steve Cook Swole Series: Episode 4");

            ArrayList<String> backWorkout3 = new ArrayList<>();
            backWorkout3.add("et Structure for Whole Workout: 12, 10, 8, 6, Drop");
            backWorkout3.add("Body Weight Pullups");
            backWorkout3.add("BB Bentover Row");
            backWorkout3.add("Lat Pulldown");
            backWorkout3.add("Single Arm DB Row");
            backWorkout3.add("Cable Delt Fly");
            backWorkout3.add("DB Pullover");
            backWorkout3.add("T-Bar Row");
            backWorkout3.add("E-Z Bar Bicep Curl");
            backWorkout3.add("Superset: \n Incline DB Curl \n Reverse Grip Spider Curl");
            backWorkout3.add("Superset: \n Alternating DB Curl \n Standing Superman Curl");


//------------------------CHEST WORKOUTS-------------------------------------------------------------------

            //----------------------------------------BSS CHEST WORKOUT--------------------------------------------------

            ArrayList<String> chestWorkoutsArrayList = new ArrayList<>();
            chestWorkoutsArrayList.add("BSS Chest Workout");

            ArrayList<String> chestWorkout1 = new ArrayList<>();
            chestWorkout1.add(getString(R.string.P1C1));
            chestWorkout1.add(getString(R.string.P1C2));
            chestWorkout1.add(getString(R.string.P1C3));
            chestWorkout1.add(getString(R.string.P1C4));
            chestWorkout1.add(getString(R.string.P1C5));

            //---------------------CRAIG CAPURSO CHEST WORKOUT--------------------------------------------------------

            chestWorkoutsArrayList.add("Craig Capurso Chest Workout");

            ArrayList<String> chestWorkout2 = new ArrayList<>();
            chestWorkout2.add(getString(R.string.P2C1));
            chestWorkout2.add(getString(R.string.P2C2));
            chestWorkout2.add(getString(R.string.P2C3));
            chestWorkout2.add(getString(R.string.P2C4));
            chestWorkout2.add(getString(R.string.P2C5));
            chestWorkout2.add(getString(R.string.P2C6));
            chestWorkout2.add(getString(R.string.P2C7));
            chestWorkout2.add(getString(R.string.P2C8));

        //------------------------------------SSETH FEROCE CHEST VOLUME WORKOUT--------------------------------
            chestWorkoutsArrayList.add("Seth Feroce: Chest Volume Workout");

            ArrayList<String> chestWorkout3 = new ArrayList<>();
            chestWorkout3.add("Incline DB Press");
            chestWorkout3.add("Superset: \n Incline DB Fly \n Close Grip Pushups");
            chestWorkout3.add("Superset: \n Chest Dips \n Pec Deck");
            chestWorkout3.add("Superset: \n Cable Crossover \n Cable Chest Pullover");
            chestWorkout3.add("Smith Machine Ladder Pushups");

        //--------------------------------------BRADLEY MARTIN: CHEST WORKOUT------------------------------------
            chestWorkoutsArrayList.add("Bradley Martin: Chest Workout");
            ArrayList<String> chestWorkout4 = new ArrayList<>();
            chestWorkout4.add("BB Military Press");
            chestWorkout4.add("BB Bench Press");
            chestWorkout4.add("Machine Chest Press w/ straight bar close grip");
            chestWorkout4.add("DB Incline Bench");
            chestWorkout4.add("Hammer Strength Bench Press");
            chestWorkout4.add("Cable Rope Tricep Extension");

        //------------------------------------RAW LEAN BULK-CHEST MASS WORKOUT---------------------------------------
            chestWorkoutsArrayList.add("RAW LEAN BULK-CHEST MASS WORKOUT");
            ArrayList<String> chestWorkout5 = new ArrayList<>();
            chestWorkout5.add("Incline Barbell Press \n " +
                    "5 X 15-15-12-12-10 \n" +
                    "Drop Set \n" +
                    "75 - 90 Seconds Rest");
            chestWorkout5.add("Superset: \n" +
                    "Incline DB Press 6 X 15-15-12-12-10 \n" +
                    "Incline Plate Squeeze Press 6 X 10-10-10-10-10-AMRAP \n" +
                    "75 - 90 Seconds Rest");
            chestWorkout5.add("Hammer Strength Chest Press \n" +
                    "4 X 15-12-12-10-Drop \n " +
                    "60 - 75 Seconds Rest");
            chestWorkout5.add("High Cable Flys \n" +
                    "4 X 5-5-5 \n" +
                    "5 Slow Negatives \n " +
                    "5 Normal Tempo \n" +
                    "5 Slow Negatives \n" +
                    "45 Seconds Rest Max");

//------------------------------------------------------SHOULDER WORKOUTS-------------------------------------------------------

            ArrayList<String> shoulderWorkoutsArrayList = new ArrayList<>();

        //-----------------------------BSS SHOULDER WORKOUT--------------------------------------

            shoulderWorkoutsArrayList.add("BSS Shoulder Workout");

            shoulderWorkout1 = new ArrayList<>();
            shoulderWorkout1.add(getString(R.string.P1S1));
            shoulderWorkout1.add(getString(R.string.P1S2));
            shoulderWorkout1.add(getString(R.string.P1S3));
            shoulderWorkout1.add(getString(R.string.P1S4));
            shoulderWorkout1.add(getString(R.string.P1S5));

        //-------------------------JAKE ALVEREZ SHOULDER WORKOUT-------------------------------

            shoulderWorkoutsArrayList.add("Jake Alverez Shoulder Workout");

            ArrayList<String> shoulderWorkout2 = new ArrayList<>();
            shoulderWorkout2.add("Set Structure: 4 X 8-12, DOLS");
            shoulderWorkout2.add("DB Shoulder Press");
            shoulderWorkout2.add("Rear Delt Fly");
            shoulderWorkout2.add("Face Pull");
            shoulderWorkout2.add("Reverse Pec Deck");

        //-----------------------PLATEAU-BUSTING SHOULDER WORKOUT FOR MASS: ABEL ALBENOTTI----------------------

            shoulderWorkoutsArrayList.add("Plateau-Busting Shoulder Workout for Mass: Abel Albenotti");

            ArrayList<String> shoulderWorkout3 = new ArrayList<>();
            shoulderWorkout3.add("DB Shoulder Press: 5 X 6-12, DOLS");
            shoulderWorkout3.add("Smith Machine Behind the Neck Press: 5 X 8-10, DOLS");
            shoulderWorkout3.add("Superset: \n Side Lateral Raise: 4 X 12, DOLS \n DB Upright Row: 4 X 12, DOLS");
            shoulderWorkout3.add("Leaning Cable Side Raise: 4 X 12-15, DOLS");
            shoulderWorkout3.add("Superset: \n Incline DB Reverse Fly: 4 X 12, DOLS \n Face Pull: 4 X 12, DOLS");
            shoulderWorkout3.add("Smith Machine Shrug: 6 X 12, DOLS, 3 Front/ 3 Back");
            shoulderWorkout3.add("Triple Set: \n Seated Side Lateral Raise: 3 X 10 \n DB Seated Front Raise: 3 X 10 \n Bent-Over Reverse Fly: 3 X 10");

//=============================================LEG WORKOUTS=================================================

            ArrayList<String> legWorkoutsArrayList = new ArrayList<>();
            legWorkoutsArrayList.add("BSS Leg Workout");

            ArrayList<String> legWorkout1 = new ArrayList<>();
            legWorkout1.add(getString(R.string.P1L1));
            legWorkout1.add(getString(R.string.P1L2));
            legWorkout1.add(getString(R.string.P1L3));
            legWorkout1.add(getString(R.string.P1L4));

            ArrayList<ArrayList<String[]>> calorieTrackerArrayList = new ArrayList<>();
            ArrayList<String[]> calorieArrayListDay1 = new ArrayList<>();
            calorieArrayListDay1.add(new String[]{formattedDate,"0"});
            calorieTrackerArrayList.add(calorieArrayListDay1);

            try{
                armWorkout1String = ObjectSerializer.serialize(armWorkout1);
                armWorkout2String = ObjectSerializer.serialize(armWorkout2);
                armWorkout3String = ObjectSerializer.serialize(armWorkout3);
                armWorkout4String = ObjectSerializer.serialize(armWorkout4);

                backWorkout1String = ObjectSerializer.serialize(backWorkout1);
                backWorkout2String = ObjectSerializer.serialize(backWorkout2);
                backWorkout3String = ObjectSerializer.serialize(backWorkout3);

                chestWorkout1String = ObjectSerializer.serialize(chestWorkout1);
                chestWorkout2String = ObjectSerializer.serialize(chestWorkout2);
                chestWorkout3String = ObjectSerializer.serialize(chestWorkout3);
                chestWorkout4String = ObjectSerializer.serialize(chestWorkout4);
                chestWorkout5String = ObjectSerializer.serialize(chestWorkout5);

                shoulderWorkout1String = ObjectSerializer.serialize(shoulderWorkout1);
                shoulderWorkout2String = ObjectSerializer.serialize(shoulderWorkout2);
                shoulderWorkout3String = ObjectSerializer.serialize(shoulderWorkout3);

                legWorkout1String = ObjectSerializer.serialize(legWorkout1);

                armWorkoutListString = ObjectSerializer.serialize(armWorkoutList);
                backWorkoutsString = ObjectSerializer.serialize(backWoroutsArrayList);
                chestWorkoutsString = ObjectSerializer.serialize(chestWorkoutsArrayList);
                shoulderWorkoutsString = ObjectSerializer.serialize(shoulderWorkoutsArrayList);
                legWorkoutsString = ObjectSerializer.serialize(legWorkoutsArrayList);

                calorieTrackerArrayListString = ObjectSerializer.serialize(calorieTrackerArrayList);
                Log.e(this.toString(),"Calorie Tracker Array List being serialized: " + calorieTrackerArrayList.toString());

            }catch(IOException e){
                Log.e("MAA",e.toString());
            }

            trainingSettingsEditor.putString("ArmWorkoutSet", armWorkoutListString).apply();
            trainingSettingsEditor.putString("BackWorkoutSet", backWorkoutsString).apply();
            trainingSettingsEditor.putString("ChestWorkoutSet", chestWorkoutsString).apply();
            trainingSettingsEditor.putString("ShoulderWorkoutSet", shoulderWorkoutsString).apply();
            trainingSettingsEditor.putString("LegWorkoutSet", legWorkoutsString).apply();

            trainingSettingsEditor.putString("ArmWorkout1", armWorkout1String).apply();
            trainingSettingsEditor.putString("ArmWorkout2", armWorkout2String).apply();
            trainingSettingsEditor.putString("ArmWorkout3", armWorkout3String).apply();
            trainingSettingsEditor.putString("ArmWorkout4", armWorkout4String).apply();

            trainingSettingsEditor.putString("BackWorkout1", backWorkout1String).apply();
            trainingSettingsEditor.putString("BackWorkout2", backWorkout2String).apply();
            trainingSettingsEditor.putString("BackWorkout3", backWorkout3String).apply();

            trainingSettingsEditor.putString("ChestWorkout1", chestWorkout1String).apply();
            trainingSettingsEditor.putString("ChestWorkout2",chestWorkout2String).apply();
            trainingSettingsEditor.putString("ChestWorkout3", chestWorkout3String).apply();
            trainingSettingsEditor.putString("ChestWorkout4", chestWorkout4String).apply();
            trainingSettingsEditor.putString("ChestWorkout5", chestWorkout5String).apply();

            trainingSettingsEditor.putString("ShoulderWorkout1", shoulderWorkout1String).apply();
            trainingSettingsEditor.putString("ShoulderWorkout2", shoulderWorkout2String).apply();
            trainingSettingsEditor.putString("ShoulderWorkout3", shoulderWorkout3String).apply();

            trainingSettingsEditor.putString("LegWorkout1", legWorkout1String).apply();

            calorieTrackerSharedPreferences.edit().putString("calorieTrackerArrayList",calorieTrackerArrayListString).apply();
       }



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
