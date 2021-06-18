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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class CardiacArrest extends AppCompatActivity {

    String pNum;
    private static final int REQUEST_CALL = 1;
    Button callBtn2;

    private void makePhoneCall(String number) {
        pNum=number;
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
                makePhoneCall(pNum);
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_cardiac_arrest);

        Intent intent=getIntent();
        callBtn2=(Button)findViewById(R.id.cirButtonAmb);
        callBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePhoneCall("10888");
            }
        });

        MediaController mc= new MediaController(this);
        //video1
        VideoView video=(VideoView) findViewById(R.id.videoView1);
        video.setVideoPath("android.resource://"+getPackageName()+"/"+R.raw.cprvideo);
        mc.setAnchorView(video);
        video.setMediaController(mc);
        video.start();

        //video2
        VideoView evideo=(VideoView) findViewById(R.id.videoView2);
        evideo.setVideoPath("android.resource://"+getPackageName()+"/"+R.raw.emergencypills);
        mc.setAnchorView(evideo);
        evideo.setMediaController(mc);
        evideo.start();


    }
}