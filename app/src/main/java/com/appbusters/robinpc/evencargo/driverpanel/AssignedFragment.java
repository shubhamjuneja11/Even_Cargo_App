package com.appbusters.robinpc.evencargo.driverpanel;


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
public class AssignedFragment extends Fragment {


    Firebase f;
    ListView listView;
    TextView tv,tv2;
   AssignedFirebaseListAdapter orderAdapter;
    public AssignedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootview= inflater.inflate(R.layout.fragment_assigned, container, false);
        tv=(TextView)rootview.findViewById(R.id.tv);
        tv2=(TextView)rootview.findViewById(R.id.tv2);
        f=new Firebase(Constants.orders);
        listView=(ListView)rootview.findViewById(R.id.assigneddriverlistview);
        orderAdapter=new AssignedFirebaseListAdapter(f.limit(50), OrderClass.class,R.layout.orderitem,getActivity());
        Log.e("im ","the boss");
        if(orderAdapter==null)
            Log.e("Sorry","shaktimaan2");
        if(listView==null)
                Log.e("Sorry","shaktimaan12345");
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
                intent.putExtra("decide",4);
                startActivity(intent);

            }
        });
        return rootview;

    }

}
