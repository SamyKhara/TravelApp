package com.example.samy.travelapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.identity.intents.Address;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    GoogleMap mGoogleMap;
    GoogleApiClient mGoogleApiClient;
    Marker marker;
    Polyline line;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (googleServicesAvailable()) {
            Toast.makeText(this, "Welcome", Toast.LENGTH_LONG).show();
            setContentView(R.layout.activity_main);
            initMap();
        } else {
            //No Google Maps Layout
            System.out.println("**Error in Displaying Google Maps**");
        }

//        back = (Button)findViewById(R.id.backBtn);
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MapActivity.this, TravelItinerary.class);
//                startActivity(intent);
//            }
//        });
    }

    private void initMap() {
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapfragment);
        mapFragment.getMapAsync(this);
    }

    public boolean googleServicesAvailable() {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(this);
        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (api.isUserResolvableError(isAvailable)) {
            Dialog dialog = api.getErrorDialog(this, isAvailable, 0);
            dialog.show();
        } else {
            Toast.makeText(this, "Connect to play services", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        System.out.println("\n\n**** In onMapReady function ****");
        mGoogleMap = googleMap;

        //goToLocationZoom(28.576990, 77.043202, 13);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            System.out.println("#### In checkSelfPermission");
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        System.out.println("\n\n**** Before setMyLocationEnabled function ****");
        mGoogleMap.setMyLocationEnabled(true);
        //This over hear gives us that 'MyLocation' Button in the Maps which is used to get to my present location!
        System.out.println("\n\n**** After setMyLocationEnabled function ****");
    }
        /*
       //This code gets us to our present location the moment map starts!
       mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        //Object created but never connected to the Google Api Client
        mGoogleApiClient.connect();*/

    private void goToLocationZoom(double lat, double lng, float zoom) {
        LatLng ll = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        mGoogleMap.moveCamera(update);
    }



    public void geoLocate(View view) throws IOException {

        ArrayList<String> arr = new ArrayList<>();
        arr.add("Dwarka Sector 21");
        arr.add("Dwarka Sector 10");
        arr.add("Dwarka Sector 12");

        //Finding my  present location and getting there!
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location myLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        double latitude = myLocation.getLatitude();
        double longitude = myLocation.getLongitude();
        System.out.println("## myLocation latitude: "+ latitude);
        System.out.println("## myLocation longitude: "+ longitude);

        //If you are testing this on your phone in singapore, use the following present location:
//        double latitude= 28.57713316;
//        double longitude= 77.04353435;

        //Working on setting marker points
        String location;
        int i;

        for (i=0;i<3;i++)
        {
            if (i==0){
                //Home means Present Location of the person!
                setMarker("Home", latitude, longitude, -1);
            }

            location= arr.get(i);
            System.out.println("$$$$ Location "+i+" : "+location);

            Geocoder gc = new Geocoder(this);

            List<android.location.Address> list = gc.getFromLocationName(location, 1);

            android.location.Address address = list.get(0);

            String locality = address.getLocality();

            double lat = address.getLatitude();
            double lng = address.getLongitude();

            setMarker(location, lat, lng, i);
        }
        System.out.println("******Loop has finished!");

        //Takes me straight to my present location since line starts from there!
        goToLocationZoom(latitude,longitude,13);
    }


    Marker marker1;
    Marker marker2;
    Marker marker3;
    Marker marker4;

    //This method is only for Setting marker and then drawing the line. Is hardcoded as of now!
    private void setMarker(String location, double lat, double lng, int i) {

        i=i+1;
        //        if (marker!=null){
//            removePrevinstances();
//        }

        System.out.println("\n\nI am in setMarker function");
        MarkerOptions options= new MarkerOptions()
                            .title(location)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                            .position(new LatLng(lat,lng))
                            .snippet("Point #: "+i);

        if(marker1==null){
            marker1= mGoogleMap.addMarker(options);
        }else if (marker2==null){
            marker2= mGoogleMap.addMarker(options);
        }else if (marker3==null){
            marker3= mGoogleMap.addMarker(options);
        }else  if (marker4==null){
            marker4= mGoogleMap.addMarker(options);
            drawLine();
        }else{
            System.out.println("!!!!!!! IT HAS COME INTO ELSE !!!!!!");
        }

    }


    private void drawLine(){
        PolylineOptions options = new PolylineOptions();


            options.add(marker1.getPosition())
                    .add(marker2.getPosition())
                    .add(marker3.getPosition())
                    .add(marker4.getPosition())
                    .color(Color.RED)
                    .width(4);

        line=mGoogleMap.addPolyline(options);
    }

//Code to remove markers
//    private void removePrevinstances(){
//        marker.remove();
//        marker= null;
//    }


    LocationRequest mLocationRequest;
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        //Sets in how much time the user's location is refreshed
        mLocationRequest.setInterval(1000);

            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }

        if(mGoogleApiClient!= null){
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
        else{
            System.out.println("####### ERROR in onConnected");
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        System.out.println("****## In onConnectionSuspended method!##****");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        System.out.println("****## In onConnectionFailed method!##****");
    }

    @Override
    public void onLocationChanged(Location location) {

        System.out.println("****## In onLocationChanged method!##****");

        if (location==null){
            Toast.makeText(this, "Cant get current location", Toast.LENGTH_LONG).show();
        }
        else{
            LatLng ll= new LatLng(location.getLatitude(), location.getLongitude());
            CameraUpdate update= CameraUpdateFactory.newLatLngZoom(ll, 15);
            mGoogleMap.animateCamera(update);
        }
    }

}