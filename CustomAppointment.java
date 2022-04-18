package com.example.bodyhero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class CustomAppointment extends BaseAdapter {
    String [] schedule_id,schedule_ftime,schedule_ttime;
    private Context context;

    public CustomAppointment(Context applicationContext, String[] schedule_id, String[] schedule_ftime, String[] schedule_ttime)
    {
        this.context=applicationContext;
        this.schedule_id=schedule_id;
        this.schedule_ftime=schedule_ftime;
        this.schedule_ttime=schedule_ttime;
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_custom_appointment);
//    }

    @Override
    public int getCount() {
        return schedule_ftime.length;
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
        LayoutInflater inflator=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gridView;
        if(convertView==null) {
            gridView = new View(context);
            //gridView=inflator.inflate(R.layout.customview, null);
            gridView = inflator.inflate(R.layout.activity_custom_appointment, null);
        }
             else
            {
                gridView=(View)convertView;

            }
            TextView vv=(TextView) gridView.findViewById(R.id.textView23);

//
            vv.setText(schedule_ftime[position]);
//

            return gridView;
        }
    }


