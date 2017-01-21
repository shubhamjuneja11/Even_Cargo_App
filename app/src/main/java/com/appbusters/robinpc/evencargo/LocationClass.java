package com.appbusters.robinpc.evencargo;

/**
 * Created by junejaspc on 2/10/16.
 */
public class LocationClass {
public double latitude,longitude;
    public LocationClass(){}
    public LocationClass(double latitude,double longitude){
        this.latitude=latitude;
        this.longitude=longitude;
    }
    public double getLatitude(){return latitude;}
    public double getLongitude(){return longitude;}

    public void putLatitude(double latitude){this.latitude=latitude;}
    public void putLongitude(double longitude){this.longitude=longitude;}
}
