package com.example.eswasthyasuraksha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import timber.log.Timber;

public class AuthenticationActivity extends AppCompatActivity {

    EditText phoneNumber2;
    ProgressBar progressBar;
    Button getOtpButton;
    FirebaseAuth fAuth;


    public void signIn(PhoneAuthCredential credential){
        Log.i("Msg","Sign in function accessed.");
        fAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Intent send_intent=new Intent(getApplicationContext(),OTPVerification.class);
                    startActivity(send_intent);
                }else {
                    Toast.makeText(AuthenticationActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendOtp(String pNumber) {
        Timber.i("Send otp function accessed.");

        PhoneAuthOptions options=PhoneAuthOptions.newBuilder(fAuth)
                .setPhoneNumber(pNumber)
                .setActivity(AuthenticationActivity.this)
                .setTimeout(40L,TimeUnit.SECONDS)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s,forceResendingToken);

                        progressBar.setVisibility(View.GONE);
                        getOtpButton.setVisibility(View.VISIBLE);
                        Toast.makeText(AuthenticationActivity.this, "OTP has been sent!", Toast.LENGTH_LONG).show();
                        Log.i("After Code Sent S",s);
                        Intent send_intent=new Intent(getApplicationContext(),OTPVerification.class);
                        send_intent.putExtra("auth",s);
                        send_intent.putExtra("PhoneNumber","+91"+pNumber);
                        startActivity(send_intent);
                    }
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        Log.i("Msg","Verification Completed!");
                        progressBar.setVisibility(View.GONE);
                        getOtpButton.setVisibility(View.VISIBLE);
                        signIn(phoneAuthCredential);
                    }
                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Log.i("Msg","Verification Failed!");
                        progressBar.setVisibility(View.GONE);
                        getOtpButton.setVisibility(View.VISIBLE);
                        Toast.makeText(AuthenticationActivity.this,"Failed"+ e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }).build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        Intent intent = getIntent();
        phoneNumber2 = (EditText) findViewById(R.id.editTextPhone);

        progressBar = findViewById(R.id.progressBar1);
        getOtpButton = (Button) findViewById(R.id.getOtp_button);
        fAuth = FirebaseAuth.getInstance();

        getOtpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!phoneNumber2.getText().toString().trim().isEmpty()) {
                    if ((phoneNumber2.getText().toString().trim()).length() == 10) {
                        progressBar.setVisibility(View.VISIBLE);
                        getOtpButton.setVisibility(View.INVISIBLE);

                        sendOtp("+91"+phoneNumber2.getText().toString());
                    } else {
                        phoneNumber2.setError("Enter all the 10 digits!");
                    }
                } else {
                    phoneNumber2.setError("This field is required!");
                }
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user=fAuth.getCurrentUser();
        if (user!=null){
            Intent send_intent=new Intent(getApplicationContext(),OTPVerification.class);
            send_intent.putExtra("PhoneNumber",phoneNumber2.getText().toString());
            startActivity(send_intent);
            finish();
        }
    }

}