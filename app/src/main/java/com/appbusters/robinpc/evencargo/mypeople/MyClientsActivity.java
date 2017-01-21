package com.appbusters.robinpc.evencargo.mypeople;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.appbusters.robinpc.evencargo.Constants;
import com.appbusters.robinpc.evencargo.DisplayDetails;
import com.appbusters.robinpc.evencargo.LocationClass;
import com.appbusters.robinpc.evencargo.PersonLocation;
import com.appbusters.robinpc.evencargo.chat.ChatModel;
import com.appbusters.robinpc.evencargo.clientpanel.AboutUs;
import com.appbusters.robinpc.evencargo.invites.InvitesActivity;
import com.appbusters.robinpc.evencargo.R;
import com.appbusters.robinpc.evencargo.SettingsActivity;
import com.appbusters.robinpc.evencargo.orders.DisplayLocation;
import com.appbusters.robinpc.evencargo.orders.DisplayOrder1;
import com.appbusters.robinpc.evencargo.siginsignup.SigninActivity;
import com.appbusters.robinpc.evencargo.clientpanel.ClientPanelActivity;
import com.appbusters.robinpc.evencargo.driverpanel.DriverPanelActivity;
import com.appbusters.robinpc.evencargo.managerpanel.ManagerPanelActivity;
import com.appbusters.robinpc.evencargo.users.UsersAdapter;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MyClientsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Firebase f,f2;
    ArrayList<String> arrayOfUsers;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_managers);
        progressBar=new ProgressBar(this);
        Log.e("1","1");
        arrayOfUsers = new ArrayList<String>();

// Create the adapter to convert the array to views

        final UsersAdapter adapter = new UsersAdapter(this, arrayOfUsers);

// Attach the adapter to a ListView

        final ListView listView = (ListView) findViewById(R.id.commonlistview);
        Log.e("1","1");
        listView.setAdapter(adapter);
        Log.e("1","1");
        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(adapter.getCount() - 1);
                Log.e("here","123456");
            }
        });
        f=new Firebase(Constants.myclients);
        final ArrayList<PersonLocation> pl=new ArrayList<PersonLocation>();
        DisplayOrder1.pl=pl;
     //   Log.e("##3",Constants.myclients);
        f.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.e("gggggg",dataSnapshot.getKey());
                String key=dataSnapshot.getKey();
                String value=dataSnapshot.getValue(String.class);
                Log.e("key123:",key);Log.e("Value123:",String.valueOf(value));
                if(value.equals("1")) {
                  key=Constants.convert2(key);
                    arrayOfUsers.add(key);
                    Log.e("cid","fbi");
                    Firebase firebase=new Firebase(Constants.invites+"/"+Constants.convert1(key));
                    final String finalKey = key;
                    firebase.addChildEventListener(new ChildEventListener() {

                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {Log.e("aahat","hone ko h");
                            Log.e("mykey",String.valueOf(dataSnapshot.getKey()));
                            if(dataSnapshot.getKey().equals("location")) {
                                LocationClass locationClass = dataSnapshot.getValue(LocationClass.class);
                                double lat, lon;

                                lat = locationClass.getLatitude();
                                lon = locationClass.getLongitude();
                                Log.e("Lat", String.valueOf(lat));
                                Log.e("Lon", String.valueOf(lon));
                                pl.add(new PersonLocation(finalKey, lat, lon, R.drawable.client));
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
                    // adapter.add(key);
                }
                else arrayOfUsers.remove(key);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.e("sds",dataSnapshot.getKey());
                String key=dataSnapshot.getKey();
                String value=dataSnapshot.getValue(String.class);
                Log.e("3333","3333");
                key=key.replaceAll("-1-",".");
                key=key.replaceAll("-2-","$");
                key=key.replaceAll("-3-","#");
                if(!value.equals("1")) {
                    adapter.remove(key);

                }
                else adapter.add(key);
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
        Log.e("1","1");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String email=adapter.getItem(i);
                // Toast.makeText(MyManagersActivity.this,email,Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MyClientsActivity.this,DisplayDetails.class);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu=new PopupMenu(MyClientsActivity.this,view);
                popupMenu.getMenuInflater().inflate(R.menu.myclientsmenu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.viewlocation:{
                                Intent intent=new Intent(MyClientsActivity.this, DisplayLocation.class);
                                startActivity(intent);
                                break;
                            }
                            case R.id.notifyall:{
                                AlertDialog.Builder builder = new AlertDialog.Builder(MyClientsActivity.this);
                                builder.setTitle("Send group messgae:");
                                final EditText input = new EditText(MyClientsActivity.this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                                input.setInputType(InputType.TYPE_CLASS_TEXT );
                                builder.setView(input);

// Set up the buttons
                                builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        {String m_Text = input.getText().toString();
                                        Firebase f;
                                            int i=0;
                                             Log.e("Your brain size",String.valueOf(arrayOfUsers.size()));
                                            if(arrayOfUsers.size()!=0) {
                                                while (i < arrayOfUsers.size()) {
                                                    DateFormat date = new SimpleDateFormat("dd/MM/yyyy ");
                                                    DateFormat time = new SimpleDateFormat("HH:mm:ss");
                                                    //get current date time with Date()
                                                    Date datevar = new Date();
                                                    ChatModel chatModel = new ChatModel(SigninActivity.myemail, m_Text, arrayOfUsers.get(i), date.format(datevar), time.format(datevar));
                                                    f = new Firebase(Constants.myid + "/chats");
                                                    f2 = new Firebase(Constants.invites + "/" + Constants.convert1(arrayOfUsers.get(i)) + "/chats");
                                                    //f=new Firebase(Constants.invites+"/"+Constants.convert1(arrayOfUsers.get(i))+"/chats/"+Constants.convert1(SigninActivity.myemail));
                                                    f.push().setValue(chatModel);
                                                    f2.push().setValue(chatModel);
                                                    i++;
                                                }
                                                Toast.makeText(MyClientsActivity.this, "Sent to all", Toast.LENGTH_SHORT).show();
                                            }
                                            else
                                                Toast.makeText(MyClientsActivity.this, "No Clients available", Toast.LENGTH_SHORT).show();

                                        }
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
                        return  true;
                    }
                });popupMenu.show();

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_clients, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent=new Intent(this,AboutUs.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.manager_panel) {
            Intent i= new Intent(this,ManagerPanelActivity.class);
            startActivity(i);
        } else if (id == R.id.driver_panel) {
            Intent i= new Intent(this,DriverPanelActivity.class);
            startActivity(i);
        } else if (id == R.id.client_panel) {
            Intent i= new Intent(this,ClientPanelActivity.class);
            startActivity(i);
        } else if (id == R.id.invites) {
            Intent i= new Intent(this,InvitesActivity.class);
            startActivity(i);
        } else if (id == R.id.my_drivers) {
            Intent i= new Intent(this,MyDriversActivity.class);
            startActivity(i);
        } else if (id == R.id.my_clients) {
            //cfvf
        } else if (id == R.id.my_managers) {
            Intent i= new Intent(this,MyManagersActivity.class);
            startActivity(i);
        } else if (id == R.id.settings) {
            Intent i= new Intent(this,SettingsActivity.class);
            startActivity(i);
        }
        else if (id == R.id.logout) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.clear().commit();
            Intent i= new Intent(this,SigninActivity.class);
            stopService(ManagerPanelActivity.intent1);
            startActivity(i);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
