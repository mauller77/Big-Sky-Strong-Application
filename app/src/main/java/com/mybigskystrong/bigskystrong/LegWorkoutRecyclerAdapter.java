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

public class LegWorkoutRecyclerAdapter extends RecyclerView.Adapter<LegWorkoutRecyclerAdapter.ViewHolder> {
    public Button workoutButton;
    Context currentContext;
    SharedPreferences sharedPreferences;
    ArrayList<String> legWorkoutsArrayList;
    Intent legExercisesIntent;
    FragmentManager fragmentManager;
    Activity parentActivity;

    public LegWorkoutRecyclerAdapter(FragmentManager fm, Activity activity, Context context, SharedPreferences sharedPreferences1){
        Log.e("LWRA","Reached the leg workout recycler adapter constructor");

        currentContext = context;
        sharedPreferences = sharedPreferences1;
        parentActivity=activity;
        fragmentManager = fm;
        try{
            legWorkoutsArrayList =  (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("LegWorkoutSet",null));
        }catch(IOException e){
            Log.e("EEDF",e.toString());
        }
    }

    @Override
    public LegWorkoutRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.e("LWRA","Reached the leg workout on create view holder");
        View workoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_for_workout_recycler,parent,false);
        return new LegWorkoutRecyclerAdapter.ViewHolder(workoutView);
    }

    @Override
    public void onBindViewHolder(final LegWorkoutRecyclerAdapter.ViewHolder holder, int position) {
        String workout = legWorkoutsArrayList.get(position);
        Log.e("LWRA","Workout: " + legWorkoutsArrayList.get(position) + " is in position: " + position);
        workoutButton.setText(workout);
        workoutButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View v) {
                int pos = holder.getLayoutPosition();
                workoutButtonSelected(pos, legWorkoutsArrayList.get(pos));
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.e("LWRA","Reached the leg workout get item count");
        int size;
        if (legWorkoutsArrayList == null){
            size = 0;
        }else{
            size = legWorkoutsArrayList.size();
        }
        return size;
    }

    public void workoutButtonSelected(int position, String selectedWorkoutTitle){
        int pos = position + 1;
        String workoutSelected = "LegWorkout"+String.valueOf(pos);
        Log.e("LWRA","Workout selected is: " + workoutSelected);
        Bundle bundle = new Bundle();
        bundle.putString("selectedWorkout",workoutSelected);
        bundle.putString("selectedWorkoutTitle",selectedWorkoutTitle);
        legExercisesIntent = new Intent(currentContext,LegExercisesActivity.class);
        legExercisesIntent.putExtras(bundle);
        currentContext.startActivity(legExercisesIntent);
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        public ViewHolder(View v) {

            super(v);
            Log.e("LWRA","Reached the leg workout recycler adapter constructor");
            workoutButton =(Button) v.findViewById(R.id.workoutButton);
        }
    }
}
