package com.example.bodyhero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class Ratings extends AppCompatActivity implements View.OnClickListener {
    Button submitButton;
    EditText edt;
    RatingBar r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratings);
        edt = (EditText) findViewById(R.id.editTextTextPersonName10);
        r = (RatingBar) findViewById(R.id.simpleRatingBar);
        r.setOnClickListener(this);
        submitButton=(Button)findViewById(R.id.submitButton);
        submitButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
//        float rating=r.getRating();
        Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();

        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String hu = sh.getString("ip", "");
        String url = "http://" + hu + ":5000/and_review";

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
//
//                                String name22=jsonObj.getString("name");
//                                name.setText(name22);
//                                name.setTextColor(Color.BLACK);
//
//                                String email22=jsonObj.getString("email");
//                                email.setText(email22);
//                                email.setTextColor(Color.BLACK);
//
//                                String phn=jsonObj.getString("ph_no");
//                                contact.setText(phn);
//                                contact.setTextColor(Color.BLACK);
//

                                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                SharedPreferences.Editor ed = sh.edit();
//                                ed.putString("staff_id",jsonObj.getString("lid"));
                                Intent i = new Intent(getApplicationContext(), Ratings.class);
                                startActivity(i);

//                                ed.commit();

//                                String imge=jsonObj.getString("photo");
////                                SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                                String ip=sh.getString("ip","");
//                                String url="http://" + ip + ":5000"+imge;
//                                Picasso.with(getApplicationContext()).load(url). into(img);


                            } else {
                                Toast.makeText(getApplicationContext(), "Accepted Requests", Toast.LENGTH_LONG).show();
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
            protected java.util.Map<String, String> getParams() {
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                java.util.Map<String, String> params = new java.util.HashMap<String, String>();


                params.put("lid", sh.getString("lid", ""));
                params.put("review", edt.getText().toString());
                params.put("rating", String.valueOf(rating));

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