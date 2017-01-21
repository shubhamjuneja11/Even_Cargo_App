package com.appbusters.robinpc.evencargo.sendinginvites;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.appbusters.robinpc.evencargo.Constants;
import com.appbusters.robinpc.evencargo.invites.InviteData;
import com.appbusters.robinpc.evencargo.R;
import com.firebase.client.Query;

/**
 *
 * This class is an example of how to use GotFirebaseListAdapter. It uses the <code>Chat</code> class to encapsulate the
 * data for each individual chat message
 */
public class SentInvitesAdapter extends SentFirebaseListAdapter<InviteData> {

    // The mUsername for this client. We use this to indicate which messages originated from this user
    private String mUsername;

    public SentInvitesAdapter(Query ref, Activity activity, int layout, String mUsername) {

        super(ref, InviteData.class, layout, activity);  Log.e("adapter","done");
        this.mUsername = mUsername;
    }

    /**
     * Bind an instance of the <code>Chat</code> class to our view. This method is called by <code>GotFirebaseListAdapter</code>
     * when there is a data change, and we are given an instance of a View that corresponds to the layout that we passed
     * to the constructor, as well as a single <code>Chat</code> instance that represents the current data to bind.
     *
     * @param view A view instance corresponding to the layout we passed to the constructor.
     * @param chat An instance representing the current state of a chat message
     */
    @Override
    protected void populateView(View view,InviteData chat) {
        // Map a Chat object to an entry in our listview
        String reciever,post;
        int status;
        status=chat.getStatus();
        post=chat.getpost();
        reciever=chat.getreciever();
        reciever= Constants.convert2(reciever);
            TextView statustext = (TextView) view.findViewById(R.id.status);
            TextView senderemail=(TextView)view.findViewById(R.id.inviteemailid);
        TextView posttextview=(TextView)view.findViewById(R.id.post);
            switch (status){
                case 0:statustext.setText("PENDING");break;
                case 1:statustext.setText("ACCEPTED");break;
                case 2:statustext.setText("REFUSED");break;
                case 4:statustext.setText("CANCELLED");break;
            }
        senderemail.setText(reciever);
        posttextview.setText(post);
        // If the message was sent by this user, color it differently
       // Log.e("baar",author+"   "+mUsername+"  "+reciever+"  ");


    }
}
