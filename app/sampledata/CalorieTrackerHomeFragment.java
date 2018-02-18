package com.mybigskystrong.bigskystrong;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by josh on 7/20/17.
 */

public class CalorieTrackerHomeFragment extends Fragment {

    RecyclerView dailyFoodsRecyclerView;
    TextView currentCaloriesTextView;
    SharedPreferences calorieTrackerSharedPreferences;
    int cals;
    TextView foodNameTextView;
    TextView foodDescriptionTextView;
    ArrayList<ArrayList<String[]>> allTrackedDays = new ArrayList<>();
    ArrayList<String[]> currentDayArrayList = new ArrayList<>();
    int allTrackedDaysSize;
    final String CALORIE_TRACKER_SHARED_PREFERENCES = "calorieTrackerSharedPreferences";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        calorieTrackerSharedPreferences = getActivity().getApplicationContext().getSharedPreferences(CALORIE_TRACKER_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        View view = inflater.inflate(R.layout.calorie_tracker_home_fragment, container, false);
        currentCaloriesTextView = (TextView) view.findViewById(R.id.totalDailyCaloriesTextView);

        try {
            allTrackedDays = (ArrayList<ArrayList<String[]>>) ObjectSerializer.deserialize(calorieTrackerSharedPreferences.getString("calorieTrackerArrayList", null));
        }catch (IOException e){
            Log.e(this.toString(),e.toString());
        }


        allTrackedDaysSize = allTrackedDays.size();
        currentDayArrayList = allTrackedDays.get(allTrackedDaysSize-1);

        Log.e(this.toString(),"All tracked days: " + allTrackedDays);
        Log.e(this.toString(),"Current Day ArrayList: " + currentDayArrayList);
        for(String[] strings : currentDayArrayList){
            String[] s = new String[]{};
            try{
                s = (String[]) ObjectSerializer.deserialize(strings[0]);
            }catch(IOException e){};
            Log.e(this.toString(),s.toString());
        }
        cals = calorieTrackerSharedPreferences.getInt("currentCaloriesInteger", 0);
        currentCaloriesTextView.setText(String.valueOf(cals));

        dailyFoodsRecyclerView = (RecyclerView) view.findViewById(R.id.dailyFoodRecyclerView);
        DailyFoodRecyclerViewAdapter adapter = new DailyFoodRecyclerViewAdapter();
        dailyFoodsRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        dailyFoodsRecyclerView.setAdapter(adapter);


        return view;
    }

    class DailyFoodRecyclerViewAdapter extends RecyclerView.Adapter<DailyFoodRecyclerViewAdapter.ViewHolder>{

        @Override
        public DailyFoodRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calorie_tracker_foods_view,parent,false);
            return new DailyFoodRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(DailyFoodRecyclerViewAdapter.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }

        class ViewHolder extends RecyclerView.ViewHolder{

            public ViewHolder(View itemView) {
                super(itemView);
                foodNameTextView = (TextView) itemView.findViewById(R.id.foodTypeAndNameTextView);
                foodDescriptionTextView = (TextView) itemView.findViewById(R.id.foodDescriptionTextView);
            }
        }
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.calorie_tracker_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.addFoodItem:
                getFragmentManager().beginTransaction().replace(R.id.calorieTrackerFragmentContainer,new CalorieTrackerAddFoodFragment()
                    ).commit();
        }
        return super.onOptionsItemSelected(item);
    }
}
