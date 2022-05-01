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

public class BookingDetails extends AppCompatActivity implements View.OnClickListener {
    TextView t1,t2,t3,t4,t5;
    Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);

        t1=(TextView)findViewById(R.id.textView27);
        t2=(TextView)findViewById(R.id.textView28);
        t3=(TextView)findViewById(R.id.textView29);
        t4=(TextView)findViewById(R.id.textView30);
        t5=(TextView)findViewById(R.id.textView31);
        bt=(Button)findViewById(R.id.button8);
        bt.setOnClickListener(this);
        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        final String hu = sh.getString("ip", "");
        String url = "http://" + hu + ":5000/and_docviewbookingdetails_post";

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

                                t1.setText(jsonObj.getString("patient_name"));
                                t2.setText(jsonObj.getString("patient_age"));
                                t3.setText(jsonObj.getString("patient_place"));
                                t4.setText(jsonObj.getString("patient_phone"));
                                t5.setText(jsonObj.getString("patient_email"));
//                                t6.setText(jsonObj.getString("phone"));
//                                t7.setText(jsonObj.getString("qualification"));
//                                t8.setText(jsonObj.getString("gender"));
//                                t9.setText(jsonObj.getString("dob"));



                            }


                            // }
                            else {
                                Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_LONG).show();
                            }

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

                String id=sh.getString("slot_id","");
                params.put("slot_id",id);
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



    @Override
    public void onClick(View v) {
        Intent i =new Intent(getApplicationContext(),DocViewSlots.class);
        startActivity(i);

    }
}