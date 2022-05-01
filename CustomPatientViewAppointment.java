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

public class CustomPatientViewAppointment extends BaseAdapter {
    String[] date,d_name,demail,slot,time,schedule_id,doctor_id,d_qualification,d_phone,pres_content,pres_disease,pres_medicine;
    private Context context;

    public CustomPatientViewAppointment(Context appcontext,String[] date, String[]d_name,String[]d_qualification,String[]d_phone, String[]demail,String[] slot, String[] time, String[] doctor_id,String[] pres_content,String[] pres_disease,String[] pres_medicine,String[] schedule_id)
    {
        this.context=appcontext;
        this.date=date;
        this.d_name=d_name;
        this.d_qualification=d_qualification;
        this.d_phone=d_phone;
        this.demail=demail;
        this.slot=slot;
        this.time=time;
        this.pres_content=pres_content;
        this.pres_disease=pres_disease;
        this.pres_medicine=pres_medicine;
        this.schedule_id=schedule_id;
        this.doctor_id=doctor_id;


    }


    @Override
    public int getCount() {
        return date.length;
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
            gridView = inflator.inflate(R.layout.activity_custom_patient_view_appointment, null);
        }
        else {
            gridView = (View) convertView;

        }
        TextView tv1 = (TextView) gridView.findViewById(R.id.textView58);
        TextView tv2 = (TextView) gridView.findViewById(R.id.textView51);
        TextView tv3 = (TextView) gridView.findViewById(R.id.textView53);
        TextView tv4 = (TextView) gridView.findViewById(R.id.textView55);
        TextView tv5 = (TextView) gridView.findViewById(R.id.textView57);





        tv1.setTextColor(Color.BLACK);//color setting
        tv3.setTextColor(Color.BLACK);
        tv4.setTextColor(Color.BLACK);
        tv5.setTextColor(Color.BLACK);



        tv2.setTextColor(Color.BLACK);


        tv1.setText(date[position]);
        tv2.setText(d_name[position]);
        tv3.setText(demail[position]);
        tv4.setText(slot[position]);
        tv5.setText(time[position]);



//        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
//        String ip=sh.getString("ip","");
//        String url="http://" + ip + ":5000"+photo[i];
//        Picasso.with(context).load(url). into(im);


        Button b1=(Button)gridView.findViewById(R.id.button);
        b1.setTag(position);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos= (int) view.getTag();
                final String dlid=doctor_id[pos];
                SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor ed=sh.edit();

                ed.putString("doctor_id",dlid);
                ed.putString("doccc",d_name[pos]);
                ed.putString("dqualification",d_qualification[pos]);
                ed.putString("dphone",d_phone[pos]);
                ed.putString("presdisease",pres_content[pos]);
                ed.putString("pressymp",pres_disease[pos]);
                ed.putString("presmed",pres_medicine[pos]);
                ed.putString("schedule_id",schedule_id[pos]);
                ed.commit();

                Toast.makeText(context, "====", Toast.LENGTH_SHORT).show();
                Intent i= new Intent(context, DoctorAddPrescription.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);


            }
        });
        return gridView;
    }
}