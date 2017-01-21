package com.appbusters.robinpc.evencargo;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.appbusters.robinpc.evencargo.chat.ChatPage;
import com.appbusters.robinpc.evencargo.orders.DisplayOrder1;
import com.appbusters.robinpc.evencargo.registeredusers.Users;
import com.appbusters.robinpc.evencargo.siginsignup.SigninActivity;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;

public class DisplayDetails extends AppCompatActivity {
Firebase f;
FloatingActionButton fb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final TextView t1,t2,t3;

        super.onCreate(savedInstanceState);
        setTitle("Details");
        setContentView(R.layout.activity_display_details);
        t1=(TextView)findViewById(R.id.name);
        t2=(TextView)findViewById(R.id.phoneno);
        t3=(TextView)findViewById(R.id.emaildetail);
        final String[] email = {getIntent().getStringExtra("email")};
        final String[] name = {new String()};
        final String[] phone = new String[1];
        String from= email[0];
        from=from.replaceAll("\\.","-1-");
        from=from.replaceAll("\\$","-2-");
        from=from.replaceAll("\\#","-3-");
        t3.setText(email[0]);
        f=new Firebase(Constants.registered_users);
        f=f.child(from);
        f.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.e("haha",dataSnapshot.getKey());
                if(dataSnapshot.getKey().equals("name"))
                { name[0] =dataSnapshot.getValue(String.class);t1.setText(name[0]);}
                else if(dataSnapshot.getKey().equals("phone"))
                {phone[0] =dataSnapshot.getValue(String.class);
                t2.setText(phone[0]);
                }

               // Toast.makeText(DisplayDetails.this,users.getemail(),Toast.LENGTH_SHORT).show();
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
        fb=(FloatingActionButton)findViewById(R.id.fb);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu=new PopupMenu(DisplayDetails.this,view);
                popupMenu.getMenuInflater().inflate(R.menu.dislpaydetailsmenu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.viewlocation:seelocation();break;
                            case R.id.instantmessage:
                            {
                                Intent intent=new Intent(DisplayDetails.this, ChatPage.class);
                                intent.putExtra("FROM_USER", SigninActivity.myemail);
                                intent.putExtra("TO_USER",getIntent().getStringExtra("email"));
                                startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

    }
    public void seelocation(){
        int i;
        PersonLocation p;
        double lat=0,lng=0;
        String email=getIntent().getStringExtra("email");
        ArrayList<PersonLocation>pl=DisplayOrder1.pl;
        for(i=0;i< pl.size();i++) {
            p = pl.get(i);
            if(p.name.equals(email))
            {
                lat=p.latitude;
                lng=p.longitude;
            }
        }
        String map = "http://www.google.com/maps/place/"+lat+","+lng;
        Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(map));
        startActivity(in);
    }

}
