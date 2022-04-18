package com.example.bodyhero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PatientViewDoc extends AppCompatActivity implements TextWatcher {

    EditText edsearch;
    ListView lvs;

    String [] doctor_id,name,photo,qualification,experience,ftime,ttime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_view_doc);


        edsearch=(EditText) findViewById(R.id.editTextTextPersonName2);
        edsearch.addTextChangedListener(this);
        lvs=(ListView) findViewById(R.id.ls2);

        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String IP = sh.getString("ip", "");
        String url = "http://" + IP + ":5000/and_viewdoc_post";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(com.android.volley.Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(PatientViewDoc.this, "===="+response, Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            String status = jsonObj.getString("status");
                            if (status.equalsIgnoreCase("ok")) {
                                JSONArray js= jsonObj.getJSONArray("data");

                                name=new String[js.length()];
                                doctor_id=new String[js.length()];
                                photo=new String[js.length()];
                                qualification=new String[js.length()];
                                experience=new String[js.length()];
                                ftime=new String[js.length()];
                                ttime=new  String[js.length()];




                                for(int i=0;i<js.length();i++)
                                {
                                    JSONObject u=js.getJSONObject(i);
                                    name[i]=u.getString("d_name");
                                    doctor_id[i]=u.getString("dlid");
                                    photo[i]=u.getString("d_image");
                                    qualification[i]=u.getString("d_qualification");
                                    experience[i]=u.getString("d_experience");
                                    ftime[i]=u.getString("s");
                                    ttime[i]=u.getString("t");
                                }
                                lvs.setAdapter(new CustomPatientViewDoc(getApplicationContext(),doctor_id,photo,name,qualification,experience,ftime,ttime));

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



    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String IP = sh.getString("ip", "");
        String url = "http://" + IP + ":5000/and_viewdoc_post";
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

                                name=new String[js.length()];
                                doctor_id=new String[js.length()];
                                photo=new String[js.length()];
                                qualification=new String[js.length()];
                                experience=new String[js.length()];
                                ftime=new String[js.length()];
                                ttime=new  String[js.length()];



                                for(int i=0;i<js.length();i++)
                                {
                                    JSONObject u=js.getJSONObject(i);
                                    name[i]=u.getString("name");
                                    doctor_id[i]=u.getString("log_id");
                                    photo[i]=u.getString("photo");
                                    qualification[i]=u.getString("qualification");
                                    experience[i]=u.getString("experience");
                                    ftime=new String[js.length()];
                                    ttime=new  String[js.length()];


                                }
                                lvs.setAdapter(new CustomPatientViewDoc(getApplicationContext(),doctor_id,photo,name,qualification,experience,ftime,ttime));

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
                params.put("name",edsearch.getText().toString());
                params.put("lid",sh.getString("lid",""));

                return params;
            }
        };


        requestQueue.add(postRequest);

    }

    @Override
    public void afterTextChanged(Editable s) {

    }



    }