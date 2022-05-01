package com.example.bodyhero;

import androidx.appcompat.app.AppCompatActivity;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DoctorViewAppointment extends AppCompatActivity {
    ListView lv;
    String[] appoint_id,plid,name,age,place,phone,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_view_appointment);
        lv=(ListView)findViewById(R.id.ls2);
        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String IP = sh.getString("ip", "");
        String url = "http://" + IP + ":5000/and_docviewbookedpatientdetails_post";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(com.android.volley.Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(DoctorViewAppointment.this, "===="+response, Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            String status = jsonObj.getString("status");
                            if (status.equalsIgnoreCase("ok")) {
                                JSONArray js= jsonObj.getJSONArray("data");
                                plid=new  String[js.length()];
                                appoint_id=new  String[js.length()];
                                name=new String[js.length()];
                                age=new String[js.length()];
                                place=new String[js.length()];
                                phone=new String[js.length()];
                                email=new String[js.length()];





                                for(int i=0;i<js.length();i++)
                                {
                                    JSONObject u=js.getJSONObject(i);
                                    appoint_id[i]=u.getString("appoint_id");
                                    plid[i]=u.getString("plid");
                                    name[i]=u.getString("patient_name");
                                    age[i]=u.getString("patient_age");
                                    place[i]=u.getString("patient_place");
                                    phone[i]=u.getString("patient_phone");
                                    email[i]=u.getString("patient_email");

                                    Toast.makeText(DoctorViewAppointment.this, "===="+email[i], Toast.LENGTH_SHORT).show();

                                }
                                lv.setAdapter(new CustoemDocViewAppointment(getApplicationContext(),appoint_id,plid,name,age,place,phone,email));

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