package com.example.mapstaskapp.Models;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;

public class Errand {
    public String id;
    public String description;
    public LatLng start;
    public LatLng end;
    public boolean isComplete;

    public Errand() {}

    public Errand(String description, LatLng start, LatLng end) {
        this.id = "";
        this.description = description;
        this.start = start;
        this.end = end;
    }

    public static Errand fromSnapshot(DataSnapshot snapshot) {
        Errand errand = new Errand();

        errand.id = snapshot.getKey();
        errand.description = snapshot.child("description").getValue(String.class);
        errand.isComplete = snapshot.child("isComplete").getValue(boolean.class);

        float startLat = snapshot.child("start").child("lat").getValue(float.class);
        float startLong = snapshot.child("start").child("long").getValue(float.class);
        errand.start = new LatLng(startLat, startLong);

        float endLat = snapshot.child("end").child("lat").getValue(float.class);
        float endLong = snapshot.child("end").child("long").getValue(float.class);
        errand.end = new LatLng(endLat, endLong);

        return errand;

    }
}
