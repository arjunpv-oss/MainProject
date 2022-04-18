package com.example.bodyhero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CustomPatientViewDoc extends  BaseAdapter {
    SharedPreferences sh;
    String [] doctor_id,name,email,photo,qualification,experience,ftime,ttime;
    Button bt1, bt2;


    private Context context;
    public CustomPatientViewDoc(Context applicationContext, String[] staff_id, String[] photo, String[] name, String[] qualification, String[] experience, String[] fromtime, String[] totime){
        this.context=applicationContext;
        this.doctor_id=staff_id;
        this.name=name;
        this.photo=photo;
        this.qualification=qualification;
        this.ftime=fromtime;
        this.ttime=totime;




    }



    @Override
    public int getCount() {
        return doctor_id.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gridView;
        if (view == null) {
            gridView = new View(context);
            gridView = inflator.inflate(R.layout.activity_custom_patient_view_doc, null);
        }
        else {
            gridView = (View) view;

        }
        TextView tv1 = (TextView) gridView.findViewById(R.id.textView11);
        TextView tv2 = (TextView) gridView.findViewById(R.id.textView12);
        TextView tv3 = (TextView) gridView.findViewById(R.id.textView15);
        TextView tv4 = (TextView) gridView.findViewById(R.id.textView14);
        TextView tv5 = (TextView) gridView.findViewById(R.id.textView13);
        TextView tv6 = (TextView) gridView.findViewById(R.id.textView10);
        ImageView im=(ImageView)  gridView.findViewById(R.id.imageView3);



        tv1.setTextColor(Color.BLACK);//color setting
        tv3.setTextColor(Color.BLACK);
        tv5.setTextColor(Color.BLACK);
        tv6.setTextColor(Color.BLACK);

        tv2.setTextColor(Color.BLACK);


        tv1.setText(name[i]);
        tv2.setText(qualification[i]);
        tv3.setText("Timing:");
//        tv4.setText(ftime[i]);
        tv5.setText(ftime[i]);
        tv6.setText(ttime[i]);


        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
        String ip=sh.getString("ip","");
        String url="http://" + ip + ":5000"+photo[i];
        Picasso.with(context).load(url). into(im);


        Button b1=(Button)gridView.findViewById(R.id.button);
        b1.setTag(i);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos= (int) view.getTag();
                final String dlid=doctor_id[pos];
                SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor ed=sh.edit();

                ed.putString("doctor_id",dlid);
                ed.putString("photo",photo[pos]);
                ed.putString("name",name[pos]);
                ed.putString("qualification",qualification[pos]);
                ed.commit();
                Intent i= new Intent(context, Appointment.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);


            }
        });

        Button b2=(Button)gridView.findViewById(R.id.button2);
        b2.setTag(i);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos= (int) view.getTag();
                final String dlid=doctor_id[pos];
                SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor ed=sh.edit();

                ed.putString("doctor_id",dlid);
                ed.putString("photo",photo[pos]);
                ed.putString("name",name[pos]);
                ed.putString("qualification",qualification[pos]);
                ed.commit();
                Intent i= new Intent(context, Test.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);


            }
        });



        return gridView;

    }
}