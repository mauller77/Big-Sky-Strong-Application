package com.mybigskystrong.bigskystrong;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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


public class ChestWorkoutActivity extends AppCompatActivity {
    Intent trainingActivityIntent;
    Intent chestExerciseActivityIntent;
    TextView workoutTypeTextView;
    String jsonString;
    ListView workoutListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_listview);

        workoutTypeTextView = (TextView) findViewById(R.id.workoutTypeTextView);
        workoutTypeTextView.setText("Chest Workouts");

        workoutListView = (ListView) findViewById(R.id.workoutListView);
        getJson(workoutListView);
    }

    class singleRow{
        String workoutName;
        String workoutDescription;

        singleRow(String name, String description){
            this.workoutName = name;
            this.workoutDescription = description;
        }
    }

    class customAdapter extends BaseAdapter {

        ArrayList<ChestWorkoutActivity.singleRow> list;
        Context c;

        customAdapter (Context context,JSONArray jsonArray){
            c = context;
            list = new ArrayList<ChestWorkoutActivity.singleRow>();

            for (int i=0;i<jsonArray.length();i++){
                try{
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    list.add(new ChestWorkoutActivity.singleRow(jsonObject.getString("Workout_Name"),jsonObject.getString("Exercise_1")));
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

            View row = layoutInflater.inflate(R.layout.single_workout,parent,false);

            Button workoutTitleButton = (Button) row.findViewById(R.id.workoutTitleButton);
            ChestWorkoutActivity.singleRow tmp = list.get(position);
            workoutTitleButton.setText(tmp.workoutName);
            workoutTitleButton.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("NewApi")
                @Override
                public void onClick(View view) {
                    chestExerciseActivityIntent = new Intent(getBaseContext(),ChestExerciseActivity.class);
                    Button clickedButton = (Button) view;
                    String clickedButtonTitle = clickedButton.getText().toString();
                    Log.e("awa","Clicked Button: " + clickedButtonTitle);
                    Bundle b = new Bundle();
                    b.putString("workoutName",clickedButtonTitle);
                    chestExerciseActivityIntent.putExtras(b);
                    startActivity(chestExerciseActivityIntent);
                }
            });


            return row;
        }
    }

    public void getJson(View view){
        new ChestWorkoutActivity.backgroundTask().execute();
    }

    class backgroundTask extends AsyncTask<Void,Void,JSONArray> {

        String jsonURL;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            jsonURL ="https://mybigskystrong.com/Training/androidChestDB.php";
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
            workoutListView.setAdapter(new ChestWorkoutActivity.customAdapter(getBaseContext(),resultArray));
        }


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
        }
        return super.onOptionsItemSelected(menuItem);
    }




}
