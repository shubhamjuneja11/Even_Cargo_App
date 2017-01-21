package com.appbusters.robinpc.evencargo.invites;


import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.appbusters.robinpc.evencargo.Constants;
import com.appbusters.robinpc.evencargo.R;
import com.appbusters.robinpc.evencargo.services.NotificationService;
import com.appbusters.robinpc.evencargo.siginsignup.SigninActivity;
import com.appbusters.robinpc.evencargo.sendinginvites.SentInvitesAdapter;
import com.firebase.client.Firebase;


/**
 * A simple {@link Fragment} subclass.
 */
public class SentFragment extends Fragment {
    Firebase invites,invites2;
    ListView listView;
    String from;
  SentInvitesAdapter mChatListAdapter;

    public SentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        from= SigninActivity.myemail;
        if(from==null)
            from= NotificationService.si;
        from=from.replaceAll("\\.","-1-");
        from=from.replaceAll("\\$","-2-");
        from=from.replaceAll("\\#","-3-");
        // Inflate the layout for this fragment
        invites=new Firebase("https://chat-6c23a.firebaseio.com/invites").child(from).child("Sent");
        invites2=new Firebase("https://chat-6c23a.firebaseio.com/invites");
        final View rootview=inflater.inflate(R.layout.fragment_sent,container,false);
        listView=(ListView)rootview.findViewById(R.id.inviteslist2);
        //listView.setAdapter(new ArrayAdapter<InviteData>(getContext(),R.layout.inviteslistitem,data));
        /*final CustomInvitesAdapter customInvitesAdapter=new CustomInvitesAdapter(getContext(),R.layout.inviteslistitem,data);
        listView.setAdapter(customInvitesAdapter);*/
        Log.e("here","12");
       mChatListAdapter = new SentInvitesAdapter(invites.limit(50), getActivity(), R.layout.inviteslistitem,SigninActivity.myemail);
        listView.setAdapter(mChatListAdapter);
        Log.e("here","1234");
        mChatListAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(mChatListAdapter.getCount() - 1);
                Log.e("here","123456");
            }
        });
        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {   int status;
            TextView t1,t2;
            @Override
            public void onItemClick(AdapterView<?> adapterView, final View view, int i, long l) {
                t1=(TextView)view.findViewById(R.id.inviteemailid);
                t2=(TextView)view.findViewById(R.id.post);
                final String post=t2.getText().toString();
                Log.e("thisis:",t1.getText().toString());
                String to;
                to=t1.getText().toString();
                to=to.replaceAll("\\.","-1-");
                to=to.replaceAll("\\$","-2-");
                to=to.replaceAll("\\#","-3-");
                PopupMenu popupMenu=new PopupMenu(getContext(),rootview);
                popupMenu.getMenuInflater().inflate(R.menu.sentinvitesmenu,popupMenu.getMenu());

                final String finalTo = to;
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if(menuItem.getItemId()==R.id.reject) {
                            status = 4;
                            if(post.equals("Driver"))
                            {
                                //HashMap<String ,Object> hashMap=new HashMap<>();
                                //hashMap=invites2.child(from).child("MyManagers")
                                // hashMap.put(to,to);
                                invites2.child(from).child("MyDrivers").child(finalTo).setValue(1);
                                invites2.child(finalTo).child("MyManagers").child(from).setValue(1);
                            }
                            else{
                                invites2.child(from).child("MyManagers").child(finalTo).setValue(1);
                                invites2.child(finalTo).child("MyClients").child(from).setValue(1);
                            }
                        }
                        else if(menuItem.getItemId()==R.id.pending&& status==4)
                            status=0;



                        //invites=new Firebase(Constants.invites).child(from).child("Sent");
                        Log.e("Postttttttttt",t2.getText().toString());
                        invites2.child(finalTo).child("Recieved").child(from+" "+t2.getText().toString()).child("status").setValue(status);
                        invites2.child(from).child("Sent").child(finalTo +" "+t2.getText().toString()).child("status").setValue(status);
                        return true;
                    }
                });
                popupMenu.show();
                // TextView t1=(TextView)rootview.findViewById(R.id.inviteemailid);

            }



        });*/
        return rootview;
    }
    }

