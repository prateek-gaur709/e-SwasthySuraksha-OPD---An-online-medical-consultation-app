package com.example.eswasthyasuraksha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;

public class WhatAreU extends AppCompatActivity {
    public void Registration(View v){
        Intent intent=new Intent(getApplicationContext(),PatientRegistrationActivity.class);
        startActivity(intent);

    }

    public void Login(View v){
        Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);

    }

    public void Profile(View v){
        Intent intent=new Intent(getApplicationContext(),patientProfileAuthCheck.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_what_are_u);

        Intent intent=getIntent();

        //Firebase
//        DatabaseReference dbref= FirebaseDatabase.getInstance().getReference();
//        Map<String,String> values=new HashMap<>();
////        values.put("name","Prateek");
//        dbref.push().setValue(values, new DatabaseReference.CompletionListener() {
//            @Override
//            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
//                if (error==null){
//                    Log.i("info","Save Successful");
//                }else {
//                    Log.i("info","Save Unsuccessful");
//                }
//            }
//        });





    }
}