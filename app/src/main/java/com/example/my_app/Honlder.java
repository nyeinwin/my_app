package com.example.my_app;


import android.content.Context;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class Honlder extends RecyclerView.Adapter  {
    private  Context context;
    List<User> userList;


    public Honlder(List<User> userList) {
        this.userList = userList;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.data,parent,false);
        ViewHonlderClass viewHonlderClass= new ViewHonlderClass(view);
        return viewHonlderClass;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ViewHonlderClass viewHonlderClass=(ViewHonlderClass)holder;
                final User user=userList.get(position);
                viewHonlderClass.name.setText(user.getName());
                viewHonlderClass.phone.setText(user.getPhone());
                viewHonlderClass.log.setText(user.getLog());
    }

    @Override
    public int getItemCount() {

        return userList.size();
    }


    public class ViewHonlderClass extends RecyclerView.ViewHolder  {
    TextView name,phone,log;

    public ViewHonlderClass(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.name_tv);
        phone = itemView.findViewById(R.id.phone_tv);
        log = itemView.findViewById(R.id.log_tv);

        
        }

    }

}
