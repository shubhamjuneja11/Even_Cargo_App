package com.appbusters.robinpc.evencargo.managerpanel;


import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.appbusters.robinpc.evencargo.Constants;
import com.appbusters.robinpc.evencargo.R;
import com.appbusters.robinpc.evencargo.orders.DisplayOrder1;
import com.appbusters.robinpc.evencargo.orders.OrderAdapter;
import com.appbusters.robinpc.evencargo.orders.OrderClass;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;


/**
 * A simple {@link Fragment} subclass.
 */
public class ActiveManagerFragment extends Fragment {
Firebase f;
ListView listView;
    TextView tv,tv2;
    ActiveFirebaseListAdapter orderAdapter;
    public ActiveManagerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootview= inflater.inflate(R.layout.fragment_active_manager, container, false);
        tv=(TextView)rootview.findViewById(R.id.tv);
        tv2=(TextView)rootview.findViewById(R.id.tv2);
        f=new Firebase("https://chat-6c23a.firebaseio.com/invites/Orders");
        listView=(ListView)rootview.findViewById(R.id.activemanagerlistview);
        orderAdapter=new ActiveFirebaseListAdapter<>(f.limit(50),OrderClass.class,R.layout.orderitem,getActivity());
        listView.setAdapter(orderAdapter);
        orderAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(orderAdapter.getCount() - 1);
                Log.e("here","123456");
                if(orderAdapter.getCount()!=0) {
                    tv.setVisibility(View.INVISIBLE);
                    tv2.setVisibility(View.INVISIBLE);
                }
                else {
                    tv.setVisibility(View.VISIBLE);
                    tv2.setVisibility(View.VISIBLE);
                }

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
              String orderno=((TextView)view.findViewById(R.id.orderno)).getText().toString();
                Intent intent=new Intent(getContext(), DisplayOrder1.class);
                intent.putExtra("orderno",orderno);
                intent.putExtra("decide",0);
                startActivity(intent);

            }
        });
        return rootview;


    }


}
