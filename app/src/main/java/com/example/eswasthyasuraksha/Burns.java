package com.example.eswasthyasuraksha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Burns extends AppCompatActivity {

    Button callFireBtn,callAmbBtn;
    private static final int REQUEST_CALL = 1;

    String pNumb="";
    private void makePhoneCall(String number) {
        pNumb=number;
        if (number.trim().length() > 0) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        } else {
            Toast.makeText(this, "Enter Phone Number", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall(pNumb);
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_burns);

        Intent intent=getIntent();

        callAmbBtn=(Button)findViewById(R.id.cirOkayBtnAmbulance);
        callFireBtn=(Button)findViewById(R.id.cirOkayBtnFire);
        callAmbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                makePhoneCall("108888");
            }
        });
        callFireBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                makePhoneCall("101111");
            }
        });
    }
}