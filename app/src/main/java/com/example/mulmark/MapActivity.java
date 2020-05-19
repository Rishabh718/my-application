package com.example.mulmark;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Double latitude, longitude;
    String key;
    Marker marker;
    Map<String, String> mMarkerMap;
    List<UserInformation> userInformationsList;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        databaseReference = FirebaseDatabase.getInstance().getReference("c");
        userInformationsList = new ArrayList<>();
        databaseReference.push().setValue(marker);
        mMarkerMap = new HashMap<>();
        databaseReference.push().setValue(marker);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
//        googleMap.setOnMarkerClickListener((GoogleMap.OnMarkerClickListener) this);
        databaseReference = FirebaseDatabase.getInstance().getReference("coordinates");
        ValueEventListener listener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot s : dataSnapshot.getChildren()) {
                    String lat = s.child("latitude").getValue().toString();
                    String lng = s.child("longitude").getValue().toString();
                    UserInformation userInformation=s.getValue(UserInformation.class);
                    key=userInformation.getUid();

                    UserInformation user=dataSnapshot.getValue(UserInformation.class);
                    user.setUid(dataSnapshot.getKey());

                    Log.d("User key", s.getKey());


                    Log.d("User ref", s.getRef().toString());
                    Log.d("User val", s.getValue().toString());

                    double latitude = Double.parseDouble(lat);
                    double longitude = Double.parseDouble(lng);
                    LatLng loc = new LatLng(latitude, longitude);
                    googleMap.addMarker(new MarkerOptions().position(loc).title(s.getKey()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                UserInformation userInformation=new UserInformation();
                String uid = userInformation.getUid();

                Intent intent = new Intent(MapActivity.this, DescActivity.class);
                intent.putExtra("KEY",uid);
                startActivity(intent);
                return false;
            }
        });
    }
}