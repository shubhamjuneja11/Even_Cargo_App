package com.appbusters.robinpc.evencargo.orders;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.appbusters.robinpc.evencargo.Constants;
import com.appbusters.robinpc.evencargo.R;
import com.appbusters.robinpc.evencargo.services.NotificationService;
import com.appbusters.robinpc.evencargo.siginsignup.SigninActivity;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ManagerOrder extends AppCompatActivity {
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    int flag;
    double latitude,longitude;
    String TAG="HI";
    Firebase f1,f2,f3;
    TextView managername;
    Firebase firebase;
    TextView t1,t2,t3,t4,t5,t6,t7,t8,t9,t10;
    String manager,driver,client,pdate,ptime,paddress,paddressdetails,daddress,daddressdetails,packagedetails;
    ArrayAdapter<String> clientadapter,driveradapter;
   long orderno;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
getMenuInflater().inflate(R.menu.ordermenu,menu);
         firebase=new Firebase(Constants.orderno);
        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("done","bro");
                Log.e("Order key",dataSnapshot.getKey());Log.e("Order value",dataSnapshot.getValue(String.class));
                //.e("ddddddddddd",String.valueOf(dataSnapshot.getValue()));
                orderno =dataSnapshot.getValue(Long.class);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.save)
        {

            t1=(TextView)findViewById(R.id.managername);
            t2=(TextView)findViewById(R.id.drivername);
            t3=(TextView)findViewById(R.id.clientname);
            t4=(TextView)findViewById(R.id.date);
            t5=(TextView)findViewById(R.id.time);
            t6=(TextView)findViewById(R.id.pickup);
            t7=(TextView)findViewById(R.id.pickuptext);
            t8=(TextView)findViewById(R.id.delivery);
            t9=(TextView)findViewById(R.id.deliverytext);
            t10=(TextView)findViewById(R.id.packages);
            manager=t1.getText().toString();
            driver=t2.getText().toString();
            client=t3.getText().toString();
            pdate=t4.getText().toString();;
            ptime=t5.getText().toString();
            paddress=t6.getText().toString();
            paddressdetails=t7.getText().toString();
            daddress=t8.getText().toString();
            daddressdetails=t9.getText().toString();
            packagedetails=t10.getText().toString();

           /*if(driver.equals("Driver")||client.equals("Client")||pdate.equals("Date")||ptime.equals("Time")||paddress.equals("Pickup Address")||daddress.equals("Delivery Address")||packagedetails.equals("Package Detials"))
              Toast.makeText(this,"Fill complete Details",Toast.LENGTH_SHORT).show();
           else if(!client.contains("@"))Toast.makeText(this,"Enter valid client email",Toast.LENGTH_SHORT).show();
            else */
            if(manager.equals("Manager")||pdate.equals("Date")||ptime.equals("Time")||paddress.equals("Pickup Address")||daddress.equals("Delivery Address")||packagedetails.equals("Package Detials"))
            Toast.makeText(this,"Fill complete Details",Toast.LENGTH_SHORT).show();
            else {
                {
                    int status;
                    if (driver.equals("Driver"))
                        status = 0;
                    else status = 1;
                   OrderClass neworder = new OrderClass(manager, driver, client, pdate, ptime, paddress, paddressdetails, daddress, daddressdetails, packagedetails, status, orderno,0,1,1);
                    Log.e("Ordernosssssssss", String.valueOf(orderno));
                    firebase.setValue(orderno + 1);
                    f3 = new Firebase(Constants.orders);
                    f3.child(String.valueOf(neworder.orderno)).setValue(neworder);
                    finish();
                    Toast.makeText(ManagerOrder.this,"Order placed",Toast.LENGTH_SHORT).show();

               /* f3 = new Firebase(Constants.myid);
                f3.child("ManagerOrders").child("Pending").push().setValue(neworder);
                f3=new Firebase(Constants.invites);
                String from=Constants.convert1(driver);
               if(status==1)
                f3.child(from).child("DriverOrders").child("Assigned").push().setValue(neworder);
                //f3=new Firebase(Constants.invites);
                f3.child("Orders").child(String.valueOf(neworder.getorderno())).setValue(neworder);
                f3.child(Constants.convert1(neworder.client)).child("ClientOrders").child("Pending").push().setValue(neworder);*/
                    // f3.child("ManagerOrders").push()
                }
            }

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("email",SigninActivity.myemail);

        setContentView(R.layout.activity_manager_order);
        managername=(TextView)findViewById(R.id.managername);
        managername.setText(SigninActivity.myemail);
        f1=new Firebase(Constants.myclients);
        f2=new Firebase(Constants.mydrivers);
        clientadapter=new ArrayAdapter<String>(
                ManagerOrder.this,
                android.R.layout.select_dialog_singlechoice);
        driveradapter=new ArrayAdapter<String>(
                ManagerOrder.this,
                android.R.layout.select_dialog_singlechoice);
        f1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                clientadapter.add(Constants.convert2(dataSnapshot.getKey()));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                    clientadapter.remove(dataSnapshot.getKey());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        f2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                driveradapter.add(Constants.convert2(dataSnapshot.getKey()));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                driveradapter.remove(dataSnapshot.getKey());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
       // driveradapter.addAll(arrayOfUsers);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F89748")));
        final TextView editext=(TextView) findViewById(R.id.date);
        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

            private void updateLabel() {
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                editext.setText(sdf.format(myCalendar.getTime()));
            }


        };


        editext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(ManagerOrder.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


//Time
        final TextView text=(TextView)findViewById(R.id.time);
        text.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(ManagerOrder.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        text.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);// 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
    }
    public void fun(final View view){flag=0;
        switch (view.getId()){
            case R.id.clientname:{
                AlertDialog.Builder builderSingle = new AlertDialog.Builder(ManagerOrder.this);
                //builderSingle.setIcon(R.drawable.ic_launcher);
                builderSingle.setTitle("Select Client");
                builderSingle.setNegativeButton(
                        "cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                builderSingle.setAdapter(
                        clientadapter,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String strName = clientadapter.getItem(which);
                                ((TextView)view).setText(strName);
                            }
                        });
                builderSingle.show();
                break;
            }
            case R.id.drivername:{
                AlertDialog.Builder builderSingle = new AlertDialog.Builder(ManagerOrder.this);
                //builderSingle.setIcon(R.drawable.ic_launcher);
                builderSingle.setTitle("Select Driver");
                builderSingle.setNegativeButton(
                        "cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                builderSingle.setAdapter(
                        driveradapter,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String strName = driveradapter.getItem(which);
                                ((TextView)view).setText(strName);
                            }
                        });
                builderSingle.show();
                break;
            }
            case R.id.pickup:{
                try {
                    Intent intent =
                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                    .build(this);
                    flag=1;
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                    //((TextView)view).setText(address);
                } catch (GooglePlayServicesRepairableException e) {
                    Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
                } catch (GooglePlayServicesNotAvailableException e) {
                    Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
                    // TODO: Handle the error.
                }
                break;

            }
            case R.id.delivery:{

                try {
                    Intent intent =
                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                    .build(this);
                    flag=2;
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                    //((TextView)view).setText(address);
                } catch (GooglePlayServicesRepairableException e) {
                    Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
                } catch (GooglePlayServicesNotAvailableException e) {
                    Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
                    // TODO: Handle the error.
                }
                break;
            }
            case  R.id.packages:{
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Package details:");
                final TextView textView=(TextView)view.findViewById(R.id.packages);
// Set up the input
                final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT );
                builder.setView(input);

// Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String m_Text = input.getText().toString();
                        textView.setText(m_Text);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
                break;
            }
            case R.id.button1:{
                String s=((TextView)findViewById(R.id.pickup)).getText().toString();
                if(!s.equals("Pickup Address")) {
                    String map = "http://www.google.com/maps/place/"+latitude+","+longitude;
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(map));
                    startActivity(i);
                }
                else Toast.makeText(this,"Enter address first",Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.button2:{
                String s=((TextView)findViewById(R.id.delivery)).getText().toString();
                if(!s.equals("Delivery Address")) {
                    String map = "http://www.google.com/maps/place/"+latitude+","+longitude;
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(map));
                    startActivity(i);
                }
                else Toast.makeText(this,"Enter address first",Toast.LENGTH_SHORT).show();
                break;
            }

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Toast.makeText(this,"fff",Toast.LENGTH_LONG).show();
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {//Toast.makeText(this,"code",Toast.LENGTH_LONG).show();
            if (resultCode == RESULT_OK) {//Toast.makeText(this,"ok",Toast.LENGTH_LONG).show();
                Place place = PlaceAutocomplete.getPlace(this, data);
                // Log.i(TAG, "Place: " + place.getName());
                String address= place.getName()+" "+place.getAddress();
                LatLng latLng=place.getLatLng();
                latitude=latLng.latitude;
                longitude=latLng.longitude;
                //Toast.makeText(this,place.getName()+address,Toast.LENGTH_LONG).show();
                {     if(flag==1)
                {
                    TextView textView = (TextView) findViewById(R.id.pickup);
                    textView.setText(address);
                }
                else {
                    TextView textView = (TextView) findViewById(R.id.delivery);
                    textView.setText(address);
                }
                }
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.i(TAG, status.getStatusMessage());

                //  Toast.makeText(this,status.getStatusMessage(),Toast.LENGTH_LONG).show();

            } else if (resultCode == RESULT_CANCELED) {//Toast.makeText(this,"cancel",Toast.LENGTH_LONG).show();
                // The user canceled the operation.
            }
        }
    }
    public void clientmanual(View view){
        t3=(TextView)findViewById(R.id.clientname);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Client email");
        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT );
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String m_Text = input.getText().toString();
                t3.setText(m_Text);
                client=m_Text;
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

}
