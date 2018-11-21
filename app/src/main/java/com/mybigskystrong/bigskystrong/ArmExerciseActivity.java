package com.mybigskystrong.bigskystrong;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ArmExerciseActivity extends AppCompatActivity {
    Intent ArmActivityIntent;
    ListView exerciseListView;
    TextView workoutTextView;
    String jsonString;
    String workoutName;
    Intent ArmExercisesIntent;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_listview);

        ArmExercisesIntent = getIntent();
        bundle = ArmExercisesIntent.getExtras();
        workoutName = bundle.getString("workoutName");
        Log.e("aea","Workout Name Passed to Exercise Activity: " + workoutName);
        workoutTextView = (TextView) findViewById(R.id.workoutTypeTextView);
        workoutTextView.setText(workoutName);
        exerciseListView = (ListView) findViewById(R.id.workoutListView);
        getExercises(exerciseListView);
    }

    class singleRow{
        String exerciseName;

        singleRow(String name){
            this.exerciseName = name;
        }
    }

    class customAdapter extends BaseAdapter {

        ArrayList<ArmExerciseActivity.singleRow> list;
        Context c;
        String jsonObjectWorkoutName;

        customAdapter (Context context,JSONArray jsonArray){
            c = context;
            list = new ArrayList<ArmExerciseActivity.singleRow>();

            for (int i=0;i<jsonArray.length();i++){
                try{
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    String exerciseName = "";
                    jsonObjectWorkoutName = jsonObject.getString("Workout_Name");
                    if (jsonObjectWorkoutName.equals(workoutName)){
                        for (int j = 1 ; j<=jsonObject.length(); j++){
                            exerciseName = "Exercise_" + Integer.toString(j);
                            if (jsonObject.getString(exerciseName).equals("") ) {
                            }else{
                                list.add(new ArmExerciseActivity.singleRow(jsonObject.getString(exerciseName)));
                            }
                        }

                    }


                }catch (JSONException e) {
                    Log.e("ma", e.toString());
                }
            }
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View row = layoutInflater.inflate(R.layout.single_exercise,parent,false);

            TextView exerciseTextView = (TextView) row.findViewById(R.id.exerciseTextView);
            ArmExerciseActivity.singleRow tmp = list.get(position);
            exerciseTextView.setText(tmp.exerciseName);


            return row;
        }
    }

    public void getExercises(View view){
        new ArmExerciseActivity.backgroundTask().execute();
    }

    class backgroundTask extends AsyncTask<Void,Void,JSONArray> {

        String jsonURL;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            jsonURL ="https://mybigskystrong.com/Training/androidArmsDB.php";
        }

        @Override
        protected JSONArray doInBackground(Void... voids) {
            try{
                URL url = new URL(jsonURL);
                HttpURLConnection httpURLConnection =(HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();


                while ((jsonString = bufferedReader.readLine()) != null){
                    stringBuilder.append(jsonString+"\n");
                }

                JSONArray jsonArray = new JSONArray(stringBuilder.toString());



                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return jsonArray;
            }catch (IOException e){

                Log.e("ma",e.toString());
            } catch (JSONException e) {
                Log.e("ma",e.toString());
            }


            return null;
        }

        @Override
        protected void onPostExecute(JSONArray resultArray) {
            exerciseListView.setAdapter(new ArmExerciseActivity.customAdapter(getBaseContext(),resultArray));
        }


    }

    @Override
    public void onBackPressed() {
        ArmActivityIntent = new Intent(this,ArmWorkoutActivity.class);
        startActivity(ArmActivityIntent);
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
        }
        return super.onOptionsItemSelected(menuItem);
    }



}
