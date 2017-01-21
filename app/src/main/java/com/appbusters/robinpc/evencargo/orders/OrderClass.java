package com.appbusters.robinpc.evencargo.orders;

/**
 * Created by junejaspc on 28/9/16.
 */
public class OrderClass {
    public String manager,driver,client,pdate,ptime,paddress,paddressdetails,daddress,daddressdetails,packagedetails;
    public  int status;
    public long orderno;
    public int m,d,c;
    public int nm,nd,nc;
    public OrderClass(){}
    public OrderClass(String manager,String driver,String client,String pdate,String ptime,String paddress,String paddressdetails,String daddress,String daddressdetails,String packagedetails,int status,long orderno)
    {
        this.manager=manager;
        this.driver=driver;
        this.client=client;
        this.pdate=pdate;
        this.ptime=ptime;
        this.paddress=paddress;
        this.paddressdetails=paddressdetails;
        this.daddress=daddress;
        this.daddressdetails=daddressdetails;
        this.packagedetails=packagedetails;
        this.status=status;
        this.orderno=orderno;
        m=d=c=1;
    }
    public OrderClass(String manager,String driver,String client,String pdate,String ptime,String paddress,String paddressdetails,String daddress,String daddressdetails,String packagedetails,int status,long orderno,int nm,int nd,int nc)
    {
        this.manager=manager;
        this.driver=driver;
        this.client=client;
        this.pdate=pdate;
        this.ptime=ptime;
        this.paddress=paddress;
        this.paddressdetails=paddressdetails;
        this.daddress=daddress;
        this.daddressdetails=daddressdetails;
        this.packagedetails=packagedetails;
        this.status=status;
        this.orderno=orderno;
        this.nm=nm;
        this.nd=nd;
        this.nc=nc;
        m=d=c=1;
    }
    public long getorderno(){return  orderno;}
    public String getmanager(){return  manager;}
    public String getdriver(){return  driver;}
    public String getclient(){return  client;}
    public String getpdate(){return  pdate;}
    public String getptime(){return  ptime;}
    public String getpaddress(){return  paddress;}
    public String getpaddressdetails(){return  paddressdetails;}
    public String getdaddress(){return  daddress;}
    public String getdaddressdetails(){return  daddressdetails;}
    public String getpackagedetails(){return  packagedetails;}
    public int getstatus(){return  status;}
    public int getM(){return  m;}

    public int getC() {
        return c;
    }
    public int getnm(){return nm;}
    public int getnd(){return  nd;}
    public int getnc(){return nc;}
    public int getD() {
        return d;
    }

    public void putorderno(long orderno){this.orderno=orderno;}
    public void putmanager(String manager){this.manager=manager;}
    public void putdriver(String driver){this.driver=driver;}
    public void putclient(String client){this.client=client;}
    public void putpdate(String pdate){this.pdate=pdate;}
    public void putptime(String ptime){this.ptime=ptime;}
    public void putpaddress(String paddress){this.paddress=paddress;}
    public void putaddressdetails(String paddressdetails){this.paddressdetails=paddressdetails;}
    public void putdaddress(String daddress){this.daddress=daddress;}
    public void putdaddressdetails(String daddressdetails){this.daddressdetails=daddressdetails;}
    public void putpackagedetails(String packagedetails){this.packagedetails=packagedetails;}
    public void putstatus(int status){this.status=status;}
    public void putM(int m){this.m=m;}
    public void putD(int d){this.d=d;}
    public void putC(int c){this.c=c;}
    public void putnm(int nm){this.nm=nm;}
    public void putnc(int nc){this.nc=nc;}
    public void putnd(int nd){this.nd=nd;}

}
