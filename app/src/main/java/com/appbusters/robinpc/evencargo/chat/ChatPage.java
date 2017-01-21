package com.appbusters.robinpc.evencargo.chat;

import android.content.Context;
import android.database.DataSetObserver;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.appbusters.robinpc.evencargo.Constants;
import com.appbusters.robinpc.evencargo.R;
import com.firebase.client.Firebase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChatPage extends AppCompatActivity {
    public static String fromuser,message;
    static String touser;
    EditText editText;
    static ListView listView;
    Display display;
    Firebase child2,chatnode;
    ChatModel model;
    static int width,height;
    static ChatListAdapter mChatListAdapter;
    @Override
    public void onStart() {
        super.onStart();
        Log.e("Hi","start");
         display = getWindowManager().getDefaultDisplay();
         width = display.getWidth();
        fromuser=getIntent().getStringExtra("FROM_USER");
        touser=getIntent().getStringExtra("TO_USER");
        setTitle(touser);
        chatnode= new Firebase(Constants.myid+"/chats");
        child2=new Firebase(Constants.invites+"/"+Constants.convert1(touser)+"/chats");
        listView = (ListView)findViewById(R.id.message_list);
        mChatListAdapter = new ChatListAdapter(chatnode.limit(50), this, R.layout.item, fromuser);
        listView.setAdapter(mChatListAdapter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("Hi","create");
        setContentView(R.layout.activity_chat_page);
        editText=(EditText)findViewById(R.id.new_message);

    }
    public void send(View view){
        message=editText.getText().toString();
        message=message.trim();
        height=display.getHeight();
        if(!message.equals("")) {
            DateFormat date = new SimpleDateFormat("dd/MM/yyyy ");
            DateFormat time = new SimpleDateFormat("HH:mm:ss");
            Date datevar = new Date();
            model = new ChatModel(fromuser, message, touser, date.format(datevar), time.format(datevar));
            chatnode.push().setValue(model);
            child2.push().setValue(model);
            editText.setText("");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mChatListAdapter.cleanup();
        Log.e("Hi","stop");
    }


}
