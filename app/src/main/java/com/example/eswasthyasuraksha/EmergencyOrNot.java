package com.example.eswasthyasuraksha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import java.util.Objects;

public class EmergencyOrNot extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_emergency_or_not);


        Intent intent=getIntent();
    }

    public void MoveToEmergencyPortal(View view) {
        startActivity(new Intent(getApplicationContext(),EmergencyPortal.class));
    }

    public void GotoNormalPortal(View view) {
        startActivity(new Intent(getApplicationContext(),WhatAreU.class));
    }
}