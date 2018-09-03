package com.example.mapstaskapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        ButterKnife.bind(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        mMap.setOnMapClickListener(this);
    }

    private LatLng start;
    private LatLng end;
    private boolean isChoosingStart = false;
    private boolean isChoosingEnd = false;

    @OnClick(R.id.setStart)
    public void setStart() {
        isChoosingStart = true;
        isChoosingEnd = false;
    }

    @OnClick(R.id.setEnd)
    public void setEnd() {
        isChoosingEnd = true;
        isChoosingStart = false;
    }

    @BindView(R.id.startLocation) EditText startText;
    @BindView(R.id.endLocation) EditText endText;

    @Override
    public void onMapClick(LatLng latLng) {
        Log.d("LATLNG", latLng.latitude + "," + latLng.longitude);

        if (isChoosingStart) {
            start = latLng;
            mMap.addMarker(new MarkerOptions().position(start).title("Start"));

            startText.setText(latLng.toString());
            isChoosingStart = false;
        }

        if (isChoosingEnd) {
            end = latLng;
            mMap.addMarker(new MarkerOptions().position(end).title("End"));

            endText.setText(latLng.toString());
            isChoosingEnd = false;
        }
    }
}
