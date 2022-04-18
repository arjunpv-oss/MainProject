package com.example.bodyhero;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DocSchedule extends AppCompatActivity {
    TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_schedule);
        tv1=(TextView)findViewById(R.id.textView16);
        tv2=(TextView)findViewById(R.id.textView17);
        tv3=(TextView)findViewById(R.id.textView18);
        tv4=(TextView)findViewById(R.id.textView19);
        tv5=(TextView)findViewById(R.id.textView20);
        tv6=(TextView)findViewById(R.id.textView21);
        tv7=(TextView)findViewById(R.id.textView22);
    }
}