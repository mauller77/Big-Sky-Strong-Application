package com.mybigskystrong.bigskystrong;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

public class BackExercisesActivity extends AppCompatActivity {
    String workoutSelected;
    private TextView timerTextView;
    private Button timerStartButton;
    private Button timerStopResetButton;
    SharedPreferences sharedPreferences;
    private final static String TRAINING_PREFERENCES = "Training Preferences";
    ChestExercisesRecyclerAdapter backExercisesRecyclerAdapter;
    AddExerciseDialogFragment addExerciseDialogFragment;
    String backWorkoutsArrayListString;
    ArrayList<String> backWorkoutsArrayList;
    private RecyclerView exerciseRecyclerView;
    TextView workoutTitleTextView;
    String selectedWorkoutTitle;
    Intent backWorkoutsIntent;


    private long startTime = 0L;
    private Handler customHandler = new Handler();
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercises_recycler_view);
        workoutSelected = getIntent().getExtras().getString("selectedWorkout");
        selectedWorkoutTitle = getIntent().getExtras().getString("selectedWorkoutTitle");
        sharedPreferences = getApplicationContext().getSharedPreferences(TRAINING_PREFERENCES, MODE_PRIVATE);

        exerciseRecyclerView = (RecyclerView) findViewById(R.id.exerciseRecyclerView);
        backExercisesRecyclerAdapter = new ChestExercisesRecyclerAdapter(workoutSelected,selectedWorkoutTitle, getSupportFragmentManager(),sharedPreferences);
        exerciseRecyclerView.setAdapter(backExercisesRecyclerAdapter);
        exerciseRecyclerView.setLayoutManager(new LinearLayoutManager(this.getBaseContext()));

        timerTextView = (TextView) findViewById(R.id.timerTextView);
        timerStartButton = (Button) findViewById(R.id.startButton);
        timerStopResetButton=(Button) findViewById(R.id.stopButton);
        workoutTitleTextView = (TextView) findViewById(R.id.workoutTitleTextView);
        workoutTitleTextView.setText(selectedWorkoutTitle);

        timerStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startTime = SystemClock.uptimeMillis();
                customHandler.postDelayed(updateTimerThread,0);
                timerStopResetButton.setText("Stop");
            }
        });

        timerStopResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String stopText = timerStopResetButton.getText().toString();

                Log.e("BWF","Stop/Reset Button Reads: " + stopText);

                if (stopText.equals("Stop")) {
                    timeSwapBuff += timeInMilliseconds;
                    customHandler.removeCallbacks(updateTimerThread);
                    timerStopResetButton.setText("Reset");
                    timerStartButton.setText("Resume");
                }

                if (stopText.equals("Reset")){
                    timeSwapBuff = 0L;
                    timeInMilliseconds = 0L;
                    customHandler.removeCallbacks(updateTimerThread);
                    timerStopResetButton.setText("Stop");
                    timerStartButton.setText("Start");
                    timerTextView.setText("00:00:00");
                }

            }
        });

    }

    private Runnable updateTimerThread = new Runnable() {
        @Override
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeSwapBuff + timeInMilliseconds;
            int secs = (int) (updatedTime/1000);
            int mins = secs/60;
            secs = secs%60;
            int milliseconds = (int) (updatedTime % 1000);
            timerTextView.setText("" + mins + ":" + String.format("%02d",secs)
                    + ":" + String.format("%02d",milliseconds));
            customHandler.postDelayed(this,0);

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.exercises_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.addExerciseItem:
                Bundle bundle = new Bundle();
                bundle.putString("bodyPart","BackExerciseActivity");
                bundle.putString("workoutSelected",workoutSelected);
                bundle.putString("selectedWorkoutTitle",selectedWorkoutTitle);
                addExerciseDialogFragment = new AddExerciseDialogFragment();
                addExerciseDialogFragment.setArguments(bundle);
                addExerciseDialogFragment.show(getSupportFragmentManager(),"AEA");
                break;
            case R.id.deleteWorkoutItem:
                deleteWorkout();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        backWorkoutsIntent = new Intent(getBaseContext(),BackWorkoutActivity.class);
        startActivity(backWorkoutsIntent);
    }

    public void deleteWorkout(){
        try{
            backWorkoutsArrayList =  (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("BackWorkoutSet",null));
        }catch(IOException e){
            Log.e("EEDF",e.toString());
        }
        Log.e("BEA","BackWorkoutsArrayList: " + backWorkoutsArrayList);
        Log.e("BEA","Selected Workout Title to delete: " + selectedWorkoutTitle);
        backWorkoutsArrayList.remove(selectedWorkoutTitle);
        try{
            backWorkoutsArrayListString = ObjectSerializer.serialize(backWorkoutsArrayList);
        }catch(IOException e){
            Log.e("EEDF", e.toString());
        }
        Log.e("BEA","Removing " + workoutSelected + " from the shared preferences");
        sharedPreferences.edit().remove(workoutSelected).apply();
        sharedPreferences.edit().putString("BackWorkoutSet", backWorkoutsArrayListString).apply();

        backWorkoutsIntent = new Intent(getBaseContext(),BackWorkoutActivity.class);
        startActivity(backWorkoutsIntent);
    }
}
