package com.example.eswasthyasuraksha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;


public class OTPVerification extends AppCompatActivity {

    EditText phoneNo,otpByUser;
    ProgressBar pB;
    Button verifybtn;
    String OTP;
    String phoneNumber;
    FirebaseAuth firebaseAuth;

    public void signIn(PhoneAuthCredential credential){
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Intent intent=new Intent(getApplicationContext(),PatientDetails.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(OTPVerification.this, "Verification Failed!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_o_t_p_verification);
        phoneNo=(EditText) findViewById(R.id.editTextPhone2);
        otpByUser=(EditText)findViewById(R.id.otpByUser);
        verifybtn=(Button)findViewById(R.id.getOtp_button);
        pB=findViewById(R.id.progressBar1);

        phoneNumber=getIntent().getStringExtra("PhoneNumber");
        OTP=getIntent().getStringExtra("auth");
        phoneNo.setText(phoneNumber);

        firebaseAuth=FirebaseAuth.getInstance();

      verifybtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              String verificationCode=otpByUser.getText().toString();
              if(!verificationCode.isEmpty()){
                  PhoneAuthCredential credential=PhoneAuthProvider.getCredential(OTP,verificationCode);
                  signIn(credential);
              }else{
                  otpByUser.setError("OTP field can't be empty!");
              }
          }
      });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser=firebaseAuth.getCurrentUser();
        if(currentUser!=null){
            Intent intent=new Intent(getApplicationContext(),PatientDetails.class);
            startActivity(intent);
        }

    }
}