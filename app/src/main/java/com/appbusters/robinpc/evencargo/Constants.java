package com.appbusters.robinpc.evencargo;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.appbusters.robinpc.evencargo.services.NotificationService;
import com.appbusters.robinpc.evencargo.siginsignup.SigninActivity;
import com.appbusters.robinpc.evencargo.users.UsersAdapter;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;

/**
 * Created by junejaspc on 14/9/16.
 */
public class Constants {
    public static String mymanagers,mydrivers,myclients,registered_users,invites,myid,managerorders,orderno,driverorders,clientorders,location,app;
    public static String from= " ",orders;
    public static void fun() {
        from=Constants.convert1(from);
        mymanagers="https://chat-6c23a.firebaseio.com/invites/"+from+"/MyManagers";
       mydrivers="https://chat-6c23a.firebaseio.com/invites/"+from+"/MyDrivers";
         myclients="https://chat-6c23a.firebaseio.com/invites/"+from+"/MyClients";
         registered_users="https://chat-6c23a.firebaseio.com/registered_users";
        invites="https://chat-6c23a.firebaseio.com/invites";
        myid="https://chat-6c23a.firebaseio.com/invites/"+from;
        managerorders=myid+"/ManagerOrders";
        app="https://chat-6c23a.firebaseio.com/invites";
        orderno="https://chat-6c23a.firebaseio.com/Orderno";
        driverorders=myid+"/DriverOrders";
        clientorders=myid+"/ClientOrders";
        location=myid+"/location";
        orders=app+"/Orders";
    }
public static String convert1(String a)
{
    String from=a;
    from = from.replaceAll("\\.", "-1-");
    from = from.replaceAll("\\$", "-2-");
    from = from.replaceAll("\\#", "-3-");
    return from;

}
    public static String convert2(String a)
    {
        String key=a;
        key=key.replaceAll("-1-",".");
        key=key.replaceAll("-2-","$");
        key=key.replaceAll("-3-","#");
        return key;
    }

}
