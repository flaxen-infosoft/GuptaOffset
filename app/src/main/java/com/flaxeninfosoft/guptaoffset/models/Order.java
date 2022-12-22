package com.flaxeninfosoft.guptaoffset.models;

import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Order {

    @SerializedName(Constants.ID)
    @Expose
    private Long id;

    @SerializedName(Constants.CLIENT_ID)
    @Expose
    private Long client_Id;

    @SerializedName(Constants.EMPLOYEE_ID)
    @Expose
    private Long emp_Id;

    @SerializedName(Constants.LATITUDE)
    @Expose
    private double latitude;

    @SerializedName(Constants.LONGITUDE)
    @Expose
    private double longitude;

    @SerializedName(Constants.IMAGE)
    @Expose
    private String slipImage;

    @SerializedName(Constants.DATE)
    @Expose
    private Long date;

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

    public Long getEmp_Id() {
        return emp_Id;
    }

    public void setEmp_Id(Long emp_Id) {
        this.emp_Id = emp_Id;
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
}
