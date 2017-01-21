package com.appbusters.robinpc.evencargo.invites;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.appbusters.robinpc.evencargo.Constants;
import com.appbusters.robinpc.evencargo.R;
import com.appbusters.robinpc.evencargo.sendinginvites.SentFirebaseListAdapter;
import com.appbusters.robinpc.evencargo.services.NotificationService;
import com.appbusters.robinpc.evencargo.siginsignup.SigninActivity;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.List;

public class SendInviteActivity extends AppCompatActivity {
Firebase invites=new Firebase(Constants.invites);
    String from,to,post;
    InviteData inviteData;
    RadioGroup radioGroup;
    Firebase firebase;
    ArrayList<String> al;
    ArrayAdapter ap;
    AutoCompleteTextView at;
    public List<String> mKeys;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Send Invite");
        al=new ArrayList<>();
        mKeys= SentFirebaseListAdapter.mKeys;
        setContentView(R.layout.activity_send_invite);
        firebase=new Firebase(Constants.registered_users);
        at=(AutoCompleteTextView)findViewById(R.id.emailid);
        String s=SigninActivity.myemail;
        if(s==null)
            s= NotificationService.si;
        final String h=Constants.convert1(s);
        firebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
             String a=dataSnapshot.getKey();
                if(!h.equals(a))
                al.add(Constants.convert2(a));
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
      ap=new ArrayAdapter(this,android.R.layout.simple_list_item_1,al);
        at.setAdapter(ap);
        at.setThreshold(1);
    }
    public void sendinvites(View view){

        Log.e("hi","kjkj");
        to=((EditText)findViewById(R.id.emailid)).getText().toString();
        if(al.contains(to)) {
            from = SigninActivity.myemail;

            //removing @ , . symbol to add in firebase as it doesnt allow symbol as key

            to=Constants.convert1(to);
            from=Constants.convert1(from);
            Log.e("from", from);
            radioGroup = (RadioGroup) findViewById(R.id.radiogroup1);
            int id = radioGroup.getCheckedRadioButtonId();
            if (id == R.id.forclient) post = "Client";
            else post = "Driver";
            inviteData = new InviteData(from, to, post, 0);
            Log.e("2345", "456");
            if(mKeys.contains(to+" "+post))
            {Snackbar.make(view,"Already Invited",Snackbar.LENGTH_LONG).show();

            }
            else {
                invites.child(inviteData.getreciever()).child("Recieved").child(from + " " + post).setValue(inviteData);
                Log.e("Sender", inviteData.getsender());
                invites.child(inviteData.getsender()).child("Sent").child(to + " " + post).setValue(inviteData);
                Log.e("hi2", "kjkj");
                Toast.makeText(this, "Invite Sent", Toast.LENGTH_SHORT).show();
            }
        }
        else Toast.makeText(this,"Invite couldn't be sent.",Toast.LENGTH_SHORT).show();
    }
}
