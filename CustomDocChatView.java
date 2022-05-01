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

public class CustomDocChatView extends BaseAdapter {

    SharedPreferences sh;
    String [] patient_id;
    String [] name;
//    String [] email;
    String [] photo;


    private Context context;
    public CustomDocChatView(Context applicationContext, String[] patient_id, String[] photo, String[] name){
        this.context=applicationContext;
        this.patient_id=patient_id;
        this.name=name;
        this.photo=photo;
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
            gridView = inflator.inflate(R.layout.activity_custom_doc_chat_view, null);
        }
        else {
            gridView = (View) convertView;

        }
        TextView tv1 = (TextView) gridView.findViewById(R.id.textView76);

        ImageView im=(ImageView)  gridView.findViewById(R.id.imageView6);

        Toast.makeText(context, "eeeeeeeeeeeeeeee", Toast.LENGTH_SHORT).show();


        tv1.setTextColor(Color.BLACK);//color setting
        tv1.setText(name[position]);
//        tv2.setText(qualification[position]);
//        tv3.setText("Timing:");
////        tv4.setText(ftime[i]);
//        tv5.setText(ftime[position]);
//        tv6.setText(ttime[position]);


        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
        String ip=sh.getString("ip","");
        String url="http://" + ip + ":5000"+photo[position];
        Picasso.with(context).load(url). into(im);



//


        return gridView;

    }
}