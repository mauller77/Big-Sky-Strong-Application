package com.mybigskystrong.bigskystrong;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class TrainingActivity extends AppCompatActivity implements View.OnClickListener {

    public Button chestWorkoutsButton;
    public Button backWorkoutsButton;
    public Button legWorkoutsButton;
    public Button shoulderWorkoutsButton;
    public Button armsWoroutButton;

    Intent armWorkoutIntent;
    Intent backWorkoutIntent;
    Intent chestWorkoutIntent;
    Intent legWorkoutIntent;
    Intent shoulderWorkoutIntent;
    Intent mainActivityIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.training_activity);

        chestWorkoutsButton = (Button) findViewById(R.id.chestButton);
        backWorkoutsButton = (Button) findViewById(R.id.backButton);
        legWorkoutsButton = (Button) findViewById(R.id.LegButton);
        shoulderWorkoutsButton = (Button) findViewById(R.id.shoulderButton);
        armsWoroutButton = (Button) findViewById(R.id.armButton);

        chestWorkoutsButton.setOnClickListener(this);
        backWorkoutsButton.setOnClickListener(this);
        legWorkoutsButton.setOnClickListener(this);
        shoulderWorkoutsButton.setOnClickListener(this);
        armsWoroutButton.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id){
            case android.R.id.home:
                mainActivityIntent = new Intent(getBaseContext(),MainAppActivity.class);
                startActivity(mainActivityIntent);
            case R.id.chestButton:

                chestWorkoutIntent = new Intent(getBaseContext(),ChestWorkoutActivity.class);

                startActivity(chestWorkoutIntent);

                break;
            case R.id.backButton:

                backWorkoutIntent = new Intent(getBaseContext(),BackWorkoutActivity.class);
                startActivity(backWorkoutIntent);

                break;
            case R.id.LegButton:

                legWorkoutIntent = new Intent(getBaseContext(),LegWorkoutActivity.class);
                startActivity(legWorkoutIntent);

                break;
            case R.id.shoulderButton:

                shoulderWorkoutIntent = new Intent(getBaseContext(),ShoulderWorkoutActivity.class);
                startActivity(shoulderWorkoutIntent);

                break;
            case R.id.armButton:

                armWorkoutIntent = new Intent(getBaseContext(),ArmWorkoutActivity.class);
                startActivity(armWorkoutIntent);

                break;




        }





    }

    @Override
    public void onBackPressed() {
        mainActivityIntent = new Intent(getBaseContext(),MainAppActivity.class);
        startActivity(mainActivityIntent);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }
}
