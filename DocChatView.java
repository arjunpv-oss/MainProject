package com.example.bodyhero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
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

public class DocChatView extends AppCompatActivity implements TextWatcher, AdapterView.OnItemClickListener {
    EditText edsearch;
    ListView lvs;

    String [] patient_id,name,photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_chat_view);

        edsearch=(EditText) findViewById(R.id.editTextTextPersonName2);
        edsearch.addTextChangedListener(this);
        lvs=(ListView) findViewById(R.id.ls2);

        lvs.setOnItemClickListener(this);

        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String IP = sh.getString("ip", "");
        String url = "http://" + IP + ":5000/and_viewpatient";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(com.android.volley.Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(DocChatView.this, "===="+response, Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            String status = jsonObj.getString("status");
                            if (status.equalsIgnoreCase("ok")) {
                                JSONArray js= jsonObj.getJSONArray("data");

                                name=new String[js.length()];
                                patient_id=new String[js.length()];
                                photo=new String[js.length()];
//                                qualification=new String[js.length()];
//                                experience=new String[js.length()];
//                                ftime=new String[js.length()];
//                                ttime=new  String[js.length()];
//                                schedule_id=new  String[js.length()];




                                for(int i=0;i<js.length();i++)
                                {
                                    JSONObject u=js.getJSONObject(i);
                                    name[i]=u.getString("patient_name");
                                    patient_id[i]=u.getString("plid");
                                    photo[i]=u.getString("patient_image");
//                                    qualification[i]=u.getString("d_qualification");
//                                    experience[i]=u.getString("d_experience");
//                                    ftime[i]=u.getString("s");
//                                    ttime[i]=u.getString("t");
//                                    schedule_id[i]=u.getString("schedule_id");
                                }
                                lvs.setAdapter(new CustomDocChatView(getApplicationContext(),patient_id,photo,name));

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
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {


    }

    @Override
    public void afterTextChanged(final Editable editable) {

        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String IP = sh.getString("ip", "");
        String url = "http://" + IP + ":5000/and_viewpatient_search";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(com.android.volley.Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            String status = jsonObj.getString("status");
                            Toast.makeText(DocChatView.this, "==="+response, Toast.LENGTH_SHORT).show();
                            if (status.equalsIgnoreCase("ok")) {
                                JSONArray js= jsonObj.getJSONArray("data");

                                name=new String[js.length()];
                                patient_id=new String[js.length()];
                                photo=new String[js.length()];
//                                qualification=new String[js.length()];
//                                experience=new String[js.length()];
//                                ftime=new String[js.length()];
//                                ttime=new  String[js.length()];
//                                schedule_id=new  String[js.length()];



                                for(int i=0;i<js.length();i++)
                                {
                                    JSONObject u=js.getJSONObject(i);
                                    name[i]=u.getString("patient_name");
                                    patient_id[i]=u.getString("plid");
                                    photo[i]=u.getString("patient_image");
//                                    qualification[i]=u.getString("qualification");
//                                    experience[i]=u.getString("experience");
//                                    ftime=new String[js.length()];
//                                    ttime=new  String[js.length()];
//                                    schedule_id[i]=u.getString("schedule_id");


                                }
                                lvs.setAdapter(new CustomDocChatView(getApplicationContext(),patient_id,photo,name));

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
                params.put("name",editable.toString());

                return params;
            }
        };


        requestQueue.add(postRequest);


    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor ed1=sh.edit();
        ed1.putString("toolid",patient_id[position]);
        ed1.putString("bnm",name[position]);
        ed1.commit();

        Intent ij=new Intent(getApplicationContext(),Test2.class);
        startActivity(ij);

    }
}