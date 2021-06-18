package com.example.eswasthyasuraksha;


import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    Button doctorBtn,patientbtn;
    ImageView splashIV;
    CardView cardViewmain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        patientbtn=(Button)findViewById(R.id.patientButton);
        doctorBtn=(Button)findViewById(R.id.doctorButton);
        splashIV=(ImageView) findViewById(R.id.imageViewSplash);
        cardViewmain=(CardView)findViewById(R.id.cardViewmain);

        Handler mhandler = new Handler();
        Runnable mRunnable = new Runnable() {
            @Override
            public void run() {

                splashIV.setVisibility(View.GONE);
                cardViewmain.setVisibility(View.VISIBLE);


            }
        };
        mhandler.postDelayed(mRunnable,4000);

        doctorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),DoctorPortal.class));
            }
        });
        patientbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(),EmergencyOrNot.class));
            }
        });




    }



}

