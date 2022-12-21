package com.flaxeninfosoft.guptaoffset.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Order {
    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("client_id")
    @Expose
    private Long client_Id;

    @SerializedName("latitude")
    @Expose
    private double latitude;

    @SerializedName("longitude")
    @Expose
    private double longitude;

    @SerializedName("slip_image")
    @Expose
    private String slipImage;

    @SerializedName("date")
    @Expose
    private Long date;

    @SerializedName("emp_id")
    @Expose
    private Long emp_Id;

    public Order(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClient_Id() {
        return client_Id;
    }

    public void setClient_Id(Long client_Id) {
        this.client_Id = client_Id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getSlipImage() {
        return slipImage;
    }

    public void setSlipImage(String slipImage) {
        this.slipImage = slipImage;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public Long getEmp_Id() {
        return emp_Id;
    }

    public void setEmp_Id(Long emp_Id) {
        this.emp_Id = emp_Id;
    }
}
