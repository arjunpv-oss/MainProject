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

public class PatientViewAppointments extends AppCompatActivity {
    ListView lvs;
    String [] doctor_id,d_name,demail,slot,time,date,schedule_id,d_qualification,dphone,presdisease,pressymp,presmed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_view_appointments);
        lvs=(ListView) findViewById(R.id.ls2);
        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String IP = sh.getString("ip", "");
        String url = "http://" + IP + ":5000/and_patientviewappointments";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(com.android.volley.Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(PatientViewAppointments.this, "===="+response, Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            String status = jsonObj.getString("status");
                            if (status.equalsIgnoreCase("ok")) {
                                JSONArray js= jsonObj.getJSONArray("data");
                                date=new  String[js.length()];

                                d_name=new String[js.length()];
                               doctor_id=new String[js.length()];
                                demail=new String[js.length()];
                                slot=new String[js.length()];
                                time=new String[js.length()];
                                d_qualification=new String[js.length()];
                                dphone=new String[js.length()];
                                presdisease=new String[js.length()];
                                pressymp=new String[js.length()];
                                presmed=new String[js.length()];



                                schedule_id=new  String[js.length()];




                                for(int i=0;i<js.length();i++)
                                {
                                    JSONObject u=js.getJSONObject(i);
                                    date[i]=u.getString("schedule_date");
                                    d_name[i]=u.getString("d_name");
                                    d_qualification[i]=u.getString("d_qualification");
                                    dphone[i]=u.getString("d_phone");
                                    demail[i]=u.getString("d_email");
                                    slot[i]=u.getString("slot_id");
                                    time[i]=u.getString("slot_number");
                                    doctor_id[i]=u.getString("dlid");
                                    presdisease[i]=u.getString("pres_content");
                                    pressymp[i]=u.getString("pres_disease");
                                    presmed[i]=u.getString("pres_medicine");

                                    schedule_id[i]=u.getString("schedule_id");
                                }
                                lvs.setAdapter(new CustomPatientViewAppointment(getApplicationContext(),date,d_name,d_qualification,dphone,demail,slot,time,doctor_id,presdisease,pressymp,presmed,schedule_id));

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