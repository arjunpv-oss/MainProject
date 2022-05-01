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
import android.widget.TextView;
import android.widget.Toast;

import static android.graphics.Color.RED;

public class CustomDocViewSlots extends BaseAdapter {

    String [] slot_id,slot_number,book;
    private Context context;

    public CustomDocViewSlots(Context applicationContext, String[] slot_id, String[]  slot_number,String[] book)
    {
        this.context=applicationContext;
        this.slot_id=slot_id;
        this.slot_number=slot_number;
        this.book=book;

    }


    @Override
    public int getCount() {
        return slot_id.length;
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
            gridView = inflator.inflate(R.layout.activity_custom_doc_view_slots, null);
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
            vv.setEnabled(true);
            vv.setTextColor(RED);
        }
        else {
            vv.setEnabled(false);
        }
        vv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor ed=sh.edit();

                ed.putString("slot_id", slot_id[position]);
                ed.commit();


                Toast.makeText(context, "-------"+slot_id[position], Toast.LENGTH_SHORT).show();
                Intent i=new Intent(context,BookingDetails.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
//

        return gridView;
    }
}
