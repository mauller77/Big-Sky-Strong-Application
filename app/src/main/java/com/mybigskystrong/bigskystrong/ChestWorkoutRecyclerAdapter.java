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


public class ChestWorkoutRecyclerAdapter extends RecyclerView.Adapter<ChestWorkoutRecyclerAdapter.ViewHolder> {
    public Button workoutButton;
    Context currentContext;
    SharedPreferences sharedPreferences;
    ArrayList<String> chestWorkoutsArrayList;
    Intent chestExercisesIntent;
    FragmentManager fragmentManager;
    Activity parentActivity;

    public ChestWorkoutRecyclerAdapter(FragmentManager fm, Activity activity, Context context, SharedPreferences sharedPreferences1){
        currentContext = context;
        sharedPreferences = sharedPreferences1;
        parentActivity=activity;
        fragmentManager = fm;
        try{
            chestWorkoutsArrayList =  (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("ChestWorkoutSet",null));
        }catch(IOException e){
            Log.e("EEDF",e.toString());
        }
    }

    @Override
    public ChestWorkoutRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View workoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_for_workout_recycler,parent,false);
        return new ViewHolder(workoutView);
    }

    @Override
    public void onBindViewHolder(final ChestWorkoutRecyclerAdapter.ViewHolder holder, int position) {
        String workout = chestWorkoutsArrayList.get(position);
        workoutButton.setText(workout);
        workoutButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View v) {
                int pos = holder.getLayoutPosition();
                Log.e("cwra","position selected = " + pos);
                workoutButtonSelected(pos, chestWorkoutsArrayList.get(pos));
            }
        });
    }

    @Override
    public int getItemCount() {
        int size;
        if (chestWorkoutsArrayList == null){
            size = 0;
        }else{
            size = chestWorkoutsArrayList.size();
        }
        return size;
    }

    public void workoutButtonSelected(int position, String selectedWorkoutTitle){
        int pos = position + 1;
        String workoutSelected = "ChestWorkout"+String.valueOf(pos);
        Bundle bundle = new Bundle();
        bundle.putString("selectedWorkout",workoutSelected);
        bundle.putString("selectedWorkoutTitle",selectedWorkoutTitle);
        chestExercisesIntent = new Intent(currentContext,ChestExercisesActivity.class);
        chestExercisesIntent.putExtras(bundle);
        currentContext.startActivity(chestExercisesIntent);
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        public ViewHolder(View v) {
            super(v);
            workoutButton =(Button) v.findViewById(R.id.workoutButton);
        }
    }
}


