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

/**
 * Created by josh on 7/18/17.
 */

public class ShoulderWorkoutRecyclerAdapter extends RecyclerView.Adapter<ShoulderWorkoutRecyclerAdapter.ViewHolder> {
    public Button workoutButton;
    Context currentContext;
    SharedPreferences sharedPreferences;
    ArrayList<String> shoulderWorkoutsArrayList;
    Intent shoulderExercisesIntent;
    FragmentManager fragmentManager;
    Activity parentActivity;

    public ShoulderWorkoutRecyclerAdapter(FragmentManager fm, Activity activity, Context context, SharedPreferences sharedPreferences1){
        currentContext = context;
        sharedPreferences = sharedPreferences1;
        parentActivity=activity;
        fragmentManager = fm;
        try{
            shoulderWorkoutsArrayList =  (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("ShoulderWorkoutSet",null));
        }catch(IOException e){
            Log.e("EEDF",e.toString());
        }
    }

    @Override
    public ShoulderWorkoutRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View workoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_for_workout_recycler,parent,false);
        return new ShoulderWorkoutRecyclerAdapter.ViewHolder(workoutView);
    }

    @Override
    public void onBindViewHolder(final ShoulderWorkoutRecyclerAdapter.ViewHolder holder, int position) {
        String workout = shoulderWorkoutsArrayList.get(position);
        workoutButton.setText(workout);
        workoutButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View v) {
                int pos = holder.getLayoutPosition();
                workoutButtonSelected(pos, shoulderWorkoutsArrayList.get(pos));
            }
        });
    }

    @Override
    public int getItemCount() {
        int size;
        if (shoulderWorkoutsArrayList == null){
            size = 0;
        }else{
            size = shoulderWorkoutsArrayList.size();
        }
        return size;
    }

    public void workoutButtonSelected(int position, String selectedWorkoutTitle){
        int pos = position + 1;
        String workoutSelected = "ShoulderWorkout"+String.valueOf(pos);
        Bundle bundle = new Bundle();
        bundle.putString("selectedWorkout",workoutSelected);
        bundle.putString("selectedWorkoutTitle",selectedWorkoutTitle);
        shoulderExercisesIntent = new Intent(currentContext,ShoulderExercisesActivity.class);
        shoulderExercisesIntent.putExtras(bundle);
        currentContext.startActivity(shoulderExercisesIntent);
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        public ViewHolder(View v) {
            super(v);
            workoutButton =(Button) v.findViewById(R.id.workoutButton);
        }
    }
}
