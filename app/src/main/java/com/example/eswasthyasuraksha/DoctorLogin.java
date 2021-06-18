package com.example.eswasthyasuraksha;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;


public class DoctorLogin extends AppCompatActivity {

    FirebaseAuth firebaseAuth22;
    FirebaseFirestore firebaseFirestore22;
    EditText email,pass;
    String userID,rtv_email,rtv_pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //for changing status bar icon colors
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        setContentView(R.layout.activity_doctor_login);

        Intent intent = getIntent();
        firebaseAuth22 = FirebaseAuth.getInstance();
        firebaseFirestore22=FirebaseFirestore.getInstance();
        email=(EditText)findViewById(R.id.editTextEmail);
        pass=(EditText)findViewById(R.id.editTextPassword);
        userID=firebaseAuth22.getCurrentUser().getUid();

        DocumentReference reference=firebaseFirestore22.collection("Doctors").document(userID);
        reference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                rtv_email=value.getString("Email");
                rtv_pwd=value.getString("Password");
            }
        });

    }

    public void onLoginClick(View view) {
        startActivity(new Intent(getApplicationContext(), DoctorRegistration.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
    }


    public void LoginDr(View view) {
        if(!email.getText().toString().isEmpty()){
            if(!pass.getText().toString().isEmpty()){
                if(email.getText().toString().equals(rtv_email)) {
                    if(pass.getText().toString().equals(rtv_pwd)) {
                        startActivity(new Intent(getApplicationContext(), DoctorDashboard.class));
                    }else {
                        pass.setError("Incorrect password!");
                    }
                }else {
                    email.setError("Incorrect email");
                }
            }else {
                pass.setError("Password can not be empty!");
            }
        }else {
            email.setError("Email can not be empty!");
        }
    }
}
