package com.appbusters.robinpc.evencargo.chat;

/**
 * Created by Vivek on 26-Jul-16.
 */
public class ChatModel {
    private String message;
    private String Sender;
    private String Reciever;
    private String time,date;
    private int nc;
   // private String S;
    public String getSender() {
        return Sender;
    }
    public int getnc(){return nc;}
   /* public String getS() {
        return S;
    }*/
    public void setDate(String date){this.date=date;}
    public void setTime(String time){this.time=time;}
    public void setSender(String sender) {
        Sender = sender;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    /*public void setS(String sender) {
        S = sender;
    }*/
    public void setNc(int nc){this.nc=nc;}
    public void setReciever(String reciever) {
        Reciever=reciever;
    }

    public String getMessage() {
        return message;
    }
    public String getTime(){return time;}
    public String getDate(){return date;}


    public String getReciever(){return Reciever;}

    public ChatModel(){

    }
    public ChatModel(String s, String m, String r,String d,String t){
        this.Sender =s ;
        this.message =m;
        this.Reciever=r;
        this.date=d;
        this.time=t;
        nc=1;
    }
}
