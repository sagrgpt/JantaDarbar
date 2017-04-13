package com.complaints.jd.jantadurbar.Adapters;

/**
 * Created by sagar on 9/4/17.
 */

public class DataStorageClass {

    public String title;
    public String description;
    public String landmark;
    public String wardNo;
    public String city;

    public DataStorageClass(String title, String description, String landmark, String wardNo, String city) {
        this.title = title;
        this.description = description;
        this.landmark = landmark;
        this.wardNo = wardNo;
        this.city = city;
    }

    public DataStorageClass() {
    }
}
