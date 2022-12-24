package com.flaxeninfosoft.guptaoffset.models;

import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Client {

    @SerializedName(Constants.ID)
    @Expose
    private Long id;

    @SerializedName(Constants.NAME)
    @Expose
    private String name;

    @SerializedName(Constants.ORGANIZATION_NAME)
    @Expose
    private String orgName;

    @SerializedName(Constants.ADDRESS)
    @Expose
    private String address;

    @SerializedName(Constants.PHONE)
    @Expose
    private String contactNo;

    @SerializedName(Constants.LATITUDE)
    @Expose
    private double latitude;

    @SerializedName(Constants.LONGITUDE)
    @Expose
    private double longitude;

    @SerializedName(Constants.ASSIGNED_TO)
    @Expose
    private Long assignToId;

    @SerializedName(Constants.TYPE)
    @Expose
    private String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
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

    public Long getAssignToId() {
        return assignToId;
    }

    public void setAssignToId(Long assignToId) {
        this.assignToId = assignToId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
