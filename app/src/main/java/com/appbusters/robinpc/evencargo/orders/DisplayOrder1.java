package com.appbusters.robinpc.evencargo.orders;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.appbusters.robinpc.evencargo.Constants;
import com.appbusters.robinpc.evencargo.PersonLocation;
import com.appbusters.robinpc.evencargo.R;
import com.appbusters.robinpc.evencargo.siginsignup.SigninActivity;
import com.appbusters.robinpc.evencargo.chat.ChatPage;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DisplayOrder1 extends AppCompatActivity {
    TextView t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11;
    Firebase f;
    String order;
    OrderClass orderClass;
    FloatingActionButton fab;
    int decide;
    public static ArrayList<PersonLocation> pl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_order1);
        decide = getIntent().getExtras().getInt("decide");
        Log.e("hi baessssss",String.valueOf(decide));
        f = new Firebase(Constants.orders);
        order = getIntent().getStringExtra("orderno");
        fab = (FloatingActionButton) findViewById(R.id.fab);
        Log.e("ayayayyayayayya", order);
        order = order.substring(1);
        Log.e("ttttt", order);
        f.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.getKey().equals(order)) {
                    orderClass = dataSnapshot.getValue(OrderClass.class);
                    Log.e("key", dataSnapshot.getKey());
                    Log.e("value", orderClass.getmanager());
                    t1 = (TextView) findViewById(R.id.managername);
                    t2 = (TextView) findViewById(R.id.drivername);
                    t3 = (TextView) findViewById(R.id.clientname);
                    t4 = (TextView) findViewById(R.id.date);
                    t5 = (TextView) findViewById(R.id.time);
                    t6 = (TextView) findViewById(R.id.pickup);
                    t7 = (TextView) findViewById(R.id.pickuptext);
                    t8 = (TextView) findViewById(R.id.delivery);
                    t9 = (TextView) findViewById(R.id.deliverytext);
                    t10 = (TextView) findViewById(R.id.packages);
                    t11 = (TextView) findViewById(R.id.ordernos);


                    t1.setText(orderClass.getmanager());
                    t2.setText(orderClass.getdriver());
                    t3.setText(orderClass.getclient());
                    t4.setText(orderClass.getpdate());
                    t5.setText(orderClass.getptime());
                    t6.setText(orderClass.getpaddress());
                    t7.setText(orderClass.getpaddressdetails());
                    t8.setText(orderClass.getdaddress());
                    t9.setText(orderClass.getdaddressdetails());
                    t10.setText(orderClass.getpackagedetails());
                    t11.setText("#" + String.valueOf(orderClass.getorderno()));
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        Firebase f;
        final ArrayList<String> arrayOfUsers;
        arrayOfUsers = new ArrayList<String>();
        // final UsersAdapter adapter = new UsersAdapter(DisplayOrder1.this, arrayOfUsers);
        f = new Firebase(Constants.mydrivers);
        f.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String key = dataSnapshot.getKey();
                String value = dataSnapshot.getValue(String.class);
                if (value.equals("1")) {
                    key = Constants.convert2(key);
                    arrayOfUsers.add(key);Log.e("drivername",key);
                } else arrayOfUsers.remove(key);
                // adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.e("sds", dataSnapshot.getKey());
                String key = dataSnapshot.getKey();
                String value = dataSnapshot.getValue(String.class);
                Log.e("3333", "3333");
                key=Constants.convert2(key);
                                        /*key = key.replaceAll("-1-", ".");
                                        key = key.replaceAll("-2-", "$");
                                        key = key.replaceAll("-3-", "#");*/
                if (!value.equals("1")) {
                    arrayOfUsers.remove(key);
                    //  adapter.remove(key);

                } else arrayOfUsers.add(key);
                //else adapter.add(key);
                //adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                PopupMenu popupMenu = new PopupMenu(DisplayOrder1.this, view);
                if (decide == 0) {
                    popupMenu.getMenuInflater().inflate(R.menu.activemanagerordermenu, popupMenu.getMenu());

                } else if (decide == 1) {
                    popupMenu.getMenuInflater().inflate(R.menu.pendingmanageroordermenu, popupMenu.getMenu());
                } else if(decide==2) {
                    popupMenu.getMenuInflater().inflate(R.menu.completedmanagerordermenu, popupMenu.getMenu());
                }
                else if(decide==3)
                    popupMenu.getMenuInflater().inflate(R.menu.driverstartedmenu, popupMenu.getMenu());
                else if(decide==4)
                    popupMenu.getMenuInflater().inflate(R.menu.driverassignedmenu, popupMenu.getMenu());
                else if(decide==5)
                    popupMenu.getMenuInflater().inflate(R.menu.driverdonemenu, popupMenu.getMenu());
                else if(decide==6)
                    popupMenu.getMenuInflater().inflate(R.menu.clientmenu, popupMenu.getMenu());
                else if(decide==7)
                    popupMenu.getMenuInflater().inflate(R.menu.clientmenu, popupMenu.getMenu());
                else if(decide==8)

                    popupMenu.getMenuInflater().inflate(R.menu.clientmenucompleted, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.cancel: {

                                   Firebase firebase=new Firebase(Constants.orders);
                                    firebase.child(order).removeValue();
                                    orderClass.putstatus(6);
                                    if(decide<=2){orderClass.putnm(0);orderClass.putnd(1);orderClass.putnc(1);Log.e("block","1");}
                                    else if(decide<=5)
                                    {orderClass.putnm(1);orderClass.putnd(0);orderClass.putnc(1);Log.e("block","2");}
                                    else
                                    {orderClass.putnm(1);orderClass.putnd(1);orderClass.putnc(0);Log.e("block","3");}
                                    firebase.child(order).setValue(orderClass);
                                finish();
                                break;
                            }
                            case R.id.assigndriver: {

                                AlertDialog.Builder builderSingle = new AlertDialog.Builder(DisplayOrder1.this);
                                builderSingle.setTitle("Select Driver");
                                builderSingle.setNegativeButton(
                                        "cancel",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                final ArrayAdapter<String> driveradapter = new ArrayAdapter<String>(DisplayOrder1.this, android.R.layout.select_dialog_singlechoice);
                                driveradapter.addAll(arrayOfUsers);Log.e("arrayofusers",String.valueOf(arrayOfUsers.size()));
                                builderSingle.setAdapter(
                                        driveradapter,
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                String strName = driveradapter.getItem(which);
                                               t2.setText(strName);
                                                Firebase firebase=new Firebase(Constants.orders);
                                                firebase.child(order).removeValue();
                                                orderClass.putdriver(strName);
                                                orderClass.putstatus(1);
                                                firebase.child(order).setValue(orderClass);
                                            }
                                        });
                                builderSingle.show();
                                break;
                            }
                            case R.id.messageclient: {
                                String client = t3.getText().toString();
                                if(!client.equals("Client")) {
                                    Intent intent = new Intent(DisplayOrder1.this, ChatPage.class);
                                    intent.putExtra("FROM_USER", SigninActivity.myemail);

                                    if (client.contains("@"))
                                        intent.putExtra("TO_USER", client);
                                    startActivity(intent);
                                }
                                else
                                    Toast.makeText(DisplayOrder1.this, "Client not available", Toast.LENGTH_SHORT).show();
                                break;
                            }
                            case R.id.messagedriver: {
                                String driver = t2.getText().toString();
                                if(!driver.equals("Driver")) {
                                    Intent intent = new Intent(DisplayOrder1.this, ChatPage.class);
                                    intent.putExtra("FROM_USER", SigninActivity.myemail);

                                    if (driver.contains("@"))
                                        intent.putExtra("TO_USER", driver);
                                    startActivity(intent);
                                }
                                else
                                    Toast.makeText(DisplayOrder1.this, "Driver not assigned", Toast.LENGTH_SHORT).show();
                                break;
                            }
                            case R.id.messagemanger: {//driver = manager
                                String driver = t1.getText().toString();
                                if(!driver.equals("Manager")) {
                                    Intent intent = new Intent(DisplayOrder1.this, ChatPage.class);
                                    intent.putExtra("FROM_USER", SigninActivity.myemail);

                                    if (driver.contains("@"))
                                        intent.putExtra("TO_USER", driver);
                                    startActivity(intent);
                                }
                                else
                                    Toast.makeText(DisplayOrder1.this, "Manager not available", Toast.LENGTH_SHORT).show();
                                break;
                            }
                            /*case R.id.mapview: {
                                Intent intent = new Intent(DisplayOrder1.this, DisplayLocation.class);
                                pl=new ArrayList<PersonLocation>();
                                Address address1=getLocationFromAddress(orderClass.paddress,0);
                                Address address2=getLocationFromAddress(orderClass.daddress,0);
                                pl.add(new PersonLocation("Pickup",address1.getLatitude(),address1.getLongitude(),R.drawable.client));
                                pl.add(new PersonLocation("Destination",address2.getLatitude(),address2.getLongitude(),R.drawable.driver));
                                startActivity(intent);
                                break;
                            }*/
                            case R.id.delete: {
                                Firebase firebase=new Firebase(Constants.orders+"/"+order);
                                int whattodo;Log.e("aaal","iss welll");
                                if(decide<=2) {
                                    whattodo = 0; //m
                                    firebase.child("m").setValue(0);
                                     }
                                    else if (decide <= 5) {
                                    whattodo = 1; //d
                                    firebase.child("d").setValue(0);
                                }
                                        else{
                                            whattodo = 2;//c
                                    firebase.child("c").setValue(0);
                                        }

                                            finish();
                                            break;
                                        }


                            case R.id.delievered: {
                                Firebase firebase = new Firebase(Constants.orders);
                                firebase.child(order).removeValue();
                                orderClass.putstatus(5);
                                if(decide<=2){orderClass.putnm(0);orderClass.putnd(1);orderClass.putnc(1);}
                                else if(decide<=5){orderClass.putnm(1);orderClass.putnd(0);orderClass.putnc(1);}
                                else {orderClass.putnm(1);orderClass.putnd(1);orderClass.putnc(0);}
                                firebase.child(order).setValue(orderClass);
                                finish();
                                break;
                            }
                            case R.id.notdelievered:
                            {Firebase firebase=new Firebase(Constants.orders);
                                firebase.child(order).removeValue();
                                orderClass.putstatus(4);
                                if(decide<=2){orderClass.putnm(0);orderClass.putnd(1);orderClass.putnc(1);}
                                else if(decide<=5){orderClass.putnm(1);orderClass.putnd(0);orderClass.putnc(1);}
                                else {orderClass.putnm(1);orderClass.putnd(1);orderClass.putnc(0);}
                                firebase.child(order).setValue(orderClass);
                                finish();
                                break;
                            }
                            case R.id.picked:
                            {Firebase firebase=new Firebase(Constants.orders);
                                firebase.child(order).removeValue();
                                orderClass.putstatus(3);
                                if(decide<=2){orderClass.putnm(0);orderClass.putnd(1);orderClass.putnc(1);}
                                else if(decide<=5){orderClass.putnm(1);orderClass.putnd(0);orderClass.putnc(1);}
                                else {orderClass.putnm(1);orderClass.putnd(1);orderClass.putnc(0);}
                                firebase.child(order).setValue(orderClass);
                                finish();
                                break;
                            }
                            case R.id.start:
                            {Firebase firebase=new Firebase(Constants.orders);
                                firebase.child(order).removeValue();
                                orderClass.putstatus(2);
                                if(decide<=2){orderClass.putnm(0);orderClass.putnd(1);orderClass.putnc(1);}
                                else if(decide<=5){orderClass.putnm(1);orderClass.putnd(0);orderClass.putnc(1);}
                                else {orderClass.putnm(1);orderClass.putnd(1);orderClass.putnc(0);}
                                firebase.child(order).setValue(orderClass);
                                finish();
                                break;
                            }
                            }


                        return true;
                    }
                });
                popupMenu.show();
            }
        });

    }
    public void pickuplocation(View view){
    String pickup=t6.getText().toString();
        getLocationFromAddress(pickup);
    }
    public void deliverylocation(View view){
        String delivery=t8.getText().toString();
        getLocationFromAddress(delivery);
    }
    public void getLocationFromAddress(String strAddress)
    {
        //Create coder with Activity context - this
        Geocoder coder = new Geocoder(this);
        List<Address> address;

        try {
            address = coder.getFromLocationName(strAddress,5);
            if (address == null) {
                return;
            }
            Address location=address.get(0);
            double lat,lng;
            lat=location.getLatitude();
            lng=location.getLongitude();
            String map = "http://www.google.com/maps/place/"+lat+","+lng;
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(map));
            startActivity(i);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public Address getLocationFromAddress(String strAddress,int a)
    {
        //Create coder with Activity context - this
        Geocoder geocoder = new Geocoder(this);
        List<Address> address;

        try {Log.e("adr",strAddress);
            //Get latLng from String
                address=geocoder.getFromLocationName(strAddress,1);
                Address location = address.get(0);
               // LatLng  = new LatLng(location.getLatitude(), location.getLongitude());
                return location;


        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
