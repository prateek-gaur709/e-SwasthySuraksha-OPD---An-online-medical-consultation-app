package com.example.eswasthyasuraksha;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PatientDetails extends AppCompatActivity {

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    String name="";
    String radioText;
    String ageOfPerson;
    String pinCodeOfPerson;
    Spinner spinnerAge,spinnerDistrict,spinnerCity;
    Button generateToken;
    RadioGroup radioGroup;
    ConstraintLayout constraintLayout;
    EditText fname,lname,pincode,editTextDocs;
    Button uploadDocsbtn;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    String p_token="";
    CardView cardView123;

    public static String randomTokenGenerator(){
        String token=UUID.randomUUID()
                .toString()
                .replace("-","@");
        return token.substring(0, Math.min(token.length(),8));
    }

    public void okay(View v){
        Intent return_intent=new Intent(getApplicationContext(),LoginActivity.class);
        return_intent.putExtra("Token",p_token);
        startActivity(return_intent);
    }

    private void chooseFile() {
        Intent intent=new Intent();
        intent.setType("application/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select PDF File "),1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){

             uploadFile(data.getData());
        }

    }

    private void uploadFile(Uri data){
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Uploading your file....");
        progressDialog.show();

        StorageReference reference=storageReference.child("Uploads/"+System.currentTimeMillis()+".pdf");
        reference.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uri=taskSnapshot.getStorage().getDownloadUrl();
                        while (!uri.isComplete());
                        Uri url=uri.getResult();

                        uploadFile uploadFile=new uploadFile(editTextDocs.getText().toString(),url.toString());
                        databaseReference.child(databaseReference.push().getKey()).setValue(uploadFile);
                        Toast.makeText(PatientDetails.this, "File Uploaded", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {

                double progress=(taskSnapshot.getBytesTransferred()*100.0)/taskSnapshot.getTotalByteCount();
                progressDialog.setMessage("Uploaded : "+(int)progress +"%");


            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);

        Intent verify_intent=getIntent();
        spinnerAge=findViewById(R.id.spinnerAge);
        spinnerDistrict=findViewById(R.id.spinnerDistrict);
        spinnerCity=findViewById(R.id.spinnerCity);
       cardView123=findViewById(R.id.patientDetailsCard);
       constraintLayout=(ConstraintLayout)findViewById(R.id.constraintLay1);
        fname=(EditText)findViewById(R.id.editTextFname);
        lname=(EditText)findViewById(R.id.editTextLname);
        pincode=(EditText)findViewById(R.id.editTextPin);
        uploadDocsbtn=(Button)findViewById(R.id.uploadDocs);
        editTextDocs=(EditText)findViewById(R.id.editTextDocs);

        storageReference= FirebaseStorage.getInstance().getReference();
        databaseReference= FirebaseDatabase.getInstance().getReference("Uploads");

        generateToken=(Button) findViewById(R.id.GenerateTokenBtn);
        radioGroup=(RadioGroup)findViewById(R.id.radioGroup);

        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();

        ArrayList<Integer> age=new ArrayList<Integer>();
        for (int i=1;i<=100;i++){
            age.add(i);
        }
        ArrayAdapter arrayAdapter=new ArrayAdapter(this, android.R.layout.simple_spinner_item,age);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAge.setAdapter(arrayAdapter);



        spinnerAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
              ageOfPerson= spinnerAge.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                RadioButton rb=(RadioButton) findViewById(checkedId);
                radioText=rb.getText().toString();
            }
        });

        generateToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!fname.getText().toString().isEmpty()){
                    if(!lname.getText().toString().isEmpty()) {
                        if(!pincode.getText().toString().isEmpty()) {

                            name = fname.getText().toString() + lname.getText().toString();
                            Log.i("Msg", fname.getText().toString() + lname.getText().toString());

                            //
                            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            inputMethodManager.hideSoftInputFromWindow(lname.getWindowToken(), 0);
                            //

                            pinCodeOfPerson = pincode.getText().toString();

                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("Users")
                                    .document(userID);
                            Map<String, Object> user = new HashMap<>();
                            user.put("Name", name);
                            user.put("Gender", radioText);
                            user.put("Age", ageOfPerson);
                            user.put("District", null);
                            user.put("PinCode", pinCodeOfPerson);

                            documentReference.set(user)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.i("Msg", "Succesfully updated firebase");
                                            cardView123.setVisibility(View.VISIBLE);
                                            p_token = randomTokenGenerator();
                                            Log.i("Msg", p_token);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.i("Msg", "Failed" + e.getMessage());
                                }
                            });
                        }
                        else {
                            pincode.setError("Field cannot be empty!!");
                        }
                    }else {
                        lname.setError("Field cannot be empty!!");
                    }
            }else {
                    fname.setError("Field cannot be empty!!");
                }
        }});



        uploadDocsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseFile();
            }
        });




    }
}