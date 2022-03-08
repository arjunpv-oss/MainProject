package com.example.bodyhero;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Custom_view_disease extends BaseAdapter {
    String [] disease_name,disease_description,disease_image;
    private Context context;

    public Custom_view_disease(Context appcontext,String[]disease_name,String[]disease_description,String[] disease_image)
    {
        this.context=appcontext;
        this.disease_name=disease_name;
        this.disease_image=disease_image;
        this.disease_description=disease_description;


    }

    @Override
    public int getCount() {
        return disease_description.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflator=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if(view==null)
        {
            gridView=new View(context);
            //gridView=inflator.inflate(R.layout.customview, null);
            gridView=inflator.inflate(R.layout.custom_view_disease,null);

        }
        else
        {
            gridView=(View)view;

        }
        TextView tv1=(TextView)gridView.findViewById(R.id.textView);
        TextView tv2=(TextView)gridView.findViewById(R.id.textView2);
        ImageView im=(ImageView) gridView.findViewById(R.id.imageView);

        tv1.setTextColor(Color.BLACK);
        tv2.setTextColor(Color.BLACK);

        tv1.setText(disease_name[i]);
        tv2.setText(disease_description[i]);
        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
        String ip=sh.getString("ip","");

        String url="http://" + ip + ":5000"+disease_image[i];


        Picasso.with(context).load(url). into(im);

        return gridView;
    }
}
