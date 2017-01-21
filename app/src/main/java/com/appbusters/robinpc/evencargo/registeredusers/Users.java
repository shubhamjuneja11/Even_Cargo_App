package com.appbusters.robinpc.evencargo.registeredusers;

/**
 * Created by junejaspc on 26/9/16.
 */
public class Users {
    String name;
    String phone;
    String email;
    public Users(String email,String name,String phone)
    {
        this.name=name;
        this.phone=phone;
        this.email=email;
    }
    public String getname(){
        return name;

    }
    public String getphone(){
        return name;

    }
    public String getemail(){
        return email;

    }
    public void setname(String name){
        this.name=name;
    }
    public void setphone(String phone){
        this.phone=phone;
    }
    public void setemail(String email){
        this.email=email;
    }
}
