package com.example.bodyhero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class view_rating extends AppCompatActivity {

    ListView lvs;

    String [] review,date,rating,part_name,photo,contact,email;

FloatingActionButton fb;

    @Override
    public void onBackPressed() {
        Intent ins =new Intent(getApplicationContext(),DoctorHome.class);
        startActivity(ins);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewreviews);


        lvs=(ListView) findViewById(R.id.lv1);



        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String IP = sh.getString("ip", "");
        String url = "http://" + IP + ":5000/and_viewreviews";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(com.android.volley.Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            String status = jsonObj.getString("status");
                            if (status.equalsIgnoreCase("ok")) {
                                JSONArray js= jsonObj.getJSONArray("data");

                                review=new String[js.length()];
                                date=new String[js.length()];
                                rating=new String[js.length()];
                                part_name=new String[js.length()];
                                photo=new String[js.length()];
                                contact=new String[js.length()];
                                email=new String[js.length()];

                                for(int i=0;i<js.length();i++)
                                {
                                    JSONObject u=js.getJSONObject(i);
                                    review[i]=u.getString("rev_content");
                                    date[i]=u.getString("rev_date");
                                    rating[i]=u.getString("rating");
                                    part_name[i]=u.getString("patient_name");
                                    photo[i]=u.getString("patient_image");
                                    contact[i]=u.getString("patient_phone");
                                    email[i]=u.getString("patient_email");
                                }

                                lvs.setAdapter(new customviewreviews(getApplicationContext(),review,date,rating,part_name,photo,contact,email));
                            } else {
                                Toast.makeText(getApplicationContext(),  "No values", Toast.LENGTH_LONG);
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), "Error"+e.getMessage(), Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Toast.makeText(getApplicationContext(), "eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Map<String, String> params = new HashMap<String, String>();
                params.put("lid",sh.getString("lid",""));
                return params;
            }
        };


        requestQueue.add(postRequest);




    }
}