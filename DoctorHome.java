package com.example.bodyhero;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

public class DoctorHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ImageView im;
    TextView tvname ;

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_profile,R.id.nav_schedule, R.id.nav_doctors,R.id.nav_prescription,R.id.nav_prediction,R.id.nav_identification,R.id.nav_chat,R.id.nav_review,R.id.nav_dises,R.id.nav_logout)
                .setDrawerLayout(drawer)
                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setItemIconTintList(null);
        im=(ImageView)navigationView.getHeaderView(0).findViewById(R.id.imageView);
        tvname=(TextView)navigationView.getHeaderView(0).findViewById(R.id.textView);

        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        tvname.setText(sh.getString("name",""));
        String url="http://"+sh.getString("ip","")+":5000"+sh.getString("photo","");
        Picasso.with(getApplicationContext()).load(url).transform(new CircleTransform()).into(im);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.doctor_home, menu);
        return true;
    }


//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
//                || super.onSupportNavigateUp();
//    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.nav_profile){
            startActivity( new Intent(getApplicationContext(),DoctorProfile.class));
        }
        else if(id==R.id.nav_identification){
            startActivity(new Intent(getApplicationContext(),SymptomIdentification.class));
        }
        else if (id==R.id.nav_schedule){
            Intent i =new Intent(getApplicationContext(),DoctorVIewSchedule.class);
            startActivity(i);
        }
        else if (id==R.id.nav_dises){

            Intent i =new Intent(getApplicationContext(),ViewDisease.class);
            startActivity(i);

        }
        else if (id==R.id.nav_appointment){

            Intent i =new Intent(getApplicationContext(),DoctorViewAppointment.class);
            startActivity(i);

        } else if (id==R.id.nav_chat){

            Intent i =new Intent(getApplicationContext(),DocChatView.class);
            startActivity(i);

        }

        return true;
    }
}


