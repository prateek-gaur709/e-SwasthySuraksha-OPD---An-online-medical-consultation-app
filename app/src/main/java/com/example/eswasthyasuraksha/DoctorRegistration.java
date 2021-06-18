package com.example.eswasthyasuraksha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class DoctorRegistration extends AppCompatActivity {

    EditText MobileNOEditText,OTPEditText;
    Button button,btnVerifyOtp;
    String auths;
    FrameLayout fL1,fL2;
    ProgressBar progressBarnew;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    public void signIn(PhoneAuthCredential credential){
        Log.i("Msg","Sign in function accessed.");
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Intent send_intent=new Intent(getApplicationContext(),DoctorRegisterForm.class);
                    startActivity(send_intent);
                }else {
                    Toast.makeText(DoctorRegistration.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void sendOtpToPhone(String pNumber) {
        Log.i("Msg","sendotp accessed ");
        PhoneAuthOptions options=PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber(pNumber)
                .setActivity(DoctorRegistration.this)
                .setTimeout(40L, TimeUnit.SECONDS)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s,forceResendingToken);

                        auths=s;
                        progressBarnew.setVisibility(View.GONE);
                        OTPEditText.setVisibility(View.VISIBLE);
                        fL1.setVisibility(View.GONE);
                        fL2.setVisibility(View.VISIBLE);
                        Toast.makeText(DoctorRegistration.this, "OTP has been sent!", Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        Log.i("Msg","Verification Completed!");

                        progressBarnew.setVisibility(View.GONE);
                        button.setVisibility(View.VISIBLE);
                        signIn(phoneAuthCredential);
                        startActivity(new Intent(getApplicationContext(),DoctorRegistration.class));
                    }
                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Log.i("Msg","Verification Failed!");
                        progressBarnew.setVisibility(View.GONE);
                        button.setVisibility(View.VISIBLE);
                        Toast.makeText(DoctorRegistration.this,"Failed"+ e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }).build();
        PhoneAuthProvider.verifyPhoneNumber(options);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_doctor_registration);

        MobileNOEditText=(EditText)findViewById(R.id.editTextMobileDr);
        OTPEditText=(EditText)findViewById(R.id.editTextOTP);
        button=(Button)findViewById(R.id.button56);
        btnVerifyOtp=(Button)findViewById(R.id.btnVerifyOtp);
        progressBarnew=findViewById(R.id.progressBarNew);
        firebaseAuth=FirebaseAuth.getInstance();

        fL1=(FrameLayout)findViewById(R.id.frameLayout1);
        fL2=(FrameLayout)findViewById(R.id.frameLayout2);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!(MobileNOEditText.getText().toString()).isEmpty()) {
                        if ((MobileNOEditText.getText().toString()).length() == 10) {

                            progressBarnew.setVisibility(View.VISIBLE);
                            button.setVisibility(View.INVISIBLE);
                            sendOtpToPhone("+91" + (MobileNOEditText.getText().toString()));
                            btnVerifyOtp.setVisibility(View.VISIBLE);

                        } else {
                            Toast.makeText(DoctorRegistration.this, "Pls enter all 10 digits", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(DoctorRegistration.this, "Phone Number is Empty", Toast.LENGTH_LONG).show();
                    }
                }
            });
                    btnVerifyOtp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (!OTPEditText.getText().toString().isEmpty() && !auths.isEmpty()){
                                PhoneAuthCredential credential=PhoneAuthProvider.getCredential(auths,OTPEditText.getText().toString());
                                signIn(credential);
                            }else {
                                Toast.makeText(DoctorRegistration.this, "Plz enter your OTP ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

    @Override
    protected void onStart() {

        super.onStart();
        FirebaseUser currentUser=firebaseAuth.getCurrentUser();
        if(currentUser!=null){
            Intent intent=new Intent(getApplicationContext(),DoctorRegisterForm.class);
            startActivity(intent);
        }

    }

}