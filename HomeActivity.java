package com.example.bodyhero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
EditText ed;
Button bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ed=(EditText)findViewById(R.id.editTextName);
        bt=(Button)findViewById(R.id.button);
ed.setText("192.168.43.3");
bt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor ed1=sh.edit();
        ed1.putString("ip",ed.getText().toString());
        ed1.commit();


        Intent ij=new Intent(getApplicationContext(),login.class);
        startActivity(ij);
    }
}