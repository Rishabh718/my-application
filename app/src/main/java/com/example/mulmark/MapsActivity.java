package com.example.mulmark;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    EditText lattitudeTxt,longitudeTxt;
    Button save;

    private DatabaseReference mUsers;
    Marker marker;

    List<UserInformation> venueList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        lattitudeTxt=findViewById(R.id.lattitude);
        longitudeTxt=findViewById(R.id.longitude);
        save=findViewById(R.id.saveBtn);

        venueList = new ArrayList<>();
        mUsers = FirebaseDatabase.getInstance().getReference("coordinates");
//        mUsers.push().setValue(marker);
    }

    public void saveToDatabase(View view){
        double latdb=Double.parseDouble(lattitudeTxt.getText().toString());
        double lngdb=Double.parseDouble(longitudeTxt.getText().toString());
//        HashMap<String,Object> map=new HashMap<>();
//        map.put("lattitude",latdb);
//        map.put("longitude",lngdb);
//        mUsers.updateChildren(map);

        UserInformation userInformation=new UserInformation(latdb,lngdb);
        mUsers.push().setValue(userInformation).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MapsActivity.this, "Added", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(MapsActivity.this,MapActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(MapsActivity.this, "OOOOps", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

//                for (DataSnapshot s : dataSnapshot.getChildren()) {
//                    UserInformation userInformation = s.getValue(UserInformation.class);
//
//                    venueList.add(userInformation);
//                    for (int i = 0; i < venueList.size(); i++) {
//                        LatLng latLng = new LatLng(userInformation.getLatitude(), userInformation.getLongitude());
//                        if (mMap != null) {
//                            marker = mMap.addMarker(new MarkerOptions().position(latLng));
//                        }
//                    }
//                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}