package com.mybigskystrong.bigskystrong;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainAppFragment extends Fragment implements View.OnClickListener{

    private Button nutritionButton;
    private Button trainingButton;
    private Button aboutMeButton;
    private Button settingsButton;
    private Button calorieTrackerButton;
    Intent aboutMeIntent;
    Intent settingsIntent;
    Intent nutritionIntent;
    Intent trainingIntent;
    Intent calorieTrackerIntent;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.main_app_fragment,container,false);


        nutritionButton = (Button) view.findViewById(R.id.nutritionButton);

        trainingButton = (Button) view.findViewById(R.id.trainingButton);

        aboutMeButton = (Button) view.findViewById(R.id.aboutBSSButton);
        settingsButton = (Button) view.findViewById(R.id.settingButton);
        calorieTrackerButton = (Button) view.findViewById(R.id.calorieTrackerButton);

        calorieTrackerButton.setOnClickListener(this);
        nutritionButton.setOnClickListener(this);
        trainingButton.setOnClickListener(this);
        aboutMeButton.setOnClickListener(this);
        settingsButton.setOnClickListener(this);


        return view;

    }


    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id){
            /*
            case R.id.nutritionButton:

                nutritionButtonSelected();

                break;
            */
            case R.id.trainingButton:

                trainingButtonSelected();

                Log.e("maf","training button hit");

                break;
            /*
            case R.id.aboutBSSButton:

                aboutMeButtonSelected();

                break;

            case R.id.settingButton:

                settingsButtonSelected();

                break;
            case R.id.calorieTrackerButton:
                calorieTrackerButtonSelected();
                break;
            */
        }


    }

    /*
    public void calorieTrackerButtonSelected(){
        calorieTrackerIntent = new Intent(getContext(),CalorieTrackerActivity.class);
        startActivity(calorieTrackerIntent);
    }

    public void aboutMeButtonSelected(){
        aboutMeIntent = new Intent(getContext(),AboutMeActivity.class);
        startActivity(aboutMeIntent);

    }

    public void settingsButtonSelected(){
        settingsIntent = new Intent(getContext(), SettingsActivity.class);
        startActivity(settingsIntent);

    }

    public void nutritionButtonSelected(){

        nutritionIntent = new Intent(getContext(),NutritionActivity.class);
        startActivity(nutritionIntent);

    }

    */

    public void trainingButtonSelected(){

        trainingIntent = new Intent(getContext(),TrainingActivity.class);
        startActivity(trainingIntent);

    }


}
