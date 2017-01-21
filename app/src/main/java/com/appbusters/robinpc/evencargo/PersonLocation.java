package com.appbusters.robinpc.evencargo;

/**
 * Created by junejaspc on 3/10/16.
 */
public class PersonLocation {
    public String name;
    public double latitude,longitude;
    int id;
    public PersonLocation(String n, double a, double b,int id)
    {
        name=n;
        latitude=a;
        longitude=b;
        this.id=id;
    }

}
