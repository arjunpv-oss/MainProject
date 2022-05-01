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
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;
import static android.graphics.Color.green;

public class CustomAppointment extends BaseAdapter {
    String [] slot_id,slot_number,sts,book;
    private Context context;

    public CustomAppointment(Context applicationContext, String[] slot_id, String[]  slot_number,String[]  book)
    {
        this.context=applicationContext;
        this.slot_id=slot_id;
        this.slot_number=slot_number;
        this.book=book;

    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_custom_appointment);
//    }

    @Override
    public int getCount() {
        return slot_number.length;
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

            vv.setText(slot_number[position]);

            if(book[position].equalsIgnoreCase("filled"))
            {
                vv.setEnabled(false);
                vv.setTextColor(RED);
            }
            else {
                vv.setEnabled(true);
            }

            vv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor ed=sh.edit();

        ed.putString("slot_id", slot_id[position]);
        ed.commit();


        Toast.makeText(context, "-------"+slot_id[position], Toast.LENGTH_SHORT).show();
        Intent i=new Intent(context,AppointmentForm.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
                }
            });
//

            return gridView;
        }
    }


