package com.example.bodyhero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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

public class SymptomIdentification extends AppCompatActivity {
    ListView listView;
    String [] disease;
    Button bts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptom_identification);


        disease=new String[] {"itching"," skin_rash", " nodal_skin_eruptions", " dischromic _patches" ,
        " continuous_sneezing" ," shivering", " chills" ," watering_from_eyes",
        " stomach_pain", " acidity", " ulcers_on_tongue", " vomiting" ," cough",
        " chest_pain", " yellowish_skin", " nausea" ," loss_of_appetite",
        " abdominal_pain" ," yellowing_of_eyes" ," burning_micturition",
        " spotting_ urination" ," passage_of_gases", " internal_itching",
        " indigestion", " muscle_wasting" ," patches_in_throat" ," high_fever",
        " extra_marital_contacts", " fatigue", " weight_loss", " restlessness",
        " lethargy", " irregular_sugar_level" ," blurred_and_distorted_vision",
        " obesity" ," excessive_hunger", " increased_appetite"," polyuria",
        " sunken_eyes"," dehydration"," diarrhoea" ," breathlessness",
        " family_history" ," mucoid_sputum", " headache" ," dizziness",
        " loss_of_balance", " lack_of_concentration", " stiff_neck", " depression",
        " irritability" ," visual_disturbances"," back_pain"," weakness_in_limbs",
        " neck_pain"," weakness_of_one_body_side", " altered_sensorium",
        " dark_urine" ," sweating" ," muscle_pain", " mild_fever",
        " swelled_lymph_nodes", " malaise" ," red_spots_over_body", " joint_pain",
        " pain_behind_the_eyes" ," constipation" ," toxic_look_(typhos)",
        " belly_pain" ," yellow_urine" ," receiving_blood_transfusion",
        " receiving_unsterile_injections", " coma", " stomach_bleeding",
        " acute_liver_failure" ," swelling_of_stomach" ," distention_of_abdomen",
        " history_of_alcohol_consumption" ," fluid_overload", " phlegm",
        " blood_in_sputum", " throat_irritation", " redness_of_eyes",
        " sinus_pressure" ," runny_nose" ," congestion" ," loss_of_smell",
        " fast_heart_rate" ," rusty_sputum" ," pain_during_bowel_movements",
        " pain_in_anal_region" ," bloody_stool", " irritation_in_anus", " cramps",
        " bruising", " swollen_legs" ," swollen_blood_vessels",
        " prominent_veins_on_calf", " weight_gain" ," cold_hands_and_feets",
        " mood_swings" ," puffy_face_and_eyes" ," enlarged_thyroid",
        " brittle_nails" ," swollen_extremeties", " abnormal_menstruation",
        " muscle_weakness", " anxiety", " slurred_speech" ," palpitations",
        " drying_and_tingling_lips" ," knee_pain", " hip_joint_pain",
        " swelling_joints" ," painful_walking" ," movement_stiffness",
        " spinning_movements", " unsteadiness", " pus_filled_pimples" ," blackheads",
        " scurring" ," bladder_discomfort" ," foul_smell_of urine",
        " continuous_feel_of_urine" ," skin_peeling" ," silver_like_dusting",
        " small_dents_in_nails" ," inflammatory_nails" ," blister",
        " red_sore_around_nose" ," yellow_crust_ooze"};


        bts=(Button) findViewById(R.id.button2);
        listView=(ListView) findViewById(R.id.list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice,disease);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);


        bts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String result="";

                SparseBooleanArray checked = listView.getCheckedItemPositions();
                for (int i = 0; i < checked.size(); i++) {
                    if (checked.valueAt(i) == true) {
                        String tag = String.valueOf(listView.getItemAtPosition(checked.keyAt(i)));
                        result=result+tag+"#";
                    }
                }

                if(result.length()>0)
                {
                    SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                    String hu = sh.getString("ip", "192.168.239.33");
                    String url = "http://" + hu + ":5000/predict_symptombased_disease";



                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    String finalResult = result;
                    StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                                    // response
                                    try {
                                        JSONObject jsonObj = new JSONObject(response);
                                        if (jsonObj.getString("status").equalsIgnoreCase("ok")) {


                                            SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                            SharedPreferences.Editor ed=sh.edit();
                                            ed.putString("d",jsonObj.getString("disease"));
                                            ed.commit();

                                            Intent ins= new Intent(getApplicationContext(),Detail.class);
                                            startActivity(ins);



//                                            Toast.makeText(getApplicationContext(),"Predicted Disease is"+ jsonObj.getString("disease"),Toast.LENGTH_LONG).show();
                                        }
                                        else {
                                            Toast.makeText(getApplicationContext(), "Failed to predict disease. Try again later", Toast.LENGTH_LONG).show();
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
                            params.put("input", finalResult);

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
        });
    }
}