package com.example.mapstaskapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.example.mapstaskapp.Models.Errand;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private static final int REQUEST_PERMISSION_GRANT = 1;
    private GoogleMap mMap;
    private LocationManager mLocationManager;
    private LatLng mCurrentLocation;

    private int LOCATION_REFRESH_TIME = 1;
    private int LOCATION_REFRESH_DISTANCE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        ButterKnife.bind(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            initializeLocationListener();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
            }, REQUEST_PERMISSION_GRANT);
        }
    }

    @SuppressLint("MissingPermission")
    private void initializeLocationListener() {
        LocationListener listener = this;
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                LOCATION_REFRESH_TIME, LOCATION_REFRESH_DISTANCE, listener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION_GRANT && grantResults[0] == RESULT_OK &&
            requestCode == REQUEST_PERMISSION_GRANT && grantResults[1] == RESULT_OK) {
            initializeLocationListener();
        }
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

        final Intent data = getIntent();
        FirebaseDatabase.getInstance().getReference("errands").child(data.getStringExtra("id"))    .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Errand errand = Errand.fromSnapshot(dataSnapshot);

                mMap.addMarker(new MarkerOptions().title("start").position(errand.start).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

                mMap.addMarker(new MarkerOptions().title("end").position(errand.end));

                LatLngBounds bounds = LatLngBounds.builder().include(errand.start).include(errand.end).build();
                int padding = 200;
                mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        mCurrentLocation = latLng;
    }

    @OnClick(R.id.goToMyLocation)
    public void goToMyLocation() {
        if (mCurrentLocation != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(mCurrentLocation));
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("GPS", "gps turned on");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("GPS", "gps turned off");
    }
}
