package com.flaxeninfosoft.guptaoffset.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Client {

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("client_Name")
    @Expose
    private String client_name;

    @SerializedName("org_Name")
    @Expose
    private String orgName;

    @SerializedName("contact_no")
    @Expose
    private String contactNo;

    @SerializedName("address")
    @Expose
    private String Address;

    @SerializedName("assign_to_id")
    @Expose
    private Long assignToId;

    @SerializedName("latitude")
    @Expose
    private double latitude;

    @SerializedName("longitude")
    @Expose
    private double longitude;

    public Client() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public Long getAssignToId() {
        return assignToId;
    }

    public void setAssignToId(Long assignToId) {
        this.assignToId = assignToId;
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
}
