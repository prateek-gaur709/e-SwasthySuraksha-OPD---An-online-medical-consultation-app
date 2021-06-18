package com.example.eswasthyasuraksha;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class patientProfileAuthCheck extends AppCompatActivity {

    EditText username;
    FirebaseFirestore firebaseFirestore0;
    FirebaseAuth firebaseAuth0;
    String user_Id,rtv_username;



    public void send(View v) {
        if(!username.getText().toString().isEmpty()) {
            if(username.getText().toString().equals(rtv_username)) {
                Intent intent = new Intent(getApplicationContext(), PatientProfileActivity.class);
                startActivity(intent);
            }else {
                username.setError("Invalid Username!");
            }
        }else {
            username.setError("Empty field!!");
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile_auth_check);

        Intent intent =getIntent();
        firebaseAuth0=FirebaseAuth.getInstance();
        firebaseFirestore0=FirebaseFirestore.getInstance();
        user_Id=firebaseAuth0.getCurrentUser().getUid();
        username=(EditText)findViewById(R.id.editTextUsername);

        DocumentReference reference=firebaseFirestore0.collection("Users").document(user_Id);
        reference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                rtv_username=value.getString("Name")+value.getString("Age");
            }
        });
    }
}