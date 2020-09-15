package com.example.my_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class New_User extends AppCompatActivity {
    EditText userId,phoneId,logId;
    Button btn;
    FirebaseDatabase database;
    DatabaseReference ref;
    int maxid=0;
    User nn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__user);
        userId=findViewById(R.id.member);
        phoneId=findViewById(R.id.memberPhone);
        btn=findViewById(R.id.memberbutton);
        logId=findViewById(R.id.log);
        (findViewById(R.id.imageView12)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(New_User.this,ProfileActivity.class));
            }
        });
        (findViewById(R.id.imageView13)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(New_User.this,Customer_List.class));
            }
        });
        (findViewById(R.id.imageView15)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(New_User.this,"Error",Toast.LENGTH_LONG).show();
            }
        });

        nn=new User();
        ref=database.getInstance().getReference().child("Users");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    maxid = (int) dataSnapshot.getChildrenCount();
                }else {
                    ///
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=userId.getText().toString();
                String phone=phoneId.getText().toString();
                String log=logId.getText().toString();
                if(name.isEmpty()){
                    userId.setError("Please enter name!");
                    userId.requestFocus();
                }else if(phone.isEmpty()) {
                    phoneId.setError("Please enter phone Number");
                    phoneId.requestFocus();
                }else if(log.isEmpty()) {
                    logId.setError("Please enter lotlog!");
                    logId.requestFocus();
                }else if (!(name.isEmpty() && phone.isEmpty())) {
                    nn.setName(userId.getText().toString());
                    nn.setPhone(phoneId.getText().toString());
                    nn.setLog(logId.getText().toString());
                    ref.child(String.valueOf(maxid + 1)).setValue(nn);
                    Toast.makeText(New_User.this, "successfully", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(New_User.this, Customer_List.class));
                }


            }
        });
    }
}