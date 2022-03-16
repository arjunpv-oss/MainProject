package com.example.bodyhero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class PatientReg extends AppCompatActivity implements View.OnClickListener {
    ImageView img;
    EditText ed1,ed2,ed3,ed4,ed5,ed6,ed7,ed8;
    RadioButton r1,r2,r3;
    Button bt1,bt2;


    String path, atype, fname, attach, attatch1;
    byte[] byteArray = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_reg);

        img=(ImageView)findViewById(R.id.imageView2);
        TextInputLayout fnm=(TextInputLayout)findViewById(R.id.fullname);
        ed1=fnm.getEditText();
        TextInputLayout age=(TextInputLayout)findViewById(R.id.age);
        ed2=age.getEditText();
        TextInputLayout place=(TextInputLayout)findViewById(R.id.place);
        ed3=place.getEditText();
        TextInputLayout post=(TextInputLayout)findViewById(R.id.post);
        ed4=post.getEditText();
        TextInputLayout pin=(TextInputLayout)findViewById(R.id.pincode);
        ed5=pin.getEditText();
        TextInputLayout phone=(TextInputLayout)findViewById(R.id.phno);
        ed6=phone.getEditText();
        TextInputLayout email=(TextInputLayout)findViewById(R.id.email);
        ed7=email.getEditText();
        TextInputLayout pass=(TextInputLayout)findViewById(R.id.pswd);
        ed8=pass.getEditText();
        r1=(RadioButton)findViewById(R.id.radioMale);

        r2=(RadioButton)findViewById(R.id.radioFemale);

        r3=(RadioButton)findViewById(R.id.radioOthers);

        bt1=(Button)findViewById(R.id.reg);
    //    bt2=(Button)findViewById(R.id.reg_log);

        bt1.setOnClickListener(this);
      //  bt2.setOnClickListener(this);
        img.setOnClickListener(this);
    }
String gender="";
    @Override
    public void onClick(View v) {
        if(v==bt1) {
            final String name = ed1.getText().toString();
            final String age = ed2.getText().toString();
            final String place = ed3.getText().toString();
            final String post = ed4.getText().toString();
            final String pincode = ed5.getText().toString();
            final String phno = ed6.getText().toString();
            final String email = ed7.getText().toString();
            final String password = ed8.getText().toString();
            if (r1.isChecked()) {
                gender = "Male";
            } else if (r2.isChecked()) {
                gender = "Female";
            } else {
                gender = "Others";
            }
            SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

            String hu = sh.getString("ip", "");
            String url = "http://" + hu + ":5000/and_patientregistration_post";



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



                                        Intent ij = new Intent(getApplicationContext(), login.class);
                                        startActivity(ij);
                                    
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
                    params.put("name",name);
                    params.put("gender",gender);
                    params.put("age",age);
                    params.put("place",place);
                    params.put("pin",pincode);
                    params.put("post",post);
                    params.put("email",email);
                    params.put("phone",phno);
                    params.put("password",password);
                    params.put("photo",attach);
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
        else{

showfilechooser(1);
        }
    }


    void showfilechooser(int string) {
        // TODO Auto-generated method stub
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        //getting all types of files

        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), string);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(getApplicationContext(), "Please install a File Manager.", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                ////
                Uri uri = data.getData();

                try {
                    path = FileUtils.getPath(this, uri);

                    File fil = new File(path);
                    float fln = (float) (fil.length() / 1024);
                    atype = path.substring(path.lastIndexOf(".") + 1);


                    fname = path.substring(path.lastIndexOf("/") + 1);
                   // ed15.setText(fname);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }

                try {

                    File imgFile = new File(path);

                    if (imgFile.exists()) {

                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                      img.setImageBitmap(myBitmap);

                    }


                    File file = new File(path);
                    byte[] b = new byte[8192];
                    Log.d("bytes read", "bytes read");

                    InputStream inputStream = new FileInputStream(file);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();

                    int bytesRead = 0;

                    while ((bytesRead = inputStream.read(b)) != -1) {
                        bos.write(b, 0, bytesRead);
                    }
                    byteArray = bos.toByteArray();

                    String str = Base64.encodeToString(byteArray, Base64.NO_WRAP);
                    attach = str;


                } catch (Exception e) {
                    Toast.makeText(this, "String :" + e.getMessage().toString(), Toast.LENGTH_LONG).show();
                }

                ///

            }
        }

    }

}