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
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity implements View.OnClickListener {
    EditText ed1, ed2;
    Button bt1, bt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        bt1=(Button)findViewById(R.id.login_btn);
        bt2=(Button)findViewById(R.id.signUp);
        TextInputLayout use=(TextInputLayout)findViewById(R.id.username);
        ed1=use.getEditText();
        ed1.setText("deepu12345@gmail.com");
        TextInputLayout pw=(TextInputLayout)findViewById(R.id.password);
        ed2=pw.getEditText();
        bt1.setOnClickListener(this);
        ed2.setText("333553");
        bt2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==bt1)
        {
            final String username=ed1.getText().toString();
            final String password=ed2.getText().toString();


            SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

            String hu = sh.getString("ip", "");
            String url = "http://" + hu + ":5000/and_login_post";



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

                                    SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                    SharedPreferences.Editor ed=sh.edit();
                                    ed.putString("lid",jsonObj.getString("lid"));
                                    ed.putString("type",jsonObj.getString("type"));
                                    ed.commit();

                                    if(jsonObj.getString("type").equalsIgnoreCase("doctor")) {

                                        Intent ij = new Intent(getApplicationContext(), DocDashboard.class);
                                        startActivity(ij);
                                    }
                                    else{

                                        Intent ij = new Intent(getApplicationContext(), login.class);
                                        startActivity(ij);
                                    }
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

                    String id=sh.getString("uid","");
                    params.put("username",username);
               params.put("password",password);

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
        if(v==bt2) {
            Intent intent = new Intent(getApplicationContext(), PatientReg.class);
            startActivity(intent);
        }
    }
}