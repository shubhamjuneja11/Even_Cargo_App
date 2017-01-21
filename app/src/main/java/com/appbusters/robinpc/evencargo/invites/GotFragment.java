package com.appbusters.robinpc.evencargo.invites;


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
import com.appbusters.robinpc.evencargo.invitesadapter.GotInvitesAdapter;
import com.firebase.client.Firebase;


/**
 * A simple {@link Fragment} subclass.
 */
public class GotFragment extends Fragment {
    Firebase invites,invites2,f1,f2;
    ListView listView;
    GotInvitesAdapter mChatListAdapter;
    public GotFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String from= SigninActivity.myemail;
        if(from==null)
            from=NotificationService.si;
        Constants.fun();
       from=Constants.convert1(from);
        // Inflate the layout for this fragment
        if(from==null)Log.e("fuck u","bro");
        if(Constants.invites==null)Log.e("fuck u ","too");
        invites=new Firebase("https://chat-6c23a.firebaseio.com/invites").child(from).child("Recieved");
        invites2=new Firebase("https://chat-6c23a.firebaseio.com/invites");
        final View rootview=inflater.inflate(R.layout.fragment_got,container,false);
        listView=(ListView)rootview.findViewById(R.id.inviteslist);
        //listView.setAdapter(new ArrayAdapter<InviteData>(getContext(),R.layout.inviteslistitem,data));
        /*final CustomInvitesAdapter customInvitesAdapter=new CustomInvitesAdapter(getContext(),R.layout.inviteslistitem,data);
        listView.setAdapter(customInvitesAdapter);*/
        Log.e("here","12");
        mChatListAdapter = new GotInvitesAdapter(invites.limit(50), getActivity(), R.layout.inviteslistitem,SigninActivity.myemail);
        listView.setAdapter(mChatListAdapter);
        Log.e("here","1234");
        /*mChatListAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(mChatListAdapter.getCount() - 1);
                Log.e("here","123456");
            }
        });*/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {   int status;
            TextView t1,t2;
            String post,status2;
            @Override
            public void onItemClick(AdapterView<?> adapterView, final View view, int i, long l) {
                t1=(TextView)view.findViewById(R.id.inviteemailid);
                t2=(TextView)view.findViewById(R.id.post);
                post=t2.getText().toString();
                status2=((TextView)view.findViewById(R.id.status)).getText().toString();
                Log.e("thisis:",t1.getText().toString());
               final  String to,from;
                to=Constants.convert1(t1.getText().toString());
                //from <->to
                from=Constants.convert1(SigninActivity.myemail);

                PopupMenu popupMenu=new PopupMenu(getContext(),rootview);
                if(status2.equals("PENDING"))
                popupMenu.getMenuInflater().inflate(R.menu.invitesmenu,popupMenu.getMenu());
                else  if(status2.equals("ACCEPTED"))popupMenu.getMenuInflater().inflate(R.menu.invitesmenu3,popupMenu.getMenu());
                else popupMenu.getMenuInflater().inflate(R.menu.invitesmenu2,popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        if(menuItem.getItemId()==R.id.accept)
                        {status=1;
                            if(post.equals("Driver"))
                            {
                               //HashMap<String ,Object> hashMap=new HashMap<>();
                                //hashMap=invites2.child(from).child("MyManagers")
                               // hashMap.put(to,to);
                                invites2.child(from).child("MyManagers").child(to).setValue(1);
                                invites2.child(to).child("MyDrivers").child(from).setValue(1);

                            }
                            else{
                                invites2.child(from).child("MyManagers").child(to).setValue(1);
                                invites2.child(to).child("MyClients").child(from).setValue(1);
                            }
                        }
                        else if(menuItem.getItemId()==R.id.reject) {
                            status = 2;
                            if(post.equals("Driver"))
                            {
                                //HashMap<String ,Object> hashMap=new HashMap<>();
                                //hashMap=invites2.child(from).child("MyManagers")
                                // hashMap.put(to,to);
                                invites2.child(from).child("MyManagers").child(to).setValue(0);
                                invites2.child(to).child("MyDrivers").child(from).setValue(0);
                            }
                            else{
                                invites2.child(from).child("MyClients").child(to).setValue(0);
                                invites2.child(to).child("MyManagers").child(from).setValue(0);
                            }
                        }


                        Log.e("Postttttttttt",t2.getText().toString());

                        invites2.child(to).child("Sent").child(from+" "+t2.getText().toString()).child("status").setValue(status);
                        invites2.child(from).child("Recieved").child(to+" "+t2.getText().toString()).child("status").setValue(status);
                        invites2.child(to).child("Sent").child(from+" "+t2.getText().toString()).child("ns").setValue(1);

                        return true;
                    }
                });
                popupMenu.show();
               // TextView t1=(TextView)rootview.findViewById(R.id.inviteemailid);

            }



        });
        return rootview;
    }

}
