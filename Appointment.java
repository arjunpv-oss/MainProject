package com.example.bodyhero;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Appointment extends AppCompatActivity {
    String [] schedule_id,schedule_ftime,schedule_ttime;
    Button bt6;
    GridView ggv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        bt6=(Button) findViewById(R.id.button6);
        ggv=(GridView) findViewById(R.id.gv);
        ggv.setVisibility(View.VISIBLE);

        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        String hu = sh.getString("ip", "");
        String url = "http://" + hu + ":5000/and_dates_get_more";



        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // response
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            if (jsonObj.getString("status").equalsIgnoreCase("ok")) {

                                JSONArray js= jsonObj.getJSONArray("data");
                                schedule_id=new String[js.length()];
                                schedule_ftime=new String[js.length()];
                                schedule_ttime=new String[js.length()];


                                for(int i=0;i<js.length();i++)
                                {
                                    JSONObject u=js.getJSONObject(i);
                                    schedule_id[i]=u.getString("slot");
                                    schedule_ftime[i]=u.getString("slot");
                                    schedule_ttime[i]=u.getString("slot");



                                }

                                ggv.setAdapter(new CustomAppointment(getApplicationContext(),schedule_id,schedule_ftime,schedule_ttime));


                            }


                            // }
//                                else {
//                                    Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_LONG).show();
//                                }

                        }    catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
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

                String id=sh.getString("doctor_id","");
                params.put("lid",id);
//                params.put("password",password);

                return params;


            }
        };

        int MY_SOCKET_TIMEOUT_MS=100000;

        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);



        String datess = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        bt6.setText(datess);
        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yy"; //In which you need put here


                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                bt6.setText(sdf.format(myCalendar.getTime()));
                String jk=sdf.format(myCalendar.getTime());
                Toast.makeText(Appointment.this, "--"+jk, Toast.LENGTH_SHORT).show();




                SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                String hu = sh.getString("ip", "");
                String url = "http://" + hu + ":5000/and_dates_get";



                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                                // response
                                try {
                                    JSONObject jsonObj = new JSONObject(response);
                                    if (jsonObj.getString("status").equalsIgnoreCase("ok")) {

                                        JSONArray js= jsonObj.getJSONArray("data");
                                        schedule_id=new String[js.length()];
                                        schedule_ftime=new String[js.length()];
                                        schedule_ttime=new String[js.length()];


                                        for(int i=0;i<js.length();i++)
                                        {
                                            JSONObject u=js.getJSONObject(i);
                                            schedule_id[i]=u.getString("slot");
                                            schedule_ftime[i]=u.getString("slot");
                                            schedule_ttime[i]=u.getString("slot");



                                        }

                                        ggv.setAdapter(new CustomAppointment(getApplicationContext(),schedule_id,schedule_ftime,schedule_ttime));


                                    }


                                    // }
//                                else {
//                                    Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_LONG).show();
//                                }

                                }    catch (Exception e) {
                                    Toast.makeText(getApplicationContext(), "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
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


                        params.put("dd",jk);
                        params.put("lid",sh.getString("doctor_id",""));

                        return params;
                    }
                };

                int MY_SOCKET_TIMEOUT_MS=100000;

                postRequest.setRetryPolicy(new DefaultRetryPolicy(
                        MY_SOCKET_TIMEOUT_MS,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(postRequest);




            }



        };

        bt6.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(Appointment.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();





            }
        });


    }





}

