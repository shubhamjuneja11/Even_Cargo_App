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
import android.widget.ListView;
import android.widget.TextView;

import com.appbusters.robinpc.evencargo.Constants;
import com.appbusters.robinpc.evencargo.R;
import com.appbusters.robinpc.evencargo.orders.DisplayOrder1;
import com.appbusters.robinpc.evencargo.orders.OrderAdapter;
import com.appbusters.robinpc.evencargo.orders.OrderClass;
import com.firebase.client.Firebase;


/**
 * A simple {@link Fragment} subclass.
 */
public class CompletedManagerFragment extends Fragment {

    Firebase f;
    ListView listView;
    TextView tv,tv2;
    CompletedFirebaseListAdapter orderAdapter;
    public CompletedManagerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview= inflater.inflate(R.layout.fragment_completed_manager, container, false);
        tv=(TextView)rootview.findViewById(R.id.tv);
        tv2=(TextView)rootview.findViewById(R.id.tv5);
        f=new Firebase("https://chat-6c23a.firebaseio.com/invites/Orders");
        orderAdapter=new CompletedFirebaseListAdapter(f.limit(50),OrderClass.class,R.layout.orderitem, getActivity());

        listView=(ListView)rootview.findViewById(R.id.completedmanagerlistview);

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
                intent.putExtra("decide",2);
                startActivity(intent);

            }
        });

        return  rootview;
    }

}
