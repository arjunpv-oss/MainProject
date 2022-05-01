package com.example.bodyhero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DoctorPrecriptionAdd extends AppCompatActivity implements View.OnClickListener {
    EditText ed1,ed2,ed3,ed4,ed5,ed6,ed7;
    Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_precription_add);

        bt=(Button)findViewById(R.id.button7);
        ed1=(EditText)findViewById(R.id.editTextTextPersonName3);
        ed2=(EditText)findViewById(R.id.editTextTextPersonName4);
        ed3=(EditText)findViewById(R.id.editTextTextPersonName5);
        ed4=(EditText)findViewById(R.id.editTextTextPersonName6);
        ed5=(EditText)findViewById(R.id.editTextTextPersonName7);
        ed6=(EditText)findViewById(R.id.editTextTextPersonName8);
        ed7=(EditText)findViewById(R.id.editTextTextPersonName9);
       bt.setOnClickListener(this);

        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        String hu = sh.getString("ip", "");

        ed1.setText(sh.getString("patient_name",""));
        ed2.setText(sh.getString("patient_age",""));
        ed3.setText(sh.getString("patient_place",""));
        ed4.setText(sh.getString("patient_email",""));



    }

    @Override
    public void onClick(View v) {
        Toast.makeText(getApplicationContext(),"hi",Toast.LENGTH_SHORT).show();
        final String pres_content = ed5.getText().toString();
        final String pres_disease = ed6.getText().toString();
        final String pres_medicine = ed7.getText().toString();


        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        String hu = sh.getString("ip", "");
        String url = "http://" + hu + ":5000/and_prescriptioncontent_post";



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
                                Toast.makeText(getApplicationContext(),"SuccessBookeddddddd",Toast.LENGTH_SHORT).show();
                                Intent ij=new Intent(getApplicationContext(),DoctorViewAppointment.class);
                                startActivity(ij);



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

                String id=sh.getString("lid","");
                String plid=sh.getString("plid","");
                String appoint_id=sh.getString("appoint_id","");
                String pres_date=sh.getString("pres_date","");


                params.put("lid",id);
                params.put("appoint_id",appoint_id);
                params.put("plid",plid);
                params.put("pres_content",pres_content);
                params.put("pres_date",pres_date);
                params.put("pres_disease",pres_disease);
                params.put("pres_medicine",pres_medicine);


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
}