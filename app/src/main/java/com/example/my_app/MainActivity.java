package com.example.my_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    EditText mailId,pwdId;
    Button signIn;
    TextView signUp;
    ProgressBar prog;
    FirebaseAuth mfirebaseAuth;
    ImageView hh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mailId=findViewById(R.id.Email0);
        pwdId=findViewById(R.id.Password0);
        signIn=findViewById(R.id.btn0);
        signUp=findViewById(R.id.signup);
        prog=findViewById(R.id.prog0);
        prog.setVisibility(View.GONE);
        mfirebaseAuth=FirebaseAuth.getInstance();


        signIn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String email=mailId.getText().toString();
                        String pwd=pwdId.getText().toString();
                        if(email.isEmpty()){
                            mailId.setError("Enter Mail!");
                            mailId.requestFocus();
                        }else if(pwd.isEmpty()){
                            pwdId.setError("Enter Password!");
                            pwdId.requestFocus();
                        }else  if(!(email.isEmpty() && pwd.isEmpty())){
                                    prog.setVisibility(View.VISIBLE);
                            mfirebaseAuth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if(task.isSuccessful()){
                                        if (mfirebaseAuth.getCurrentUser().isEmailVerified()){
                                            startActivity(new Intent(MainActivity.this,HomeActivity.class));
                                        }else {
                                            Toast.makeText(MainActivity.this,"Please verify your email address",Toast.LENGTH_LONG).show();
                                            mailId.setText("");
                                            pwdId.setText("");
                                            prog.setVisibility(View.GONE);
                                        }

                                    }else {
                                        Toast.makeText(MainActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                        mailId.setText("");
                                        pwdId.setText("");
                                        prog.setVisibility(View.GONE);
                                    }
                                }
                            });

                        }
                    }
                });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SingUpActivity.class));
            }
        });

    }
}