//package com.example.bodyhero;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.preference.PreferenceManager;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import com.squareup.picasso.Picasso;
//
//public class CustomIdentification extends BaseAdapter {
//
//
//    String[] Sno,Name,Image,Symptoms,Causes,Description;
//    Context context;
//
//    public CustomIdentification (Context appcontext,String[] Sno,String[] Name){
//
//
//
//
//    }
//
//    @Override
//    public int getCount() {
//        return 0;
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return null;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return 0;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        LayoutInflater inflator=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//        View gridView;
//        if(view==null)
//        {
//            gridView=new View(context);
//            //gridView=inflator.inflate(R.layout.customview, null);
//            gridView=inflator.inflate(R.layout.custom_view_disease,null);
//
//        }
//        else
//        {
//            ListView=(View)view;
//
//        }
//        TextView tv1=(TextView)ListView.findViewById(R.id.textView);
//        TextView tv2=(TextView)gridView.findViewById(R.id.textView2);
//        TextView tvreadmore=(TextView)gridView.findViewById(R.id.textView4);
//        ImageView im=(ImageView) gridView.findViewById(R.id.imageView);
//        tvreadmore.setTag(i);
//        tvreadmore.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int pos= (int) v.getTag();
//                SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
//                SharedPreferences.Editor ed1=sh.edit();
//                ed1.putString("disease_image",disease_image[pos]);
//                ed1.putString("disease_name",disease_name[pos]);
//                ed1.putString("disease_description",disease_description[pos]);
//                ed1.commit();
//
//
//                Intent i =new Intent(context,viewmore.class);
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(i);
//            }
//        });
//
//        tv1.setTextColor(Color.BLACK);
//        tv2.setTextColor(Color.BLACK);
//
//        tv1.setText(disease_name[i]);
//        tv2.setText(disease_description[i]);
//        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
//        String ip=sh.getString("ip","");
//
//        String url="http://" + ip + ":5000"+disease_image[i];
//
//
//        Picasso.with(context).load(url). into(im);
//
//        return gridView;
//    }
//}