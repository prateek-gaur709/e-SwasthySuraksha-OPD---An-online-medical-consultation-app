package com.example.eswasthyasuraksha;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class DoctorRegisterForm extends AppCompatActivity {

    EditText name,email,reg_no,mobile,password,chooseFiles;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    Button uploadDocuments,SubmitBtn,Okay_btn;
    String user_dr_ID;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    CardView cardView;


    private void chooseFileToUpload() {
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

        StorageReference reference=storageReference.child("Doctor_Uploads/"+System.currentTimeMillis()+".pdf");
        reference.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uri=taskSnapshot.getStorage().getDownloadUrl();
                        while (!uri.isComplete());
                        Uri url=uri.getResult();

                        uploadFile uploadFile=new uploadFile(chooseFiles.getText().toString(),url.toString());
                        databaseReference.child(databaseReference.push().getKey()).setValue(uploadFile);
                        Toast.makeText(DoctorRegisterForm.this, "File Uploaded", Toast.LENGTH_SHORT).show();
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
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_doctor_register_form);
        changeStatusBarColor();

        Intent intent=getIntent();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        cardView=(CardView)findViewById(R.id.cardview);


        storageReference= FirebaseStorage.getInstance().getReference();
        databaseReference= FirebaseDatabase.getInstance().getReference("Doctor_Uploads");

        name=(EditText)findViewById(R.id.editTextName);
        email=(EditText)findViewById(R.id.editTextEmail);
        reg_no=(EditText)findViewById(R.id.editTextReg);
        mobile=(EditText)findViewById(R.id.editTextMobile);
        password=(EditText)findViewById(R.id.editTextPassword);
        chooseFiles=(EditText)findViewById(R.id.choosefilesto);

        uploadDocuments=(Button)findViewById(R.id.cirUploadButton);
        SubmitBtn=(Button)findViewById(R.id.cirRegisterButton);
        Okay_btn=(Button)findViewById(R.id.cirOkayButton);

        SubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!name.getText().toString().isEmpty()) {
                    if(!email.getText().toString().isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                        if(!reg_no.getText().toString().isEmpty()) {
                            if(!mobile.getText().toString().isEmpty()) {
                                if(!password.getText().toString().isEmpty()) {
                                    user_dr_ID = firebaseAuth.getCurrentUser().getUid();
                                    DocumentReference documentReference = firebaseFirestore.collection("Doctors")
                                            .document(user_dr_ID);
                                    Map<String, Object> user = new HashMap<>();
                                    user.put("Name", name.getText().toString());
                                    user.put("Email", email.getText().toString());
                                    user.put("Registration Number", reg_no.getText().toString());
                                    user.put("Mobile", mobile.getText().toString());
                                    user.put("Password", password.getText().toString());

                                    documentReference.set(user)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.i("Msg", "Succesfully updated doctors records!");

                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.i("Msg", "Failed" + e.getMessage());
                                        }

                                    });
                                    cardView.setVisibility(View.VISIBLE);

                                }else {
                                    password.setError("Password is empty!");
                                }
                            }else {
                                mobile.setError("Mobile is empty!");
                            }
                        }else {
                            reg_no.setError("Registration No is empty!");
                        }
                    }
                    else {
                        email.setError("Email is not valid!");
                    }
                }else{
                    name.setError("Name is empty!");
                }


            }
        });
        uploadDocuments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseFileToUpload();

            }
        });

        Okay_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),DoctorLogin.class));
            }
        });
    }
    private void changeStatusBarColor() {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));

    }
}