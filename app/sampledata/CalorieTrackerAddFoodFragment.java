package com.mybigskystrong.bigskystrong;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by josh on 7/20/17.
 */

public class CalorieTrackerAddFoodFragment extends Fragment {
    String API_KEY;
    String API_SHARED_SECRET;
    String requestURL = "http://platform.fatsecret.com/rest/server.api";
    String HTTP_METHOD = "GET";
    String APP_SIGNATURE_METHOD = "HmacSHA1";
    RecyclerView foodResultsRecyclerView;
    TextView updateTextView;
    EditText foodSearch;
    String foodSearched = "";
    ArrayList<String> foodNameArrayList = new ArrayList<>();
    ArrayList<String> foodTypeArrayList = new ArrayList<>();
    ArrayList<String> foodDescriptionArrayList = new ArrayList<>();
    CalorieTrackerAddFoodFragmentAdapter calorieTrackerAddFoodFragmentAdapter;
    TextView foodTypeAndNameTextView;
    TextView foodDescriptionTextView;
    SharedPreferences calorieTrackerSharedPreferences;
    int allTrackedDaysSize;
    final String CALORIE_TRACKER_SHARED_PREFERENCES = "calorieTrackerSharedPreferences";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calorie_tracker_add_food_fragment,container,false);
        calorieTrackerSharedPreferences = getActivity().getApplicationContext().getSharedPreferences(CALORIE_TRACKER_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        API_KEY = "8456c1a777c2481ca27386007fc229c9";
        API_SHARED_SECRET="1e893d903dbd46ecb93d8b6b5bb9627b";
        foodResultsRecyclerView = (RecyclerView) view.findViewById(R.id.foodResultsRecyclerView);
        calorieTrackerAddFoodFragmentAdapter= new CalorieTrackerAddFoodFragmentAdapter();
        foodResultsRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        foodResultsRecyclerView.setAdapter(calorieTrackerAddFoodFragmentAdapter);
        foodSearch = (EditText) view.findViewById(R.id.foodSearchEditText);
        foodSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                foodSearched = s.toString();
                try {
                    getFoodItems(foodSearched);
                }catch(UnsupportedEncodingException u){}catch(JSONException j){}

            }
        });

        return view;
    }

    class CalorieTrackerAddFoodFragmentAdapter extends RecyclerView.Adapter<CalorieTrackerAddFoodFragmentAdapter.ViewHolder> {
        @Override
        public CalorieTrackerAddFoodFragmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View foodView = LayoutInflater.from(parent.getContext()).inflate(R.layout.calorie_tracker_foods_view,parent,false);
            foodView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView textView = (TextView) v.findViewById(R.id.foodTypeAndNameTextView);
                    String foodTypeAndName = textView.getText().toString();
                    TextView textView1 = (TextView) v.findViewById(R.id.foodDescriptionTextView);
                    String foodDescription = textView1.getText().toString();
                    foodClicked(foodTypeAndName,foodDescription);
                }
            });
            return new CalorieTrackerAddFoodFragmentAdapter.ViewHolder(foodView);
        }
        @Override
        public void onBindViewHolder(CalorieTrackerAddFoodFragmentAdapter.ViewHolder holder, int position) {
            String foodType = foodTypeArrayList.get(position);
            String foodName = foodNameArrayList.get(position);
            String foodDescription = foodDescriptionArrayList.get(position);

            foodTypeAndNameTextView.setText(foodType + " " + foodName);
            foodDescriptionTextView.setText(foodDescription);
        }
        @Override
        public int getItemCount() {
            int size;
            if (foodNameArrayList.size() == 0){
                size = 0;
            }else{
                size = foodNameArrayList.size();
            }
            return size;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public ViewHolder(View v) {
                super(v);
                foodTypeAndNameTextView = (TextView) v.findViewById(R.id.foodTypeAndNameTextView);
                foodDescriptionTextView = (TextView) v.findViewById(R.id.foodDescriptionTextView);
            }
        }
    }

    public void foodClicked(String typeAndName, String Description){
        AddFoodDialogFragment addFoodDialogFragment = new AddFoodDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name",typeAndName);
        bundle.putString("description",Description);
        addFoodDialogFragment.setArguments(bundle);
        addFoodDialogFragment.show(getFragmentManager(), "CTAFF");
    }

    public static class AddFoodDialogFragment extends android.support.v4.app.DialogFragment{
        TextView nameTextView;
        TextView descriptionTextView;
        Button submitButton;
        Button cancelButton;
        String name;
        String description;
        int calories;
        SharedPreferences calorieTrackerSharedPreferences;

        @Override
        public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
            calorieTrackerSharedPreferences = getActivity().getApplicationContext().getSharedPreferences("calorieTrackerSharedPreferences",Context.MODE_PRIVATE);
            name = getArguments().getString("name");
            description = getArguments().getString("description");

            String[] parsedDescription = description.split(" ");
            Log.e(this.toString(),"Parsed Description: " + parsedDescription);

            for (String s : parsedDescription){
                if (s.contains("kcal")){
                    int i = s.indexOf("k");
                    String k = s.substring(0,i);
                    Log.e(this.toString(),"Calories: " + k);
                    calories = Integer.valueOf(k);
                }
            }
            Log.e(this.toString(),"Calories: " + calories);

            View view = inflater.inflate(R.layout.calorie_tracker_add_food_dialog_fragment,container,false);
            nameTextView = (TextView) view.findViewById(R.id.dfNameTextView);
            descriptionTextView = (TextView) view.findViewById(R.id.dfDescriptionTextView);
            submitButton = (Button) view.findViewById(R.id.addFoodButton);
            cancelButton = (Button) view.findViewById(R.id.cancelButton);

            nameTextView.setText(name);
            descriptionTextView.setText(description);
            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    calorieTrackerSharedPreferences.edit().putInt("currentCaloriesInteger",calories).apply();
                    dismiss();
                }
            });
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
            return view;
        }

        @Override
        public void onDismiss(DialogInterface dialog) {
            Map<String,?> calorieKeys = calorieTrackerSharedPreferences.getAll();

            for(Map.Entry<String,?> entry : calorieKeys.entrySet()){
                Log.e("map values",entry.getKey() + ": " +
                        entry.getValue().toString());
            }
            getFragmentManager().beginTransaction().replace(R.id.calorieTrackerFragmentContainer,new CalorieTrackerHomeFragment()).commit();
            super.onDismiss(dialog);
        }
    }

    public String generateRandomString() {
        Random r = new Random();
        StringBuffer n = new StringBuffer();
        for (int i = 0; i < r.nextInt(8) + 2; i++) {
            n.append(r.nextInt(26) + 'a');
        }
        return n.toString();
    }

    public String[] generateOauthParams() {
        String[] a = {
                "oauth_consumer_key=" + API_KEY,
                "oauth_signature_method=HMAC-SHA1",
                "oauth_timestamp=" + new Long(System.currentTimeMillis() / 1000).toString(),
                "oauth_nonce=" + generateRandomString(),
                "oauth_version=1.0",
                "format=json"
        };
        return a;
    }

    public String encode(String url) {
        if (url == null)
            return "";

        try {
            return URLEncoder.encode(url, "utf-8")
                    .replace("+", "%20")
                    .replace("!", "%21")
                    .replace("*", "%2A")
                    .replace("//", "%27")
                    .replace("(", "%28")
                    .replace(")", "%29");
        }
        catch (UnsupportedEncodingException wow) {
            throw new RuntimeException(wow.getMessage(), wow);
        }
    }

    public String join(String[] params, String separator) {
        StringBuffer b = new StringBuffer();
        for (int i = 0; i < params.length; i++) {
            if (i > 0) {
                b.append(separator);
            }
            b.append(params[i]);
        }
        return b.toString();
    }

    public String returnStringSortedAndSeparatedWithAmpersand(String[] params) {
        String[] p = Arrays.copyOf(params, params.length);
        Arrays.sort(p);
        return join(p, "&");
    }

    public String sign(String method, String uri, String[] params) throws UnsupportedEncodingException {

        String encodedURI = encode(uri);
        String encodedParams = encode(returnStringSortedAndSeparatedWithAmpersand(params));

        String[] p = {method, encodedURI, encodedParams};

        String text = join(p, "&");
        String sharedSecret = API_SHARED_SECRET + "&";
        SecretKey sk = new SecretKeySpec(sharedSecret.getBytes(), APP_SIGNATURE_METHOD);
        String sign = "";
        try {
            Mac m = Mac.getInstance(APP_SIGNATURE_METHOD);
            m.init(sk);
            sign = encode(new String(Base64.encode(m.doFinal(text.getBytes()), Base64.DEFAULT)).trim());
        } catch(java.security.NoSuchAlgorithmException e) {

        } catch(java.security.InvalidKeyException e) {

        }
        return sign;
    }

    public void getFoodItems(String query) throws UnsupportedEncodingException, JSONException {
        Log.e(this.toString(),"query: " + query);
        List<String> params = new ArrayList<>(Arrays.asList(generateOauthParams()));
        String[] template = new String[1];
        params.add("method=foods.search");
        params.add("max_results=50");
        params.add("search_expression=" + encode(query));
        params.add("oauth_signature=" + sign(HTTP_METHOD, requestURL, params.toArray(template)));
        Log.e(this.toString(), "Parameters: " + params.toString());
        URL url;
        try {
            url = new URL(requestURL + "?" + returnStringSortedAndSeparatedWithAmpersand(params.toArray(template)));
            Log.e(this.toString(), "The url trying to connect: " + url.toString());
            new getCurrentFoodArrayListTask().execute(url);
            Log.e(this.toString(),"Food Array list: " + foodNameArrayList.toString());
            calorieTrackerAddFoodFragmentAdapter.notifyDataSetChanged();
        }catch(MalformedURLException m){Log.e(this.toString(),"Exception: " + m.toString());}



    }

    class getCurrentFoodArrayListTask extends AsyncTask<URL,String,ArrayList<ArrayList<String>>> {


        @Override
        protected ArrayList<ArrayList<String>> doInBackground(URL... urls) {
            ArrayList<String> asyncTaskFoodNameArrayList = new ArrayList<>();
            ArrayList<String> asyncTaskFoodTypeArrayList = new ArrayList<>();
            ArrayList<String> asyncTaskFoodDescriptionArrayList = new ArrayList<>();

            try {
                URL u = urls[0];
                URLConnection api = u.openConnection();
                String line;
                StringBuilder builder = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(api.getInputStream()));
                while ((line = reader.readLine()) != null) builder.append(line);
                JSONObject jsonObject = new JSONObject(builder.toString());
                JSONObject foods = jsonObject.getJSONObject("foods");
                JSONArray food = foods.getJSONArray("food");

                for (int i = 0 ; i<food.length();i++){
                    JSONObject object = food.getJSONObject(i);
                    String foodName = object.getString("food_name");
                    asyncTaskFoodNameArrayList.add(foodName);
                }

                for (int i = 0 ; i<food.length();i++){
                    JSONObject object = food.getJSONObject(i);
                    String foodType = object.getString("food_type");
                    asyncTaskFoodTypeArrayList.add(foodType);
                }

                for (int i = 0 ; i<food.length();i++){
                    JSONObject object = food.getJSONObject(i);
                    String foodDescription = object.getString("food_description");
                    asyncTaskFoodDescriptionArrayList.add(foodDescription);
                }
            } catch (Exception e) {
                //publishProgress("Error: " + e.toString());
            }
            ArrayList<ArrayList<String>> foodInfoArrayList = new ArrayList<>();
            foodInfoArrayList.add(asyncTaskFoodTypeArrayList);
            foodInfoArrayList.add(asyncTaskFoodNameArrayList);
            foodInfoArrayList.add(asyncTaskFoodDescriptionArrayList);

            return foodInfoArrayList;

        }

        @Override
        protected void onProgressUpdate(String... values) {
            updateTextView.setText(values[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<ArrayList<String>> strings) {
            foodTypeArrayList = strings.get(0);
            foodNameArrayList = strings.get(1);
            foodDescriptionArrayList = strings.get(2);

        }
    }

}
