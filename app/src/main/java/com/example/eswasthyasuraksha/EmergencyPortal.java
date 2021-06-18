package com.example.eswasthyasuraksha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import java.util.Objects;

public class EmergencyPortal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_emergency_portal);

        Intent intent=getIntent();
    }
    public void RoadAccident(View view) {
        startActivity(new Intent(getApplicationContext(),Accident.class));
    }
    public void HeartAttack(View view) {
        startActivity(new Intent(getApplicationContext(),CardiacArrest.class));
    }
    public void Burns(View view) {
        startActivity(new Intent(getApplicationContext(),Burns.class));
    }



}