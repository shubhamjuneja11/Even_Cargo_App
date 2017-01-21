package com.appbusters.robinpc.evencargo.invites;

import android.util.Log;

/**
 * Created by junejaspc on 14/9/16.
 */
public class InviteData {
    String sender,reciever,post;
    public int status,nr,ns;
    public String getsender() {
        return sender;
    }
    public String getreciever(){return reciever;}
    public String getpost(){return post;}
    public int getStatus(){return status;}
    public int getnr(){return nr;}
    public int getns(){return ns;}

    public void setsender(String sender) {
        this.sender = sender;
    }
    public void setreciever(String reciever) {
        this.reciever=reciever;
    }
    public void setpost(String post) {
        this.post=post;
    }
    public void setstatus(int status){this.status=status;}
    public void setnr(int nr){this.nr=nr;}
    public void setns(int ns){this.ns=ns;}
    public InviteData(){

    }
    public InviteData(String s, String m,String a,int st){
        this.sender =s ;
        this.reciever=m;
        this.post=a;
        this.status=st;
        nr=1;
        ns=0;
        Log.e("teg",sender+" "+reciever+" "+post+" "+status);
    }
}
