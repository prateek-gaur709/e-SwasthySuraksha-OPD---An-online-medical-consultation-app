package com.example.eswasthyasuraksha;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    EditText patientId,token;
    String patient_Id,userId;
    FirebaseAuth firebaseAuth11;
    FirebaseFirestore firebaseFirestore11;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        firebaseAuth11=FirebaseAuth.getInstance();
        firebaseFirestore11=FirebaseFirestore.getInstance();

        String token2=getIntent().getStringExtra("Token");
        patientId=(EditText)findViewById(R.id.editTextpatientName);
        token=(EditText)findViewById(R.id.editTextToken);
        token.setText(token2);

        DocumentReference reference= FirebaseFirestore.getInstance().collection("Users").document(firebaseAuth11.getCurrentUser().getUid());
        reference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (documentSnapshot != null) {
                    patient_Id=documentSnapshot.getString("Name")+documentSnapshot.getString("Age");
                    Log.i("Msg2",patient_Id);
                }else{
                    Log.i("Msg2","Null snapshot!");
                }

            }
        });
    }
    public void VideoConference(View view) {

        if (!patientId.getText().toString().isEmpty()){
            if (!token.getText().toString().isEmpty()){
                if(patientId.getText().toString().equals(patient_Id)) {
                    Toast.makeText(LoginActivity.this, "Welcome To your dashboard!", Toast.LENGTH_SHORT).show();
                  Intent intent= new Intent(getApplicationContext(), VideoConferencing.class);
                  intent.putExtra("Token",token.getText().toString());
                  startActivity(intent);
                }else{
                    Toast.makeText(LoginActivity.this,"Wrong Patient Id entered!",Toast.LENGTH_LONG).show();
                }
            }else{
                token.setError("Empty token not allowed");
            }

        }else{
            patientId.setError("Empty Field is not allowed!");
        }


    }
}