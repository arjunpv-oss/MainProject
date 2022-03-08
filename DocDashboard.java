package com.example.bodyhero;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DocDashboard extends AppCompatActivity implements View.OnClickListener {
    CardView c1,c2,c3,c4,c5,c6,c7,c8,c9,c10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_dashboard);

        c1=(CardView)findViewById(R.id.prof);
        c2=(CardView)findViewById(R.id.dis);
        c3=(CardView)findViewById(R.id.sched);
        c4=(CardView)findViewById(R.id.pred);
        c5=(CardView)findViewById(R.id.iden);
        c6=(CardView)findViewById(R.id.appoint);
        c7=(CardView)findViewById(R.id.chat);
        c8=(CardView)findViewById(R.id.presc);
        c9=(CardView)findViewById(R.id.review);
        c10=(CardView)findViewById(R.id.logt);
        c1.setOnClickListener(this);
        c2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==c1)
        {
            Intent ij=new Intent(getApplicationContext(),DoctorProfile.class);
            startActivity(ij);
        }

        if(v==c2)
        {
            Intent ij=new Intent(getApplicationContext(),ViewDisease.class);
            startActivity(ij);
        }
    }
}