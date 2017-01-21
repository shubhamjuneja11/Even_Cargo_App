package com.appbusters.robinpc.evencargo.orders;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.appbusters.robinpc.evencargo.PersonLocation;
import com.appbusters.robinpc.evencargo.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DisplayLocation extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_location);
        initializeMap();
    }

    private void initializeMap() {
        MapFragment mapFragment = ((MapFragment) getFragmentManager().findFragmentById(
                R.id.map));
        mapFragment.getMapAsync(this);
        // check if map is created successfully or not

    }


    @Override
    protected void onResume() {
        super.onResume();
        initializeMap();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap=googleMap;
        setUpMap();

    }

    public void setUpMap() {

        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        /*String adr=getIntent().getStringExtra("add1");
  getLocationFromAddress(adr,R.drawable.client,"Pickup");
        String adr2=getIntent().getStringExtra("add2");
        getLocationFromAddress(adr2,R.drawable.driver,"Destination");*/
        /*googleMap.setMyLocationEnabled(true);
        googleMap.setTrafficEnabled(true);
        googleMap.setIndoorEnabled(true);
        googleMap.setBuildingsEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);*/
        ArrayList<PersonLocation> pl=DisplayOrder1.pl;
        int i=0;
        if(pl.size()==0)return;
        while(i<pl.size()){
            String add;
            double latitude,longitude;
            add=pl.get(i).name;
            latitude=pl.get(i).latitude;
            longitude=pl.get(i).longitude;
            i++;
            LatLng latLng = new LatLng(latitude, longitude);
            googleMap.addMarker(new MarkerOptions().position(latLng).title(add).icon(BitmapDescriptorFactory.fromResource(R.drawable.driver)));
        }
    }

    public void getLocationFromAddress(String strAddress,int a,String s)
    {
        //Create coder with Activity context - this
        Geocoder coder = new Geocoder(this);
        List<Address> address;

        try {
            //Get latLng from String
            address = coder.getFromLocationName(strAddress,5);

            //check for null
            if (address == null) {
                return;
            }

            //Lets take first possibility from the all possibilities.
            Address location=address.get(0);
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

            //Put marker on map on that LatLng
            /*Marker srchMarker = */googleMap.addMarker(new MarkerOptions().position(latLng).title(s).icon(BitmapDescriptorFactory.fromResource(a)));
            //Animate and Zoon on that map location
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
