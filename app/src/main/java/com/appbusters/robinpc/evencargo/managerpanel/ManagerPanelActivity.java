package com.appbusters.robinpc.evencargo.managerpanel;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
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
import android.widget.Toast;

import com.appbusters.robinpc.evencargo.Constants;
import com.appbusters.robinpc.evencargo.LocationClass;
import com.appbusters.robinpc.evencargo.Manifest;
import com.appbusters.robinpc.evencargo.chat.ChatPage;
import com.appbusters.robinpc.evencargo.clientpanel.AboutUs;
import com.appbusters.robinpc.evencargo.clientpanel.ClientPanelActivity;
import com.appbusters.robinpc.evencargo.driverpanel.DriverPanelActivity;
import com.appbusters.robinpc.evencargo.invites.InvitesActivity;
import com.appbusters.robinpc.evencargo.services.LocationService;
import com.appbusters.robinpc.evencargo.mypeople.MyClientsActivity;
import com.appbusters.robinpc.evencargo.mypeople.MyDriversActivity;
import com.appbusters.robinpc.evencargo.mypeople.MyManagersActivity;
import com.appbusters.robinpc.evencargo.services.NotificationService;
import com.appbusters.robinpc.evencargo.R;
import com.appbusters.robinpc.evencargo.SettingsActivity;
import com.appbusters.robinpc.evencargo.orders.ManagerOrder;
import com.appbusters.robinpc.evencargo.siginsignup.SigninActivity;
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
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class ManagerPanelActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    Intent intent=null;
    public static Intent intent1;

    @Override
    protected void onStart() {
        super.onStart();
        int perm= ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION);
        if(perm==PackageManager.PERMISSION_GRANTED) {
            intent = new Intent(this, LocationService.class);
            startService(intent);
           // Toast.makeText(this, "Location is On", Toast.LENGTH_SHORT).show();
        }
        else{
            ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},1);
            //Toast.makeText(this, "No Location", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Constants.from==null)
        {SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
            String email=sharedPreferences.getString("email","");
            Constants.from=email;Constants.fun();
        }

Log.e("ffffffffff",Constants.from);

       intent1=new Intent(this,NotificationService.class);
        startService(intent1);
        Log.e("i am ","working");

       int flag=getIntent().getIntExtra("flag",0);

        if(flag==1){
            String si,sender;
            si=getIntent().getStringExtra("FROM_USER");
            sender=getIntent().getStringExtra("TO_USER");
            Intent intent1=new Intent(this, ChatPage.class);
            intent1.putExtra("FROM_USER", si);
            intent1.putExtra("TO_USER", sender);
            startActivity(intent1);
        }
        else if(flag==2)
        {
            Intent intent=new Intent(ManagerPanelActivity.this,InvitesActivity.class);
            startActivity(intent);
        }
        else if(flag==3)
        {
            Intent intent=new Intent(ManagerPanelActivity.this,DriverPanelActivity.class);
            startActivity(intent);
        }
        else if(flag==4)
        {
            Intent intent=new Intent(ManagerPanelActivity.this,ClientPanelActivity.class);
            startActivity(intent);
        }

        //SigninActivity.myemail="a@gmail.com";
        setContentView(R.layout.activity_manager_panel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ManagerPanelActivity.this, ManagerOrder.class);
                startActivity(intent);
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        Log.e("frtrtgrgrtg","vtvrv");


       // startService(intent);
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
        getMenuInflater().inflate(R.menu.manager_panel, menu);
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
            //dd
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
            Intent i= new Intent(this,MyClientsActivity.class);
            startActivity(i);
        } else if (id == R.id.my_managers) {
            Intent i= new Intent(this,MyManagersActivity.class);
            startActivity(i);
        } else if (id == R.id.settings) {
            Intent i= new Intent(this,SettingsActivity.class);
            startActivity(i);
        }
        else if (id == R.id.logout) {
   Log.e("Logging","out");
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.clear().commit();
            Intent i= new Intent(this,SigninActivity.class);
            stopService(intent1);
            startActivity(i);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new ActiveManagerFragment(), "ACTIVE");
        adapter.addFrag(new PendingManagerFragment(), "PENDING");
        adapter.addFrag(new CompletedManagerFragment(), "COMPLETED");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("Quit","i quit");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("Quit","i quit 22222");
        if(intent!=null)
        stopService(intent);


    }
}