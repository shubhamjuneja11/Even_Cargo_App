package com.appbusters.robinpc.evencargo;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.v7.app.ActionBar;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.List;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p/>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends AppCompatActivity{
EditText t,t2;
    Firebase firebase;
    String name,phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        setTitle("Settings");
        t=(EditText)findViewById(R.id.name1);
        t2=(EditText)findViewById(R.id.phoneno1);
        firebase=new Firebase(Constants.registered_users+"/"+Constants.from);
        firebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.getKey().equals("name"))
                {name=dataSnapshot.getValue(String.class);t.setText(name);}
                if(dataSnapshot.getKey().equals("phone"))
                { phone=dataSnapshot.getValue(String.class);t2.setText(phone);}
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
              if(dataSnapshot.getKey().equals("name"))
              {name=dataSnapshot.getValue(String.class);t.setText(name);}
                if(dataSnapshot.getKey().equals("phone"))
                { phone=dataSnapshot.getValue(String.class);t2.setText(phone);}

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
    public void funz(View view){
        String text,text1;
        text=t.getText().toString();
        text1=t2.getText().toString();
        if(text.equals("-")&&text1.equals("-")){
            Toast.makeText(this, "Enter details first", Toast.LENGTH_SHORT).show();
        }
        else{
            firebase.child("name").setValue(text);
            firebase.child("phone").setValue(text1);
            Toast.makeText(this, "Details Saved", Toast.LENGTH_SHORT).show();
        }
    }
}
