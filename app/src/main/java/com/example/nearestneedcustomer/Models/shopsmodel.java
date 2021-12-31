package com.example.nearestneedcustomer.Models;


public  class shopsmodel{


   public static String shopname;
    String userid;

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getUpi() {
        return upi;
    }

    public void setUpi(String upi) {
        this.upi = upi;
    }

    String lat,lon,upi;

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getShopdesc() {
        return shopdesc;
    }

    public void setShopdesc(String shopdesc) {
        this.shopdesc = shopdesc;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getShopimage() {
        return shopimage;
    }

    public void setShopimage(String shopimage) {
        this.shopimage = shopimage;
    }

    String shopdesc;
    String phone;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    String shopimage;


    public shopsmodel() {
    }

    public shopsmodel(String shopname,String shopdesc,String phone,String shopimage,String userid,String lat,String lon,String upi) {
        this.shopname=shopname;
        this.shopdesc=shopdesc;
        this.upi=upi;
        this.lat=lat;
        this.lon=lon;
        this.phone=phone;
        this.userid=userid;
        this.shopimage=shopimage;
    }






}