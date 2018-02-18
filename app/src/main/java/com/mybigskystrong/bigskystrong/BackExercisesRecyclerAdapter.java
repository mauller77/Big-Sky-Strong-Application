package com.mybigskystrong.bigskystrong;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by josh on 7/18/17.
 */

public class BackExercisesRecyclerAdapter extends RecyclerView.Adapter<BackExercisesRecyclerAdapter.ViewHolder>{
    public Button exerciseButton;
    SharedPreferences sharedPreferences;
    FragmentManager fragmentManager;
    ArrayList<String> backExercisesArrayList;
    ExerciseEditorDialogFragment editorDialogFragment;
    String workoutSelected;
    String selectedWorkoutTitle;


    public BackExercisesRecyclerAdapter(String sW, String sWT, FragmentManager fm, SharedPreferences sharedPreferences1){
        sharedPreferences = sharedPreferences1;
        fragmentManager = fm;
        workoutSelected = sW;
        selectedWorkoutTitle = sWT;

        try{
            backExercisesArrayList =  (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString(workoutSelected,null));
        }catch(IOException e){
            Log.e("EEDF",e.toString());
        }
    }

    @Override
    public BackExercisesRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View exerciseView = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercises_view_for_recycler_view,parent,false);
        return new BackExercisesRecyclerAdapter.ViewHolder(exerciseView);
    }

    @Override
    public void onBindViewHolder(BackExercisesRecyclerAdapter.ViewHolder holder, int position) {

        String exercise = backExercisesArrayList.get(position);
        exerciseButton.setText(exercise);
        exerciseButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                exerciseButtonSelected(b);
            }
        });
    }

    public void exerciseButtonSelected(Button button){
        editorDialogFragment = new ExerciseEditorDialogFragment();
        String buttonText = button.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString("button text",buttonText);
        bundle.putString("workoutSelected",workoutSelected);
        bundle.putString("selectedWorkoutTitle",selectedWorkoutTitle);
        editorDialogFragment.setArguments(bundle);
        editorDialogFragment.selectedWorkout = workoutSelected;
        editorDialogFragment.show(fragmentManager,"editorDialogFragment");
    }

    @Override
    public int getItemCount() {
        int size;
        if (backExercisesArrayList == null){
            size = 0;
        }else{
            size = backExercisesArrayList.size();
        }
        return size;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View v) {
            super(v);
            exerciseButton =(Button) v.findViewById(R.id.exerciseButton);
        }
    }
}