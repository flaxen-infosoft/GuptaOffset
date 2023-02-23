package com.flaxeninfosoft.guptaoffset.models;

import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Location {

    @SerializedName(Constants.ID)
    @Expose
    private Long id;

    @SerializedName(Constants.EMPLOYEE_ID)
    @Expose
    private Long empId;

    @SerializedName(Constants.DATE)
    @Expose
    private String date;

    @SerializedName(Constants.LATITUDE)
    @Expose
    private double latitude;

    @SerializedName(Constants.LONGITUDE)
    @Expose
    private double longitude;

    @SerializedName(Constants.ADDRESS)
    @Expose
    private String address;

    @SerializedName(Constants.BATTERY_STATUS)
    @Expose
    private String batteryStatus;

    @SerializedName(Constants.DISTRICT)
    @Expose
    private String district;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getLatitude() {
        return latitude;
    }

//    Yeah Boiii!!!
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBatteryStatus() {
        return batteryStatus;
    }

    public void setBatteryStatus(String batteryStatus) {
        this.batteryStatus = batteryStatus;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", empId=" + empId +
                ", date='" + date + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", address='" + address + '\'' +
                ", batteryStatus='" + batteryStatus + '\'' +
                ", district='" + district + '\'' +
                '}';
    }
}
