package com.appbusters.robinpc.evencargo.users;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.appbusters.robinpc.evencargo.R;

import java.util.ArrayList;

/**
 * Created by junejaspc on 26/9/16.
 */
public class UsersAdapter extends ArrayAdapter<String> {
ArrayList<String>users;
    public UsersAdapter(Context context, ArrayList<String> users) {

        super(context, 0, users);
        this.users=users;

    }



    @Override

    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position

        String user = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view

        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.commonlayout, parent, false);

        }

        // Lookup view for data population

        TextView Name = (TextView) convertView.findViewById(R.id.useremail);

       // TextView tvHome = (TextView) convertView.findViewById(R.id.tvHome);

        // Populate the data into the template view using the data object

        Name.setText(users.get(position));

        //tvHome.setText(user.hometown);

        // Return the completed view to render on screen

        return convertView;

    }

}
