package com.appbusters.robinpc.evencargo.invites;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.appbusters.robinpc.evencargo.R;
import com.appbusters.robinpc.evencargo.SettingsActivity;
import com.appbusters.robinpc.evencargo.clientpanel.AboutUs;
import com.appbusters.robinpc.evencargo.clientpanel.ClientPanelActivity;
import com.appbusters.robinpc.evencargo.driverpanel.DriverPanelActivity;
import com.appbusters.robinpc.evencargo.managerpanel.ManagerPanelActivity;
import com.appbusters.robinpc.evencargo.mypeople.MyClientsActivity;
import com.appbusters.robinpc.evencargo.mypeople.MyDriversActivity;
import com.appbusters.robinpc.evencargo.mypeople.MyManagersActivity;
import com.appbusters.robinpc.evencargo.siginsignup.SigninActivity;

import java.util.ArrayList;
import java.util.List;

public class InvitesActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    public static boolean decide=true;
    /*ListView listView;
    ArrayList<InviteData> data;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invites);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(InvitesActivity.this,SendInviteActivity.class);
                startActivity(intent);

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
            Intent i= new Intent(this,ManagerPanelActivity.class);
            startActivity(i);
        } else if (id == R.id.driver_panel) {
            Intent i= new Intent(this,DriverPanelActivity.class);
            startActivity(i);
        } else if (id == R.id.client_panel) {
            Intent i= new Intent(this,ClientPanelActivity.class);
            startActivity(i);
        } else if (id == R.id.invites) {
            //cxc
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

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new GotFragment(), "GOT");
        adapter.addFrag(new SentFragment(), "SENT");
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
}