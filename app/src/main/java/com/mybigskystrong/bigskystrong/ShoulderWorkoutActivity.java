package com.mybigskystrong.bigskystrong;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;


public class ShoulderWorkoutActivity extends AppCompatActivity{
    private final static String TRAINING_PREFERENCES = "Training Preferences";
    SharedPreferences sharedPreferences;
    Intent trainingActivityIntent;
    AddWorkoutDialogFragment addWorkoutDialogFragment;
    ArrayList<String> shoulderWorkoutsArrayList;
    RecyclerView shoulderWorkoutsRecyclerView;
    ShoulderWorkoutRecyclerAdapter shoulderWorkoutRecyclerAdapter;
    TextView workoutTypeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_recycler);

        sharedPreferences = getApplicationContext().getSharedPreferences(TRAINING_PREFERENCES,MODE_PRIVATE);
        try{
            shoulderWorkoutsArrayList =  (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("ShoulderWorkoutSet",null));
        }catch(IOException e){
            Log.e("EEDF",e.toString());
        }

        shoulderWorkoutsRecyclerView = (RecyclerView) findViewById(R.id.workoutsRecyclerView);
        shoulderWorkoutRecyclerAdapter = new ShoulderWorkoutRecyclerAdapter(getSupportFragmentManager(),this,getBaseContext(),sharedPreferences);
        shoulderWorkoutsRecyclerView.setAdapter(shoulderWorkoutRecyclerAdapter);
        shoulderWorkoutsRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        workoutTypeTextView = (TextView) findViewById(R.id.workoutTypeTextView);
        workoutTypeTextView.setText("Shoulder Workouts");

    }

    @Override
    public void onBackPressed() {
        trainingActivityIntent = new Intent(this,TrainingActivity.class);
        startActivity(trainingActivityIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.workout_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.addWorkoutItem:
                Bundle bundle = new Bundle();
                addWorkoutDialogFragment = new AddWorkoutDialogFragment();
                bundle.putString("bodyPart","Shoulder");
                addWorkoutDialogFragment.setArguments(bundle);
                addWorkoutDialogFragment.show(getSupportFragmentManager(),"Add Workout Dialog Fragment");
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
