package com.mybigskystrong.bigskystrong;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class AddWorkoutDialogFragment extends DialogFragment {

    Button add;
    Button cancel;
    EditText workoutNameEditText;
    String workoutName;
    String currentWorkoutSet;
    String bodyPart;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private final static String TRAINING_PREFERENCES = "Training Preferences";
    ArrayList<String> workoutArrayList;
    String selectedWorkoutSet;
    Intent returnIntent;
    String arrayListString;
    String newWorkoutNumberString;
    ArrayList<String> newWorkoutArrayList;
    String newWorkoutArrayListString;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_workout_dialog_fragment,container,false);
        add = (Button) view.findViewById(R.id.addButton);
        cancel = (Button) view.findViewById(R.id.cancelButton);
        workoutNameEditText = (EditText) view.findViewById(R.id.addWorkoutEditText);
        sharedPreferences = getActivity().getApplicationContext().getSharedPreferences(TRAINING_PREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        bodyPart = getArguments().getString("bodyPart");
        currentWorkoutSet = bodyPart + "WorkoutSet";

        add.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                workoutName = workoutNameEditText.getText().toString();
                try{
                    workoutArrayList =  (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString(currentWorkoutSet,null));
                }catch(IOException e){
                    Log.e("EEDF",e.toString());
                }
                workoutArrayList.add(workoutName);
                try{
                    arrayListString = ObjectSerializer.serialize((workoutArrayList));
                }catch(IOException e){
                    Log.e("EEDF", e.toString());
                }
                editor.putString(currentWorkoutSet, arrayListString);

                Map<String,?> keys = sharedPreferences.getAll();
                int counter = 0;
                for(Map.Entry<String,?> entry : keys.entrySet()){
                    if (entry.getKey() == null){
                    }else if (entry.getKey().contains(bodyPart + "Workout")){
                        counter++;
                    }
                }
                newWorkoutNumberString = String.valueOf(counter);
                newWorkoutArrayList = new ArrayList<>();
                newWorkoutArrayList.add("Exercise 1");
                try{
                    newWorkoutArrayListString = ObjectSerializer.serialize((newWorkoutArrayList));
                }catch(IOException e){
                    Log.e("EEDF", e.toString());
                }
                String s = bodyPart+"Workout"+newWorkoutNumberString;
                Log.e("AWDF","new workout key: " + s + " and contents: " + newWorkoutArrayList);
                editor.putString(s,newWorkoutArrayListString).apply();


                try{
                    newWorkoutArrayList =  (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString(s,null));
                }catch(IOException e){
                    Log.e("EEDF",e.toString());
                }
                Log.e("AWDF","The added array list that was serialized, added, then deserialized is: " + newWorkoutArrayList);
                dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }


    public void onDismiss(DialogInterface dialog) {
        returnIntent = new Intent(getActivity().getBaseContext(),getActivity().getClass());
        getActivity().startActivity(returnIntent);

    }
}
