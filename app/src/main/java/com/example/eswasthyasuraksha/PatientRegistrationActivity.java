package com.example.eswasthyasuraksha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class PatientRegistrationActivity extends AppCompatActivity {

    public Spinner spinnerState,spinnerSpeciality,OPD;
    RadioGroup radioGroup;
    RadioButton generalbtn,specialitybtn;
    LinearLayout specialityLayout;
    Button verifyMobile;
    TextView textView;
    FirebaseAuth fireAuth;
    FirebaseUser fUser;

    public void logout(View v){
        fireAuth=FirebaseAuth.getInstance();
        fUser=FirebaseAuth.getInstance().getCurrentUser();
        if(fUser!=null){
            fireAuth.signOut();
            Toast.makeText(this, "Successfully Logged Out!!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_registration);

        Intent intent=getIntent();

        spinnerState=findViewById(R.id.spinnerContainerState);
        spinnerSpeciality=findViewById(R.id.spinnerContainerSpeciality);
        OPD=findViewById(R.id.OPDSpinner);

        radioGroup=(RadioGroup)findViewById(R.id.radioGroup);
        generalbtn=findViewById(R.id.General);
        specialitybtn=findViewById(R.id.Speciality);
        specialityLayout=(LinearLayout)findViewById(R.id.linearLayout2);
        verifyMobile=(Button) findViewById(R.id.verifyMobileButton);

        String[] states={"--Select--","Andhra Pradesh","Arunachal Pradesh","Assam","Bihar","Chhattisgarh","Goa","Gujarat","Haryana","Himachal Pradesh","Jammu and Kashmir","Jharkhand","Karnataka","Kerala","Madhya Pradesh","Maharashtra","Manipur","Meghalaya","Mizoram","Nagaland","Odisha","Punjab","Rajasthan","Sikkim","Tamil Nadu","Telangana","Tripura","Uttarakhand","Uttar Pradesh","West Bengal","Andaman and Nicobar Islands","Chandigarh","Dadra and Nagar Haveli","Daman and Diu","Delhi","Lakshadweep","Puducherry"};

        //

        ArrayAdapter arrayAdapter=new ArrayAdapter(this, android.R.layout.simple_spinner_item,states);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerState.setAdapter(arrayAdapter);

        spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l){

                textView=(TextView)spinnerState.getSelectedView();
               String getStateName=textView.getText().toString();
                String[] OPDs={"--Select--",getStateName+" OPD"};
                ArrayAdapter<String> opdAdapter=new ArrayAdapter<String>(PatientRegistrationActivity.this, android.R.layout.simple_spinner_dropdown_item,OPDs);
                OPD.setAdapter(opdAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
               switch (i){
                   case 0:Log.i("RadioButton ","General Clicked");break;//Do later
                   case 1:SpecialityLayoutCreate();break;
               }
            }
        });

        verifyMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),AuthenticationActivity.class);
                startActivity(intent);
            }
        });

    }

    public void SpecialityLayoutCreate() {
        ArrayList<String> Specialityopds = new ArrayList<String>();
        Specialityopds.add("--Select--");
        Specialityopds.add("Orthopaedics");
        Specialityopds.add("Cardiology");
        Specialityopds.add("Pulmonary");
        Specialityopds.add("Dental");
        Specialityopds.add("General Medicine");
        Specialityopds.add("Orthopaedics");
        Specialityopds.add("Paediatrics");
        Specialityopds.add("General Surgery");
        ArrayAdapter arrayAdapter1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Specialityopds);
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSpeciality.setAdapter(arrayAdapter1);
    }
}