package com.example.bodyhero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CustoemDocViewAppointment extends BaseAdapter {
    String[] appoint_id,plid,name,age,place,phone,email;
    private Context context;
    public CustoemDocViewAppointment(Context appcontext,String[] appoint_id,String[] plid,String[]name,String[]age,String[] place,String[] phone,String[] email)
    {
        this.context=appcontext;
        this.appoint_id=appoint_id;
        this.plid=plid;
        this.name=name;
        this.age=age;
        this.place=place;
        this.phone=phone;
        this.email=email;


    }


    @Override
    public int getCount() {
        return name.length;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gridView;
        if (convertView == null) {
            gridView = new View(context);
            gridView = inflator.inflate(R.layout.activity_custoem_doc_view_appointment, null);
        }
        else {
            gridView = (View) convertView;

        }
        TextView tv1 = (TextView) gridView.findViewById(R.id.textView32);
        TextView tv2 = (TextView) gridView.findViewById(R.id.textView33);
        TextView tv3 = (TextView) gridView.findViewById(R.id.textView34);
        TextView tv4 = (TextView) gridView.findViewById(R.id.textView35);
        TextView tv5 = (TextView) gridView.findViewById(R.id.textView36);





        tv1.setTextColor(Color.BLACK);//color setting
        tv3.setTextColor(Color.BLACK);



        tv2.setTextColor(Color.BLACK);
        tv4.setTextColor(Color.BLACK);
        tv5.setTextColor(Color.BLACK);


        tv1.setText(name[position]);
        tv2.setText(age[position]);
        tv3.setText(place[position]);
        tv4.setText(phone[position]);
        tv5.setText(email[position]);
//        tv4.setText(ftime[i]);



//        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
//        String ip=sh.getString("ip","");
//        String url="http://" + ip + ":5000"+photo[i];
//        Picasso.with(context).load(url). into(im);



        Button b1=(Button)gridView.findViewById(R.id.button9);
        b1.setTag(position);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos= (int) view.getTag();
                final String pat=plid[pos];
                SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor ed=sh.edit();

               ed.putString("plid",pat);
               ed.putString("appoint_id",appoint_id[pos]);
                ed.putString("patient_name",name[pos]);
                ed.putString("patient_age",age[pos]);
                ed.putString("patient_place",place[pos]);
                ed.putString("patient_email",email[pos]);
                ed.putString("patient_email",email[pos]);
                ed.commit();

                Toast.makeText(context, "===="+appoint_id[pos], Toast.LENGTH_SHORT).show();
                Intent i= new Intent(context, DoctorPrecriptionAdd.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);


            }
        });

//        Button b2=(Button)gridView.findViewById(R.id.button2);
//        b2.setTag(i);
//        b2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int pos= (int) view.getTag();
//                final String dlid=doctor_id[pos];
//                SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(context);
//                SharedPreferences.Editor ed=sh.edit();
//
//                ed.putString("doctor_id",dlid);
//                ed.putString("photo",photo[pos]);
//                ed.putString("name",name[pos]);
//                ed.putString("qualification",qualification[pos]);
//                ed.putString("schedule_id",schedule_id[pos]);
//                ed.commit();
//                Intent i= new Intent(context, Test.class);
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(i);
//
//
//            }
//        });



        return gridView;

    }
    }

