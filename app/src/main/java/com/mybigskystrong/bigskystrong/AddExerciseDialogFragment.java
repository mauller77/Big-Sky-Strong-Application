package com.mybigskystrong.bigskystrong;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by josh on 7/12/17.
 */

public class AddExerciseDialogFragment extends DialogFragment {
    Button submitButton;
    Button cancelButton;
    EditText exerciseEditText;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_exercise_dialog_fragment,container,false);
        selectedWorkout = getArguments().getString("workoutSelected");
        selectedWorkoutTitle = getArguments().getString("selectedWorkoutTitle");
        sharedPreferences = getActivity().getApplicationContext().getSharedPreferences(TRAINING_PREFERENCES, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        submitButton = (Button) view.findViewById(R.id.addExerciseSubmitButton);
        cancelButton = (Button) view.findViewById(R.id.addExerciseCancelButton);
        exerciseEditText = (EditText) view.findViewById(R.id.addExerciseEditText);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newExerciseText = exerciseEditText.getText().toString();
                Map<String,?> keys = sharedPreferences.getAll();
                for(Map.Entry<String,?> entry : keys.entrySet()){
                    if (entry.getKey() == null){
                    }else if (entry.getKey().equals(selectedWorkout)){
                        try{
                            exerciseArrayList =  (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString(selectedWorkout,null));
                        }catch(IOException e){
                            Log.e("EEDF",e.toString());
                        }
                        Log.e("AEDF","The full selected workout that was searched for is: " + selectedWorkout);
                        Log.e("AEDF","The array list that you are adding an exercise to is: " + exerciseArrayList);
                        exerciseArrayList.add(newExerciseText);
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
        returnIntent = new Intent(getActivity(), getActivity().getClass());
        Bundle bundle = new Bundle();
        bundle.putString("selectedWorkout",selectedWorkout);
        bundle.putString("selectedWorkoutTitle",selectedWorkoutTitle);
        returnIntent.putExtras(bundle);
        getActivity().startActivity(returnIntent);

    }
}
