package com.example.bodyhero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AppointmentForm extends AppCompatActivity implements View.OnClickListener {
    TextView t1;
    Button b1, b2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_form);
        t1 = (TextView) findViewById(R.id.textView24);
        b1 = (Button) findViewById(R.id.button5);
        b2 = (Button) findViewById(R.id.button4);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == b2) {

            Intent ij = new Intent(getApplicationContext(), Appointment.class);
            startActivity(ij);
        } else if (v==b1){

            SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

            String hu = sh.getString("ip", "");
            String url = "http://" + hu + ":5000/and_confirmbook_post";

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

                                    SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                    SharedPreferences.Editor ed = sh.edit();
//                                    ed.putString("prid", jsonObj.getString("prid"));
//                                    ed.putString("name", jsonObj.getString("name"));
//                                    ed.putString("photo", jsonObj.getString("photo"));
//                                    ed.commit();
                                    Toast.makeText(getApplicationContext(), "Booked", Toast.LENGTH_SHORT).show();

                                    Intent ii = new Intent(getApplicationContext(), Success.class);
                                    startActivity(ii);
//                                    Intent i = new Intent(getApplicationContext(), Home.class);
//                                    startActivity(i);
                                }


                                // }
                                else {
                                    Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
                                }

                            } catch (Exception e) {
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

                    String id1 = sh.getString("doctor_id", "");
                    String id2 = sh.getString("lid", "");
                    String id3 = sh.getString("schedule_id", "");
                    String id4 = sh.getString("slot_id", "");
//                    String id5 = sh.getString("uid", "");

//                    String hu = sh.getString("prid", "");
//
                    params.put("doctor_id", id1);
                    params.put("lid", id2);
                    params.put("schedule_id", id3);
                    params.put("slot_id", id4);

                    return params;
                }
            };

            int MY_SOCKET_TIMEOUT_MS = 100000;

            postRequest.setRetryPolicy(new DefaultRetryPolicy(
                    MY_SOCKET_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(postRequest);


        }
    }
}