package com.example.eswasthyasuraksha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DoctorPortal extends AppCompatActivity {

    Button b1,b2;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    public void logoutDr(View view) {
        firebaseAuth= FirebaseAuth.getInstance();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser!=null){
            firebaseAuth.signOut();
            Toast.makeText(this, "Successfully Logged Out!!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_doctor_portal);

        Intent intent=getIntent();
        b1=(Button)findViewById(R.id.cirButtonReg);
        b2=(Button)findViewById(R.id.cirButtonLogin);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),DoctorRegistration.class));
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent my_intent=new Intent(getApplicationContext(),DoctorLogin.class);
                startActivity(my_intent);
            }
        });
    }
}