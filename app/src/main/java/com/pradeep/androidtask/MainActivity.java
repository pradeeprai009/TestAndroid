package com.pradeep.androidtask;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RequestQueue requestQueue;
    List<Data> list=new ArrayList<>();
    SliderView imageSlider;
    AdapterData adapterData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //finding the xml by id
        imageSlider = findViewById(R.id.image_slidernew);

        //calling web services for getting the data from api
        GetData();
    }

    public void GetData() {
        String API_GetData = "https://git.io/fjaqJ";
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(MainActivity.this);
        }
        StringRequest postRequest = new StringRequest(Request.Method.GET, API_GetData,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // our response was containing the "/", so first we will remove the "/".
                            if(response.contains("/")){
                                //after removing the "/"
                                response = response.replace("/", "");

                                //converting the response from string to JSONObject
                                JSONObject jsonObject = new JSONObject(response);

                                //getting the data list in JSONArray
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    //creadted a model class with Data
                                    Data data=new Data();
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    //getting the text from its Id
                                    String id = jsonObject1.getString("id");
                                    String text = jsonObject1.getString("text");

                                    //setting data from web to model
                                    data.setId(id);
                                    data.setText(text);

                                    //setting data into list
                                    list.add(data);
                                }
                                //sending list adapter method
                                setAdapter(list);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ErrorResponse", error.getMessage());
                    }
                }
        ) {
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);
    }
    public void setAdapter(List<Data> list){
        adapterData = new AdapterData(this, list);
        imageSlider.setSliderAdapter(adapterData);
        imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM);
        imageSlider.setSliderTransformAnimation(SliderAnimations.FADETRANSFORMATION);
        imageSlider.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        imageSlider.setScrollTimeInSec(60);
        imageSlider.startAutoCycle();
        imageSlider.setAutoCycle(true);

    }
}