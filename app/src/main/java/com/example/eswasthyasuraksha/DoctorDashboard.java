package com.example.eswasthyasuraksha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class DoctorDashboard extends AppCompatActivity {

    FirebaseUser fireUser;
    FirebaseAuth fireAuth;
    FirebaseFirestore firestore;
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_dashboard);

        Intent intent=getIntent();
    }

    public void GotoVideoCall(View view) {
        startActivity(new Intent(getApplicationContext(),VideoConferencing.class));
    }
}