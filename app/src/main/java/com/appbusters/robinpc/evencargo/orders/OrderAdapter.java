package com.appbusters.robinpc.evencargo.orders;

/**
 * Created by junejaspc on 29/9/16.
 */
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.appbusters.robinpc.evencargo.R;
import com.firebase.client.Query;

/**
 *
 * This class is an example of how to use GotFirebaseListAdapter. It uses the <code>Chat</code> class to encapsulate the
 * data for each individual chat message
 */
public class OrderAdapter extends OrderFirebaseListAdapter<OrderClass> {

    // The mUsername for this client. We use this to indicate which messages originated from this user
    private String mUsername;

    public OrderAdapter(Query ref, Activity activity, int layout) {

        super(ref, OrderClass.class, layout, activity);  Log.e("adapter","done");
        //this.mUsername = mUsername;
    }

    /**
     * Bind an instance of the <code>Chat</code> class to our view. This method is called by <code>GotFirebaseListAdapter</code>
     * when there is a data change, and we are given an instance of a View that corresponds to the layout that we passed
     * to the constructor, as well as a single <code>Chat</code> instance that represents the current data to bind.
     *
     * @param view A view instance corresponding to the layout we passed to the constructor.
     *
     */
    @Override
    protected void populateView(View view,OrderClass order) {Log.e("yes","yesssssssssssss");
        // Map a Chat object to an entry in our listview
        //String sender,reciever,post;
        String driver,client,pdate,ptime;
        int status;
        long ordern;
        //manager=order.getmanager();
        driver=order.getdriver();
        client=order.getclient();
        pdate=order.getpdate();
        ptime=order.getptime();
        status=order.getstatus();
        ordern=order.getorderno();
        /*reciever=reciever.replaceAll("-1-",".");
        reciever=reciever.replaceAll("-2-","$");
        reciever=reciever.replaceAll("-3-","#");*/
        TextView t1,t2,t3,t4,statustext,orderno;
        t1=(TextView)view.findViewById(R.id.driveremailid);
        t2=(TextView)view.findViewById(R.id.clientemailid);
        t3=(TextView)view.findViewById(R.id.date);
        t4=(TextView)view.findViewById(R.id.time);
        orderno=(TextView)view.findViewById(R.id.orderno);
        orderno.setText("#"+String.valueOf(ordern));
        statustext=(TextView)view.findViewById(R.id.status);

        switch (status){
            //started
            case 6:statustext.setText("PICKED");break;
            case 8:statustext.setText("STARTED");break;

            //pending
            case 0:statustext.setText("UNASSIGNED");break;
            case 1:statustext.setText("ASSIGNED");break;

            //completedOrderAdapter
            case 4:statustext.setText("CANCELLED");break;
            case 2:statustext.setText("COMPLETED");break;
            case 5:statustext.setText("NOT DELIVERED");break;

            case 7:statustext.setText("DELIVERED");break;

        }
        t1.setText(driver);
        t2.setText(client);
        t3.setText(pdate);
        t4.setText(ptime);
        // If the message was sent by this user, color it differently
        // Log.e("baar",author+"   "+mUsername+"  "+reciever+"  ");


    }
}
