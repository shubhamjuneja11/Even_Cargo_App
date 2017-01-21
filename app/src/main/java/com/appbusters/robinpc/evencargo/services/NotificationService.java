package com.appbusters.robinpc.evencargo.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

import com.appbusters.robinpc.evencargo.Constants;
import com.appbusters.robinpc.evencargo.R;
import com.appbusters.robinpc.evencargo.chat.ChatModel;
import com.appbusters.robinpc.evencargo.invites.InviteData;
import com.appbusters.robinpc.evencargo.managerpanel.ManagerPanelActivity;
import com.appbusters.robinpc.evencargo.orders.OrderClass;
import com.appbusters.robinpc.evencargo.siginsignup.SigninActivity;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class NotificationService extends Service {
    Firebase firebase, chat,invitessent,invitesrecieved;
    public static String si;
    int decide = 0;
    SharedPreferences sharedPreferences;
    public NotificationService() {
    }

    public static String convert1(String a) {
        String from = a;
        from = from.replaceAll("\\.", "-1-");
        from = from.replaceAll("\\$", "-2-");
        from = from.replaceAll("\\#", "-3-");
        return from;

    }

    public static String convert2(String a) {
        String key = a;
        key = key.replaceAll("-1-", ".");
        key = key.replaceAll("-2-", "$");
        key = key.replaceAll("-3-", "#");
        return key;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            Class.forName("com.appbusters.robinpc.evencargo.invites.InviteData");
            Class.forName("com.appbusters.robinpc.evencargo.chat.ChatModel");
            Class.forName("com.appbusters.robinpc.evencargo.orders.OrderClass");
            Class.forName("com.appbusters.robinpc.evencargo.Constants");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        //sharedPreferences = getSharedPreferences("Mypref", Context.MODE_PRIVATE);
        if (sharedPreferences.contains("email"))
        { si = sharedPreferences.getString("email", "");Log.e("you are going"," to love this");}
        else {si = SigninActivity.myemail;Log.e("you are going"," to love this toooo");}
        Log.e("me ", "nonononono");
        if(Constants.from==null){
            Constants.from=si;Constants.fun();
        }
        firebase = new Firebase("https://chat-6c23a.firebaseio.com/invites/Orders");
        firebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                decide = 0;
                OrderClass orderClass = dataSnapshot.getValue(OrderClass.class);
                Log.e("nd",String.valueOf(orderClass.getnd()));
                Log.e("nm",String.valueOf(orderClass.getnm()));
                Log.e("nc",String.valueOf(orderClass.getnc()));
                if (orderClass.getmanager().equals(si) && orderClass.getnm() == 1)
                    decide = 1;
                else if (orderClass.getdriver().equals(si) && orderClass.getnd() == 1) decide = 2;
                else if (orderClass.getclient().equals(si) && orderClass.getnc() == 1) decide = 3;
                Log.e("decideeeeeeeeeeee",String.valueOf(decide));
                if (decide != 0) {
                    Log.e("Joey &", "Chandler");
                    String status = "";
                    int so = orderClass.getstatus();
                    switch (so) {
                        case 3:
                            status = "PICKED";
                            break;
                        case 2:
                            status = "STARTED";
                            break;

                        //pending
                        case 0:
                            status = "UNASSIGNED";
                            break;
                        case 1:
                            status = "ASSIGNED";
                            break;

                        //completedOrderAdapter


                        case 4:
                            status = "NOT DELIVERED";
                            break;
                        case 5:
                            status = "DELIVERED";
                            break;
                        case 6:
                            status = "CANCELLED";
                            break;
                    }
                    Intent intent1;
                    if (decide == 1) {
                        intent1 = new Intent(NotificationService.this, SigninActivity.class);
                        firebase.child(dataSnapshot.getKey()).child("nm").setValue(0);
                    } else if (decide == 2) {
                        intent1 = new Intent(NotificationService.this, SigninActivity.class);
                        intent1.putExtra("flag",3);
                        firebase.child(dataSnapshot.getKey()).child("nd").setValue(0);
                    } else {
                        intent1 = new Intent(NotificationService.this, SigninActivity.class);
                        intent1.putExtra("flag",4);
                        firebase.child(dataSnapshot.getKey()).child("nc").setValue(0);
                    }

                    TaskStackBuilder stackBuilder = TaskStackBuilder.create(NotificationService.this);
                    stackBuilder.addParentStack(ManagerPanelActivity.class);
                    stackBuilder.addNextIntent(intent1);
                    PendingIntent pendingIntent =
                            stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT
                            );
                    Notification notification = new Notification.Builder(NotificationService.this)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle("Order " + String.valueOf(orderClass.getorderno()) + " is " + status)
                            .setAutoCancel(true)
                            .setPriority(Notification.PRIORITY_MAX)
                            .setDefaults(Notification.DEFAULT_VIBRATE)
                            .setContentIntent(pendingIntent)
                            // .setContentText("dd")
                            .build();
                    NotificationManager notificationManager =
                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(1, notification);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        chat = new Firebase("https://chat-6c23a.firebaseio.com/invites/" + convert1(si) + "/chats");
        chat.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChatModel chatModel = dataSnapshot.getValue(ChatModel.class);
                String sender = chatModel.getSender();
                String message = chatModel.getMessage();
                if(si==null)Log.e("bro","trouble");
                else Log.e("i m","fine");
                if (!sender.equals(si)&&chatModel.getnc()==1) {

                    chat.child(dataSnapshot.getKey()).child("nc").setValue(0);
                    Intent intent1 = new Intent(getApplicationContext(), SigninActivity.class);
                    intent1.putExtra("flag",1);
                    intent1.putExtra("FROM_USER", si);
                    intent1.putExtra("TO_USER", sender);
                    TaskStackBuilder stackBuilder = TaskStackBuilder.create(NotificationService.this);
                    stackBuilder.addParentStack(ManagerPanelActivity.class);
                    stackBuilder.addNextIntent(intent1);
                    PendingIntent pendingIntent =
                            stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT
                            );
                    Notification notification = new Notification.Builder(NotificationService.this)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle("New message from " + convert2(sender))
                            .setAutoCancel(true)
                            .setPriority(Notification.PRIORITY_MAX)
                            .setDefaults(Notification.DEFAULT_VIBRATE)
                            .setContentText(message)
                            .setContentIntent(pendingIntent)
                            .build();
                    NotificationManager notificationManager =
                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(1, notification);

                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                /*String sender=dataSnapshot.getKey();
                sender=convert2(sender);
                Intent intent1 = new Intent(NotificationService.this, ChatPage.class);
                intent1.putExtra("FROM_USER",si);
                intent1.putExtra("TO_USER",sender);
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(NotificationService.this);
                stackBuilder.addParentStack(ManagerPanelActivity.class);
                stackBuilder.addNextIntent(intent1);
                PendingIntent pendingIntent =
                        stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT
                        );
                Notification notification = new Notification.Builder(NotificationService.this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("New message from "+sender)
                        .setAutoCancel(true)
                        .setPriority(Notification.PRIORITY_MAX)
                        .setDefaults(Notification.DEFAULT_VIBRATE)
                        .setContentIntent(pendingIntent)
                        .build();
                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(1, notification);*/
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        invitessent= new Firebase("https://chat-6c23a.firebaseio.com/invites/" + convert1(si) + "/Sent");
        invitesrecieved= new Firebase("https://chat-6c23a.firebaseio.com/invites/" + convert1(si) + "/Recieved");
        invitessent.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                InviteData inviteData=dataSnapshot.getValue(InviteData.class);
                if(inviteData.getns()==1)
                {invitessent.child(dataSnapshot.getKey()).child("ns").setValue(0);
                    String reciever=inviteData.getreciever();
                    int status=inviteData.getStatus();
                    String post=inviteData.getpost();
                    String s1="";
                    switch (status){
                            case 1:s1="ACCEPTED";break;
                            case 2:s1="REFUSED";break;
                            case 4:s1="CANCELLED";break;
                            case 0:s1="PENDING";break;

                    }

                    Intent intent1 = new Intent(NotificationService.this, SigninActivity.class);
                    intent1.putExtra("flag",2);
                    TaskStackBuilder stackBuilder = TaskStackBuilder.create(NotificationService.this);
                    stackBuilder.addParentStack(ManagerPanelActivity.class);
                    stackBuilder.addNextIntent(intent1);
                    PendingIntent pendingIntent =
                            stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT
                            );
                    Notification notification = new Notification.Builder(NotificationService.this)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle(convert2(reciever)+" "+s1+" your invite ")
                            .setAutoCancel(true)
                            .setContentText("for "+post)
                            .setPriority(Notification.PRIORITY_MAX)
                            .setDefaults(Notification.DEFAULT_VIBRATE)
                            .setContentIntent(pendingIntent)
                            .build();
                    NotificationManager notificationManager =
                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(1, notification);
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        invitesrecieved.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                InviteData inviteData=dataSnapshot.getValue(InviteData.class);
                if(inviteData.getnr()==1) {
                    invitesrecieved.child(dataSnapshot.getKey()).child("nr").setValue(0);
                    String sender = inviteData.getsender();
                    String post = inviteData.getpost();
                    Intent intent1 = new Intent(NotificationService.this, SigninActivity.class);
                    intent1.putExtra("flag",2);
                    TaskStackBuilder stackBuilder = TaskStackBuilder.create(NotificationService.this);
                    stackBuilder.addParentStack(ManagerPanelActivity.class);
                    stackBuilder.addNextIntent(intent1);
                    PendingIntent pendingIntent =
                            stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT
                            );
                    Notification notification = new Notification.Builder(NotificationService.this)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle(convert2(sender) + "  send you invite ")
                            .setAutoCancel(true)
                            .setContentText("for " + post)
                            .setPriority(Notification.PRIORITY_MAX)
                            .setDefaults(Notification.DEFAULT_VIBRATE)
                            .setContentIntent(pendingIntent)
                            .build();
                    NotificationManager notificationManager =
                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(1, notification);
                }
            }


            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        return super.onStartCommand(intent, flags, startId);
    }
}

