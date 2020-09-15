package com.example.my_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class HomeActivity extends AppCompatActivity  {
ImageView profile,search,list;
GoogleMap mapAPI;
SupportMapFragment mapFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        profile=findViewById(R.id.imageView02);
        search=findViewById(R.id.imageView5);
        list=findViewById(R.id.imageView03);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,ProfileActivity.class));
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this,"trying",Toast.LENGTH_LONG).show();
            }
        });
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,Customer_List.class));
            }
        });

//        mapFragment=(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapApi);
//        mapFragment.getMapAsync(this);
    }

//    @Override
//    public void onMapReady(GoogleMap googleMap) {
////        mapAPI=googleMap;
////        LatLng aungpan=new LatLng(20.663687, 96.636873);
////        mapAPI.addMarker(new MarkerOptions().position(aungpan).title("aungpan"));
////        mapAPI.moveCamera(CameraUpdateFactory.newLatLng(aungpan));
//
//    }
}