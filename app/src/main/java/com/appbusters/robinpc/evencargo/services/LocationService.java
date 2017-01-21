package com.appbusters.robinpc.evencargo.services;



import android.Manifest;
import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.Preference;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.appbusters.robinpc.evencargo.Constants;
import com.appbusters.robinpc.evencargo.LocationClass;
import com.appbusters.robinpc.evencargo.managerpanel.ManagerPanelActivity;
import com.firebase.client.Firebase;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;

public class LocationService extends Service implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {
    public GoogleApiClient mGoogleApiClient;
    private static int UPDATE_INTERVAL = 10000; // 10 sec
    private static int FATEST_INTERVAL = 5000; // 5 sec
    private static int DISPLACEMENT = 10; // 10 meters
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    public static Status status;
    String TAG="shut the f*** up";
    Location mLastLocation;
    Binder binder;
    static double lat,lon;
    LocationRequest mLocationRequest;
    boolean mRequestingLocationUpdates=true;
    Firebase firebase;
    public LocationService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        } Log.e("20000","onstartcommand");
        mGoogleApiClient.connect();Log.e("hi","buddy");

        String s=Constants.location;
        if(s==null)
        {String from=NotificationService.si;
            from = from.replaceAll("\\.", "-1-");
            from = from.replaceAll("\\$", "-2-");
            from = from.replaceAll("\\#", "-3-");
            s="https://chat-6c23a.firebaseio.com/invites/"+from+"/location";}
        firebase=new Firebase(s);
        //binder=new MyBinder();
        return super.onStartCommand(intent, flags, startId);
    }

    public double getLat(){ Log.e("10","1");return lat;}
    protected void createLocationRequest() {
        Log.e("60","createlocationrequest");
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FATEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
    }
    protected void startLocationUpdates() {Log.e("6901","190");

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e("bro","you r done");
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }Log.e("62","startlocationupdate");
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);

    }
    /**
     * Stopping location updates
     */
    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }

    /**
     * Google api callback methods
     */
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = "
                + result.getErrorCode());
        Log.e("conncetion","failed");
    }

    @Override
    public void onConnected(Bundle arg0) {

        // Once connected with google api, get the location
        //displayLocation();
        Log.e("6trrt3","1trt");
        if (mRequestingLocationUpdates) {
            createLocationRequest();
            startLocationUpdates();
        }
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
        Log.e("6rtrtrt4","onconnectionsuspended");
    }

    @Override
    public void onLocationChanged(Location location) {
        // Assign the new location
        mLastLocation = location;
        lat=mLastLocation.getLatitude();
        lon=mLastLocation.getLongitude();
        Log.e("dfdfdf","onlocationchanged");
        LocationClass locationClass=new LocationClass(lat,lon);
        firebase.setValue(locationClass);
    }

    @Override
    public void onDestroy() {Log.e("Location","destroyed");
        super.onDestroy();
        stopLocationUpdates();
        mGoogleApiClient.disconnect();

    }
}
