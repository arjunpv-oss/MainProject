package com.example.bodyhero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

public class PatiProfile extends AppCompatActivity implements View.OnClickListener {

    ImageView img;
    TextView t1,t2,t3,t4,t5,t6,t7,t8;
    Button bt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pati_profile);

        img=(ImageView)findViewById(R.id.patimage);
        t1=(TextView)findViewById(R.id.pname);
        t2=(TextView)findViewById(R.id.page);
        t3=(TextView)findViewById(R.id.paplace);
        t4=(TextView)findViewById(R.id.ppost);
        t5=(TextView)findViewById(R.id.pemail);
        t6=(TextView)findViewById(R.id.pphone);
        t7=(TextView)findViewById(R.id.ppin);
        bt=(Button)findViewById(R.id.edit);

        bt.setOnClickListener(this);

        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        final String hu = sh.getString("ip", "");
        String url = "http://" + hu + ":5000/and_patprofile_post";



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

                                t1.setText(jsonObj.getString("name"));
                                t2.setText(jsonObj.getString("age"));
                                t3.setText(jsonObj.getString("place"));
                                t4.setText(jsonObj.getString("post"));
                                t5.setText(jsonObj.getString("email"));
                                t6.setText(jsonObj.getString("phone"));
                                t7.setText(jsonObj.getString("pin"));


                                String pic=  jsonObj.getString("photo");
                                String url1 = "http://" + hu + ":5000"+pic;

                                Picasso.with(getApplicationContext()).load(url1).transform(new CircleTransform()).into(img);



                                        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                        SharedPreferences.Editor ed1=sh.edit();
                                        ed1.putString("pname",jsonObj.getString("name"));
                                        ed1.putString("age",jsonObj.getString("age"));
                                        ed1.putString("place",jsonObj.getString("place"));
                                        ed1.putString("post",jsonObj.getString("post"));
                                        ed1.putString("email",jsonObj.getString("email"));
                                        ed1.putString("phonee",jsonObj.getString("phone"));
                                        ed1.putString("pinn",jsonObj.getString("pin"));
                                        ed1.putString("pic",jsonObj.getString("photo"));
                                        ed1.commit();




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

                String id=sh.getString("lid","");
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
    }

    @Override
    public void onClick(View v) {
        Intent i =new Intent(getApplicationContext(),EditProfile.class);
        startActivity(i);
    }
}
