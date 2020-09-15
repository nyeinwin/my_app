package com.example.my_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class SingUpActivity extends AppCompatActivity {
    EditText mailId,pwdId,name;
    Button SignUp;
    TextView SignIn;
    ImageView cv;
    FirebaseAuth mFirebaseAuth;
    ProgressBar progressBar;
    SharedPreferences sp;
    String namestr;
    Uri imageuri;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private  static  final int PICK_IMAGE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);
        name=findViewById(R.id.signName);
        mailId=findViewById(R.id.Email1);
        pwdId=findViewById(R.id.Password1);
        SignUp=findViewById(R.id.btn1);
        SignIn=findViewById(R.id.signin);
        cv=findViewById(R.id.choose);

        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReference();
        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Profile"),PICK_IMAGE);
            }
        });

        progressBar=findViewById(R.id.progressBar0);
        progressBar.setVisibility(View.GONE);
        sp = getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        mFirebaseAuth=FirebaseAuth.getInstance();
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email=mailId.getText().toString();
                final String pwd=pwdId.getText().toString();

                if(email.isEmpty()){
                    mailId.setError("Enter Mail!");
                    mailId.requestFocus();
                }else if(pwd.isEmpty()){
                    pwdId.setError("Enter Password!");
                    pwdId.requestFocus();
                }else  if(!(email.isEmpty() && pwd.isEmpty())){
                    namestr =name.getText().toString();
                    SharedPreferences.Editor editor=sp.edit();
                    editor.putString("mail",email);
                    editor.putString("name",namestr);
                    editor.commit();
                    progressBar.setVisibility(View.VISIBLE);
                    mFirebaseAuth.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()){
                                mFirebaseAuth.getCurrentUser().sendEmailVerification()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(SingUpActivity.this,
                                                            "Registered Successfully! Please check your email for verification",
                                                            Toast.LENGTH_LONG).show();
                                                    startActivity(new Intent(SingUpActivity.this,MainActivity.class));
                                                    mailId.setText("");
                                                    pwdId.setText("");
                                                }else {
                                                    Toast.makeText(SingUpActivity.this,
                                                            task.getException().getMessage(),
                                                            Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });

                            }else {
                                Toast.makeText(SingUpActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SingUpActivity.this,MainActivity.class));
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK){
            imageuri = data.getData();
            cv.setImageURI(imageuri);
            Uploadpicture();

        }
    }

    private void Uploadpicture() {
        final ProgressDialog pd=new ProgressDialog(this);
        pd.setTitle("Upload Image...");
        pd.show();
        //  final String ramdomkey= UUID.randomUUID().toString();
        StorageReference riversRef = storageReference.child("img");

        riversRef.putFile(imageuri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();
                        Snackbar.make(findViewById(android.R.id.content),"Image Upload.",Snackbar.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(),"Failed to Upload.",Toast.LENGTH_LONG).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                pd.setMessage("Percentage :" + (int) progressPercent + "%");
            }
        });
    }
}