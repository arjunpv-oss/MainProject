package com.example.bodyhero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DoctorAddPrescription extends AppCompatActivity implements View.OnClickListener {
    TextView t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11;
    Button bt;

    String[] patient_name,patient_age,patient_phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_add_prescription);
        bt=(Button)findViewById(R.id.button3);
        t1=(TextView)findViewById(R.id.textView37);
        t2=(TextView)findViewById(R.id.textView38);
        t3=(TextView)findViewById(R.id.textView39);
//        t4=(TextView)findViewById(R.id.textView40);
        t5=(TextView)findViewById(R.id.pname);
        t6=(TextView)findViewById(R.id.presage);
        t7=(TextView)findViewById(R.id.presplace);
        t8=(TextView)findViewById(R.id.presphone);
        t9=(TextView)findViewById(R.id.textView73);
        t10=(TextView)findViewById(R.id.textView74);
        t11=(TextView)findViewById(R.id.textView75);

        bt.setOnClickListener(this);

        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        String hu = sh.getString("ip", "");

        t1.setText(sh.getString("doccc",""));
        t2.setText(sh.getString("dqualification",""));
        t3.setText(sh.getString("dphone",""));
//        t4.setText(sh.getString("d_email",""));
        t5.setText(sh.getString("patient_name",""));
        t6.setText(sh.getString("patient_age",""));
        t7.setText(sh.getString("patient_place",""));
        t8.setText(sh.getString("patient_phone",""));
        t9.setText(sh.getString("presdisease",""));
        t10.setText(sh.getString("pressymp",""));
        t11.setText(sh.getString("presmed",""));



    }




    @Override
    public void onClick(View v) {
        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        String hu = sh.getString("ip", "");
        String url = "http://" + hu + ":5000/and_patientviewprescription_post";



        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(DoctorAddPrescription.this, "===="+response, Toast.LENGTH_SHORT).show();

                        // response
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
//                                Toast.makeText(getApplicationContext(),"SuccessBookeddddddd",Toast.LENGTH_SHORT).show();
//                                Intent ij=new Intent(getApplicationContext(),DoctorViewAppointment.class);
//                                startActivity(ij);
                                String hu = sh.getString("ip", "");
                                String url = "http://" + hu + ":5000"+jsonObj.getString("data");

Intent bb=new Intent(Intent.ACTION_VIEW, Uri.parse(url));
startActivity(bb);
//                                Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();
                                 }
                                else {
                                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
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

                String id=sh.getString("lid","");
//                String plid=sh.getString("plid","");
                String appoint_id=sh.getString("appoint_id","");
//                String pres_date=sh.getString("pres_date","");


                params.put("lid",id);
                params.put("appoint_id",appoint_id);
                params.put("schedule_id",sh.getString("schedule_id",""));
//                params.put("pres_content",pres_content);
//                params.put("pres_date",pres_date);
//                params.put("pres_disease",pres_disease);
//                params.put("pres_medicine",pres_medicine);


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