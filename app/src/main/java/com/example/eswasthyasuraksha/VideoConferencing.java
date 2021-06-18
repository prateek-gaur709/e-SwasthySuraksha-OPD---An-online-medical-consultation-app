package com.example.eswasthyasuraksha;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.VideoView;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;
import java.net.MalformedURLException;
import java.net.URL;

public class VideoConferencing extends AppCompatActivity {

    Button JoinNow;
    EditText secretCode1;
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_conferencing);

        Intent intent=getIntent();
        JoinNow=(Button)findViewById(R.id.buttonJoin);
        secretCode1=(EditText)findViewById(R.id.secretCode);

        URL serverurl;

        try {
            serverurl=new URL("https://meet.jit.si");

            JitsiMeetConferenceOptions doptions = new JitsiMeetConferenceOptions.Builder()
                    .setServerURL(serverurl)
                    .setWelcomePageEnabled(false)
                    .build();
            JitsiMeet.setDefaultConferenceOptions(doptions);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        JoinNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                        .setRoom(secretCode1.getText().toString())
                        .setWelcomePageEnabled(false)
                        .build();
                JitsiMeetActivity.launch(VideoConferencing.this,options);

            }
        });

        videoView=(VideoView)findViewById(R.id.videoDr);
        MediaController mc= new MediaController(this);
        //video1
        videoView.setVideoPath("android.resource://"+getPackageName()+"/"+R.raw.trimz);
        mc.setAnchorView(videoView);
        videoView.setMediaController(mc);
        videoView.start();




    }
}