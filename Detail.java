package com.example.bodyhero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

public class Detail extends AppCompatActivity {

    TextView t1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        t1=(TextView)findViewById(R.id.textView9);

        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        t1.setText(sh.getString("d",""));
    }
}