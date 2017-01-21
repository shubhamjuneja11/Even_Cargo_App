package com.appbusters.robinpc.evencargo.chat;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ScrollView;
import android.widget.TextView;

import com.appbusters.robinpc.evencargo.R;
import com.appbusters.robinpc.evencargo.siginsignup.SigninActivity;
import com.firebase.client.Query;

/**
 *
 * This class is an example of how to use FirebaseListAdapter. It uses the <code>Chat</code> class to encapsulate the
 * data for each individual chat message
 */
public class ChatListAdapter extends FirebaseListAdapter<ChatModel> {

    // The mUsername for this client. We use this to indicate which messages originated from this user
    private String mUsername;
    int width,x,y;

    public ChatListAdapter(Query ref, Activity activity, int layout, String mUsername) {
        super(ref, ChatModel.class, layout, activity);
        this.mUsername = mUsername;
    }

    /**
     * Bind an instance of the <code>Chat</code> class to our view. This method is called by <code>FirebaseListAdapter</code>
     * when there is a data change, and we are given an instance of a View that corresponds to the layout that we passed
     * to the constructor, as well as a single <code>Chat</code> instance that represents the current data to bind.
     *
     * @param view A view instance corresponding to the layout we passed to the constructor.
     * @param chat An instance representing the current state of a chat message
     */
    @Override
    protected void populateView(View view, ChatModel chat) {

        TextView textmessage=(TextView)view.findViewById(R.id.msg);
        TextView date,time;
        date=(TextView)view.findViewById(R.id.dates);
        time=(TextView)view.findViewById(R.id.times);
        String author = chat.getSender();
        textmessage.setText(chat.getMessage());
        date.setText(chat.getDate());
        time.setText(chat.getTime());
        View view1=view.findViewById(R.id.msg);
        if(author.equals(SigninActivity.myemail))
        {
            width=ChatPage.width;
            x=(int)view.getX();
            y=(int)view.getY();
            view.setX(width-view.getWidth());
            Log.e(String.valueOf(x),String.valueOf(view.getX()));
            view1.setBackgroundResource(R.drawable.bubble_left_gray);
            Log.e("setting","righteeeeee");
            notifyDataSetChanged();


        }
        else {
            view.setX(0);
            view1.setBackgroundResource(R.drawable.bubble_right_green);}

       // ChatPage.listView.scrollBy(0,ChatPage.listView.getHeight());
       // ChatPage.listView.smoothScrollToPosition(ChatPage.mChatListAdapter.getCount()+view.getHeight());

        ChatPage.listView.scrollBy(0,view.getHeight()/20);
    }
}
