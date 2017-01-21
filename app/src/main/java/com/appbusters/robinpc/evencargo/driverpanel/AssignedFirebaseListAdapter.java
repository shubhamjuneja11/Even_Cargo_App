package com.appbusters.robinpc.evencargo.driverpanel;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.appbusters.robinpc.evencargo.R;
import com.appbusters.robinpc.evencargo.orders.OrderClass;
import com.appbusters.robinpc.evencargo.siginsignup.SigninActivity;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * This class is a generic way of backing an Android ListView with a Firebase location.
 * It handles all of the child events at the given Firebase location. It marshals received data into the given
 * class type. Extend this class and provide an implementation of <code>populateView</code>, which will be given an
 * instance of your list item mLayout and an instance your class that holds your data. Simply populate the view however
 * you like and this class will handle updating the list as the data changes.
 *
 * @param <T> The class type to use as a model for the data contained in the children of the given Firebase location
 */
public  class AssignedFirebaseListAdapter<T> extends BaseAdapter {

    private Query mRef;
    private Class<T> mModelClass;
    private int mLayout;
    private LayoutInflater mInflater;
    private List<T> mModels;
    private List<String> mKeys;
    private ChildEventListener mListener;


    /**
     * @param mRef        The Firebase location to watch for data changes. Can also be a slice of a location, using some
     *                    combination of <code>limit()</code>, <code>startAt()</code>, and <code>endAt()</code>,
     * @param mModelClass Firebase will marshall the data at a location into an instance of a class that you provide
     * @param mLayout     This is the mLayout used to represent a single list item. You will be responsible for populating an
     *                    instance of the corresponding view with the data from an instance of mModelClass.
     * @param activity    The activity containing the ListView
     */

    public AssignedFirebaseListAdapter(Query mRef, Class<T> mModelClass, int mLayout, Activity activity) {
        this.mRef = mRef;
        this.mModelClass = mModelClass;
        this.mLayout = mLayout;
        mInflater = activity.getLayoutInflater();
        mModels = new ArrayList<T>();
        mKeys = new ArrayList<String>();
        // Look for all child events. We will then map them to our own internal ArrayList, which backs ListView
        mListener = this.mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
Log.e("is added","");

                String key = dataSnapshot.getKey();
                T model = dataSnapshot.getValue(AssignedFirebaseListAdapter.this.mModelClass);
                OrderClass orderClass=(OrderClass)model;
                if(orderClass.getdriver().equals(SigninActivity.myemail)&&(orderClass.getstatus()==1)&&orderClass.getD()==1) {
                    Log.e("statuscactive",String.valueOf(orderClass.getstatus()));
                    //if (previousChildName == null) {
                        mModels.add(0, model);
                        mKeys.add(0, key);
                  /*  } else {
                        int previousIndex = mKeys.indexOf(previousChildName);
                        int nextIndex = previousIndex + 1;
                        if (nextIndex == mModels.size()) {
                            mModels.add(model);
                            mKeys.add(key);
                        } else {
                            mModels.add(nextIndex, model);
                            mKeys.add(nextIndex, key);
                        }
                    }*/
               /* mModels.add(model);
                mKeys.add(key);*/


                } notifyDataSetChanged();   //dont move me else u r screwed
                }


            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                // One of the mModels changed. Replace it in our list and name mapping

              /*  String key = dataSnapshot.getKey();
                T newModel = dataSnapshot.getValue(ActiveFirebaseListAdapter.this.mModelClass);
                OrderClass orderClass=(OrderClass)newModel;
                if(orderClass.getmanager().equals(SigninActivity.myemail)&&(orderClass.getstatus()==2||orderClass.getstatus()==3)) {
                    int index = mKeys.indexOf(key);
                    Log.e("indexrfefr:", String.valueOf(index));
                    mModels.set(index, newModel);
                    Log.e("hi", "2");
                    notifyDataSetChanged();
                }*/

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

                // A model was removed from the list. Remove it from our list and the name mapping
                String key = dataSnapshot.getKey();
                OrderClass orderClass = dataSnapshot.getValue(OrderClass.class);
                if (orderClass.getdriver().equals(SigninActivity.myemail) && (orderClass.getstatus() == 1)&&orderClass.getD()==1) {
                    int index = mKeys.indexOf(key);
                    Log.e("hi", "3");
                    mKeys.remove(index);
                    mModels.remove(index);


                } notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {

                // A model changed position in the list. Update our list accordingly
               /* String key = dataSnapshot.getKey();
                T newModel = dataSnapshot.getValue(ActiveFirebaseListAdapter.this.mModelClass);
                int index = mKeys.indexOf(key);Log.e("hi","4");
                mModels.remove(index);
                mKeys.remove(index);
                if (previousChildName == null) {
                    mModels.add(0, newModel);
                    mKeys.add(0, key);
                } else {
                    int previousIndex = mKeys.indexOf(previousChildName);
                    int nextIndex = previousIndex + 1;
                    if (nextIndex == mModels.size()) {
                        mModels.add(newModel);
                        mKeys.add(key);
                    } else {
                        mModels.add(nextIndex, newModel);
                        mKeys.add(nextIndex, key);
                    }
                }
                notifyDataSetChanged();*/
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
               // Log.e("GotFirebaseListAdapter", "Listen was cancelled, no more updates will occur");
            }

        });
    }

    public void cleanup() {
        // We're being destroyed, let go of our mListener and forget about all of the mModels
       // mRef.removeEventListener(mListener);
        mModels.clear();
        mKeys.clear();
    }

    @Override
    public int getCount() {
        return mModels.size();
    }

    @Override
    public Object getItem(int i) {
        return mModels.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = mInflater.inflate(mLayout, viewGroup, false);
        }

        T model = mModels.get(i);
        Log.e("hi","60.90");
        // Call out to subclass to marshall this model into the provided view
        populateView(view, model);
        return view;
    }

    /**
     * Each time the data at the given Firebase location changes, this method will be called for each item that needs
     * to be displayed. The arguments correspond to the mLayout and mModelClass given to the constructor of this class.
     * <p/>
     * Your implementation should populate the view using the data contained in the model.
     *
     * @param v     The view to populate
     * @param model The object containing the data used to populate the view
     */
    protected void populateView(View v, T model){
        String driver,client,pdate,ptime;
        int status;
        long ordern;
        OrderClass order=(OrderClass)model;
        View view=v;
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
            case 3:statustext.setText("PICKED");break;
            case 2:statustext.setText("STARTED");break;

            //pending
            case 0:statustext.setText("UNASSIGNED");break;
            case 1:statustext.setText("ASSIGNED");break;

            //completedOrderAdapter


            case 4:statustext.setText("NOT DELIVERED");break;
            case 5:statustext.setText("DELIVERED");break;
            case 6:statustext.setText("CANCELLED");break;

        }
        t1.setText(driver);
        t2.setText(client);
        t3.setText(pdate);
        t4.setText(ptime);
    }
}
