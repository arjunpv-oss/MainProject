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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class CustomeDocViewSchedule extends BaseAdapter {
    String[] date,ftime,ttime;
    private Context context;

    public CustomeDocViewSchedule(Context appcontext,String[]date,String[]ftime,String[] ttime)
    {
        this.context=appcontext;
        this.date=date;
        this.ftime=ftime;
        this.ttime=ttime;


    }




    @Override
    public int getCount() {
        return ftime.length;
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
            gridView = inflator.inflate(R.layout.activity_custome_doc_view_schedule, null);
        }
        else {
            gridView = (View) convertView;

        }
        TextView tv1 = (TextView) gridView.findViewById(R.id.textView12);
        TextView tv2 = (TextView) gridView.findViewById(R.id.textView13);
        TextView tv3 = (TextView) gridView.findViewById(R.id.textView10);





        tv1.setTextColor(Color.BLACK);//color setting
        tv3.setTextColor(Color.BLACK);



        tv2.setTextColor(Color.BLACK);


        tv1.setText(date[position]);
        tv2.setText(ftime[position]);
        tv3.setText(ttime[position]);
//        tv4.setText(ftime[i]);



//        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
//        String ip=sh.getString("ip","");
//        String url="http://" + ip + ":5000"+photo[i];
//        Picasso.with(context).load(url). into(im);


        Button b1=(Button)gridView.findViewById(R.id.button);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

                Toast.makeText(context, "999999999999999999999999999999999999999999999999999", Toast.LENGTH_SHORT).show();
                Intent i= new Intent(context, DocViewSlots.class);
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
