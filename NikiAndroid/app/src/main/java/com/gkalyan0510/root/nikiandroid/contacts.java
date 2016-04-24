package com.gkalyan0510.root.nikiandroid;

/**
 * Created by root on 24/4/16.
 */
public class contacts {
    String name;
    String email;
    String phone;
    String officePhone;
    float latitude;
    float longitude;
    contacts(String name,String email,String phone,String officePhone,float latitude,float longitude){
        this.name = name;
        this.email=email;
        this.latitude=latitude;
        this.longitude=longitude;
        this.phone= phone;
        this.officePhone= officePhone;
    }
}
