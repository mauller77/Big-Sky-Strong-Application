package com.mybigskystrong.bigskystrong;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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


public class ArmWorkoutRecyclerAdapter extends RecyclerView.Adapter<ArmWorkoutRecyclerAdapter.ViewHolder> {
    public Button workoutButton;
    Context currentContext;
    SharedPreferences sharedPreferences;
    ArrayList<String> armWorkoutsArrayList;
    Intent armExercisesIntent;
    FragmentManager fragmentManager;
    Activity parentActivity;

    public ArmWorkoutRecyclerAdapter(FragmentManager fm, Activity activity, Context context, SharedPreferences sharedPreferences1){
        currentContext = context;
        sharedPreferences = sharedPreferences1;
        parentActivity=activity;
        fragmentManager = fm;
        try{
            armWorkoutsArrayList =  (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("ArmWorkoutSet",null));
        }catch(IOException e){
            Log.e("EEDF",e.toString());
        }
    }

    @Override
    public ArmWorkoutRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View workoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_for_workout_recycler,parent,false);
        return new ArmWorkoutRecyclerAdapter.ViewHolder(workoutView);
    }

    @Override
    public void onBindViewHolder(final ArmWorkoutRecyclerAdapter.ViewHolder holder, int position) {
        String workout = armWorkoutsArrayList.get(position);
        workoutButton.setText(workout);
        workoutButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View v) {
                int pos = holder.getLayoutPosition();
                workoutButtonSelected(pos, armWorkoutsArrayList.get(pos));
            }
        });
    }

    @Override
    public int getItemCount() {
        int size;
        if (armWorkoutsArrayList == null){
            size = 0;
        }else{
            size = armWorkoutsArrayList.size();
        }
        return size;
    }

    public void workoutButtonSelected(int position, String selectedWorkoutTitle){
        int pos = position + 1;
        String workoutSelected = "ArmWorkout"+String.valueOf(pos);
        Bundle bundle = new Bundle();
        bundle.putString("selectedWorkout",workoutSelected);
        bundle.putString("selectedWorkoutTitle",selectedWorkoutTitle);
        armExercisesIntent = new Intent(currentContext,ArmExercisesActivity.class);
        armExercisesIntent.putExtras(bundle);
        currentContext.startActivity(armExercisesIntent);
    }
    public class ViewHolder extends RecyclerView.ViewHolder  {
        public ViewHolder(View v) {
            super(v);
            workoutButton =(Button) v.findViewById(R.id.workoutButton);
        }
    }
}
