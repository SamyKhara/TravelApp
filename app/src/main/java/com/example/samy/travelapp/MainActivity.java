package com.example.samy.travelapp;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap mGoogleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(googleServicesAvailable()){
            Toast.makeText(this,"PERFECT!!!!", Toast.LENGTH_LONG).show();
            setContentView(R.layout.activity_main);
            initMap();
        } else {
            //No Google Maps Layout
            System.out.println("**Error in Displaying Google Maps**");
        }
    }

    private void initMap() {
        MapFragment mapFragment= (MapFragment) getFragmentManager().findFragmentById(R.id.mapfragment);
        mapFragment.getMapAsync(this);
    }

    public boolean googleServicesAvailable() {
        GoogleApiAvailability api= GoogleApiAvailability.getInstance();
        int isAvailable= api.isGooglePlayServicesAvailable(this);
        if(isAvailable ==ConnectionResult.SUCCESS) {
            return true;
        } else if (api.isUserResolvableError(isAvailable)) {
            Dialog dialog = api.getErrorDialog(this, isAvailable,0);
            dialog.show();
        } else {
            Toast.makeText(this, "Connect to play services", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap= googleMap;
        goToLocationZoom(28.576990, 77.043202, 15);
    }

    private void goToLocation(double lat, double lng){
        LatLng ll= new LatLng(lat, lng);
        CameraUpdate update= CameraUpdateFactory.newLatLng(ll);
        mGoogleMap.moveCamera(update);
    }

    private void goToLocationZoom(double lat, double lng, float zoom){
        LatLng ll= new LatLng(lat, lng);
        CameraUpdate update= CameraUpdateFactory.newLatLngZoom(ll, zoom);
        mGoogleMap.moveCamera(update);
    }
}