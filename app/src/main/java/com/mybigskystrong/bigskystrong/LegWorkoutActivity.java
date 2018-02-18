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

public class LegWorkoutActivity extends AppCompatActivity {
    private final static String TRAINING_PREFERENCES = "Training Preferences";
    SharedPreferences sharedPreferences;
    Intent trainingActivityIntent;
    AddWorkoutDialogFragment addWorkoutDialogFragment;
    ArrayList<String> legWorkoutsArrayList;
    RecyclerView legWorkoutsRecyclerView;
    LegWorkoutRecyclerAdapter legWorkoutRecyclerAdapter;
    TextView workoutTypeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_recycler);

        sharedPreferences = getApplicationContext().getSharedPreferences(TRAINING_PREFERENCES,MODE_PRIVATE);
        try{
            legWorkoutsArrayList =  (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("LegWorkoutSet",null));
        }catch(IOException e){
            Log.e("EEDF",e.toString());
        }

        legWorkoutsRecyclerView= (RecyclerView) findViewById(R.id.workoutsRecyclerView);
        legWorkoutRecyclerAdapter = new LegWorkoutRecyclerAdapter(getSupportFragmentManager(),this,this.getBaseContext(),sharedPreferences);
        legWorkoutsRecyclerView.setAdapter(legWorkoutRecyclerAdapter);
        legWorkoutsRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        workoutTypeTextView = (TextView) findViewById(R.id.workoutTypeTextView);
        workoutTypeTextView.setText("Leg Workouts");

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
                bundle.putString("bodyPart","Leg");
                addWorkoutDialogFragment.setArguments(bundle);
                addWorkoutDialogFragment.show(getSupportFragmentManager(),"Add Workout Dialog Fragment");
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }

}
