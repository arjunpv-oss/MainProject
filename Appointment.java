package com.example.bodyhero;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
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
    String [] slot_id,slot_number,book;
    Button bt6;
    GridView ggv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        bt6=(Button) findViewById(R.id.button6);
        ggv=(GridView) findViewById(R.id.gv);
        ggv.setVisibility(View.VISIBLE);
        
//        ggv.setOnItemClickListener(this);

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
                                slot_id=new String[js.length()];
                                slot_number=new String[js.length()];
                                book=new String[js.length()];
//                                schedule_ttime=new String[js.length()];


                                for(int i=0;i<js.length();i++)
                                {
                                    JSONObject u=js.getJSONObject(i);
                                    slot_id[i]=u.getString("slot_id");
                                    slot_number[i]=u.getString("slot_number");
                                    book[i]=u.getString("book");
//                                    schedule_ttime[i]=u.getString("slot");



                                }

                                ggv.setAdapter(new CustomAppointment(getApplicationContext(),slot_id,slot_number,book));


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
                String sid=sh.getString("schedule_id","");
                params.put("lid",id);
                params.put("schedule_id",sid);
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







    }


//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        SharedPreferences.Editor ed=sh.edit();
//
//        ed.putString("slot_id", slot_id[position]);
//        ed.commit();
//
//
//        Toast.makeText(this, "-------"+slot_id[position], Toast.LENGTH_SHORT).show();
//        startActivity(new Intent(getApplicationContext(),AppointmentForm.class));
//    }
}

