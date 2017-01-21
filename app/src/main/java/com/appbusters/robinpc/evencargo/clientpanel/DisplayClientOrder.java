package com.appbusters.robinpc.evencargo.clientpanel;

        import android.content.DialogInterface;
        import android.content.Intent;
        import android.location.Address;
        import android.location.Geocoder;
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

        import com.appbusters.robinpc.evencargo.Constants;
        import com.appbusters.robinpc.evencargo.mypeople.MyDriversActivity;
        import com.appbusters.robinpc.evencargo.R;
        import com.appbusters.robinpc.evencargo.orders.DisplayLocation;
        import com.appbusters.robinpc.evencargo.orders.OrderClass;
        import com.appbusters.robinpc.evencargo.siginsignup.SigninActivity;
        import com.appbusters.robinpc.evencargo.chat.ChatPage;
        import com.appbusters.robinpc.evencargo.users.UsersAdapter;
        import com.firebase.client.ChildEventListener;
        import com.firebase.client.DataSnapshot;
        import com.firebase.client.Firebase;
        import com.firebase.client.FirebaseError;
        import com.firebase.client.ValueEventListener;
        import com.google.android.gms.maps.CameraUpdateFactory;
        import com.google.android.gms.maps.model.BitmapDescriptorFactory;
        import com.google.android.gms.maps.model.LatLng;
        import com.google.android.gms.maps.model.MarkerOptions;

        import java.io.IOException;
        import java.util.ArrayList;
        import java.util.List;

public class DisplayClientOrder extends AppCompatActivity {
    TextView t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11;
    Firebase f;
    String order;
    OrderClass orderClass;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_order1);
        f = new Firebase(Constants.invites + "/Orders");
        order = getIntent().getStringExtra("orderno");
        fab = (FloatingActionButton) findViewById(R.id.fab);
        Log.e("ayayayyayayayya", order);
        order = order.substring(1);
        Log.e("ttttt", order);
        f.child(order).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
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
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final int decide = getIntent().getExtras().getInt("decide");
                PopupMenu popupMenu = new PopupMenu(DisplayClientOrder.this, view);
                if (decide == 6) {
                    popupMenu.getMenuInflater().inflate(R.menu.clientmenu, popupMenu.getMenu());

                } else if (decide == 7) {
                    popupMenu.getMenuInflater().inflate(R.menu.clientmenu, popupMenu.getMenu());
                } else {
                    popupMenu.getMenuInflater().inflate(R.menu.clientmenucompleted, popupMenu.getMenu());
                }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.cancel: {


                                {
                                    final Firebase f2;
                                    if (decide == 6)
                                        f2 = new Firebase(Constants.clientorders).child("Pending");
                                    else f2 = new Firebase(Constants.clientorders).child("Active");
                                    f2.addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            Firebase f3;
                                            OrderClass orderClass = dataSnapshot.getValue(OrderClass.class);
                                            if (orderClass.orderno == Long.valueOf(order)) {
                                                orderClass.status = 4;
                                                f2.child(dataSnapshot.getKey()).removeValue();
                                                f3 = new Firebase(Constants.managerorders).child("Completed");
                                                f3.push().setValue(orderClass);

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
                                }
                                break;
                            }
                            case R.id.assigndriver: {
                                Firebase f;
                                final ArrayList<String> arrayOfUsers;
                                arrayOfUsers = new ArrayList<String>();
                                final UsersAdapter adapter = new UsersAdapter(DisplayClientOrder.this, arrayOfUsers);
                                f = new Firebase(Constants.mydrivers);
                                f.addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                        String key = dataSnapshot.getKey();
                                        String value = dataSnapshot.getValue(String.class);
                                        if (value.equals("1")) {
                                            key = Constants.convert2(key);
                                            arrayOfUsers.add(key);
                                        } else arrayOfUsers.remove(key);
                                        adapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                                        Log.e("sds", dataSnapshot.getKey());
                                        String key = dataSnapshot.getKey();
                                        String value = dataSnapshot.getValue(String.class);
                                        Log.e("3333", "3333");
                                        key = key.replaceAll("-1-", ".");
                                        key = key.replaceAll("-2-", "$");
                                        key = key.replaceAll("-3-", "#");
                                        if (!value.equals("1")) {
                                            adapter.remove(key);

                                        } else adapter.add(key);
                                        adapter.notifyDataSetChanged();
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
                                AlertDialog.Builder builderSingle = new AlertDialog.Builder(DisplayClientOrder.this);
                                builderSingle.setTitle("Select Driver");
                                builderSingle.setNegativeButton(
                                        "cancel",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                final ArrayAdapter<String> driveradapter = new ArrayAdapter<String>(DisplayClientOrder.this, android.R.layout.select_dialog_singlechoice);
                                driveradapter.addAll(arrayOfUsers);
                                builderSingle.setAdapter(
                                        driveradapter,
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                String strName = driveradapter.getItem(which);
                                                ((TextView) view).setText(strName);
                                            }
                                        });
                                builderSingle.show();
                                break;
                            }
                            case R.id.messageclient: {
                                Intent intent = new Intent(DisplayClientOrder.this, ChatPage.class);
                                intent.putExtra("FROM_USER", SigninActivity.myemail);
                                String client = t3.getText().toString();
                                if (client.contains("@"))
                                    intent.putExtra("TO_USER", client);
                                startActivity(intent);
                                break;
                            }
                            case R.id.messagedriver: {
                                Intent intent = new Intent(DisplayClientOrder.this, ChatPage.class);
                                intent.putExtra("FROM_USER", SigninActivity.myemail);
                                String driver = t2.getText().toString();
                                if (driver.contains("@"))
                                    intent.putExtra("TO_USER", driver);
                                startActivity(intent);
                                break;
                            }
                            /*case R.id.mapview: {
                                Intent intent = new Intent(DisplayClientOrder.this, DisplayLocation.class);
                                intent.putExtra("add1", orderClass.paddress);
                                intent.putExtra("add2", orderClass.daddress);
                                startActivity(intent);
                                break;
                            }*/
                            case R.id.delete: {
                                final Firebase f2 = new Firebase(Constants.managerorders).child("Completed");
                                f2.addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                        if (dataSnapshot.getValue(OrderClass.class).orderno == Long.valueOf(order))
                                            f2.child(dataSnapshot.getKey()).removeValue();
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
}
