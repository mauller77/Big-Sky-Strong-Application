package com.mybigskystrong.bigskystrong;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class ExerciseEditorDialogFragment extends android.support.v4.app.DialogFragment {

    Button submitButton;
    Button cancelButton;
    Button deleteButton;
    EditText exerciseEditText;
    String exerciseButtonText;
    int workoutNumber;
    String workoutNumberString;
    String newExerciseText;
    String selectedWorkout;
    String arrayListString;
    ArrayList<String> exerciseArrayList;
    Bundle bundle;
    Intent returnIntent;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private final static String TRAINING_PREFERENCES = "Training Preferences";
    String selectedWorkoutTitle;


    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.exercise_editor_dialog_fragment,container,false);

        exerciseButtonText = getArguments().getString("button text");
        selectedWorkout = getArguments().getString("workoutSelected");
        workoutNumberString = String.valueOf(workoutNumber);
        selectedWorkoutTitle = getArguments().getString("selectedWorkoutTitle");

        sharedPreferences = getActivity().getApplicationContext().getSharedPreferences(TRAINING_PREFERENCES, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        submitButton = (Button) view.findViewById(R.id.exerciseEditorSubmitButton);
        deleteButton = (Button) view.findViewById(R.id.deleteExerciseButton);
        cancelButton = (Button) view.findViewById(R.id.exerciseEditorCancelButton);
        exerciseEditText = (EditText) view.findViewById(R.id.exerciseEditorEditText);

        exerciseEditText.setText(exerciseButtonText);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newExerciseText = exerciseEditText.getText().toString();
                Map<String,?> keys = sharedPreferences.getAll();
                for(Map.Entry<String,?> entry : keys.entrySet()){
                    Log.e("EEDF","Entry Key is: " + entry.getKey() + " the selected workout is: " + selectedWorkout);
                    if(entry.getKey() == null){
                        keys.remove(entry);
                    }else if (entry.getKey().equals(selectedWorkout)){
                        try{
                            exerciseArrayList =  (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString(selectedWorkout,null));
                        }catch(IOException e){
                            Log.e("EEDF",e.toString());
                        }
                        for (int i = 0 ; i< exerciseArrayList.size(); i++){

                            if (exerciseArrayList.get(i).equals(exerciseButtonText)){
                                exerciseArrayList.remove(i);
                                exerciseArrayList.add(i,newExerciseText);
                            }

                        }
                    }
                }
                try{
                    arrayListString = ObjectSerializer.serialize((exerciseArrayList));
                }catch(IOException e){
                    Log.e("EEDF", e.toString());
                }
                editor.putString(selectedWorkout, arrayListString);
                editor.apply();
                dismiss();
            }




        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,?> keys = sharedPreferences.getAll();
                for(Map.Entry<String,?> entry : keys.entrySet()){
                    if(entry.getKey() == null){
                    }else if (entry.getKey().equals(selectedWorkout)){
                        try{
                            exerciseArrayList =  (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString(selectedWorkout,null));
                        }catch(IOException e){
                            Log.e("EEDF",e.toString());
                        }
                        for (int i = 0 ; i< exerciseArrayList.size(); i++){
                            if (exerciseArrayList.get(i) == null){
                                exerciseArrayList.remove(i);
                                exerciseArrayList.add(i,"");
                            }
                            if (exerciseArrayList.get(i).equals(exerciseButtonText)){
                                exerciseArrayList.remove(i);
                            }

                        }
                    }
                }
                try{
                    arrayListString = ObjectSerializer.serialize((exerciseArrayList));
                }catch(IOException e){
                    Log.e("EEDF", e.toString());
                }
                editor.putString(selectedWorkout, arrayListString);
                editor.apply();
                dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        returnIntent = new Intent(getContext(),getActivity().getClass());
        Bundle bundle = new Bundle();
        bundle.putString("selectedWorkout",selectedWorkout);
        bundle.putString("selectedWorkoutTitle",selectedWorkoutTitle);
        returnIntent.putExtras(bundle);
        startActivity(returnIntent);
    }
}
