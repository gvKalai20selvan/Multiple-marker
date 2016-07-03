 package com.samplejsonparsing.model;

/**
 * Created by Nehru on 28-05-2016.
 */
public class CountryData {

    String cou_id = "", cou_name = "", cou_image = "";

    public CountryData(String cou_id, String cou_name, String cou_image) {
        this.cou_id = cou_id;
        this.cou_name = cou_name;
        this.cou_image = cou_image;
    }

    public CountryData() {
        cou_id = new String();
        cou_name = new String();
        cou_image = new String();
    }

    public String getCou_id() {
        return cou_id;
    }

    public CountryData setCou_id(String cou_id) {
        this.cou_id = cou_id;
        return this;
    }

    public String getCou_name() {
        return cou_name;
    }

    public CountryData setCou_name(String cou_name) {
        this.cou_name = cou_name;
        return this;
    }

    public String getCou_image() {
        return cou_image;
    }

    public CountryData setCou_image(String cou_image) {
        this.cou_image = cou_image;
        return this;
    }
}
