package com.flaxeninfosoft.guptaoffset.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Client {

    @SerializedName("Id")
    @Expose
    private Long id;

    @SerializedName("client_Name")
    @Expose
    private String client_name;

    @SerializedName("org_Name")
    @Expose
    private String OrgName;

    @SerializedName("Contact_no")
    @Expose
    private Long contact_no;

    @SerializedName("address")
    @Expose
    private String Address;

    @SerializedName("packageName")
    @Expose
    private String packageName;

    @SerializedName("packageValue")
    @Expose
    private String packageValue;

    @SerializedName("Assign_to_id")
    @Expose
    private Long assign_to_Id;

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
        return OrgName;
    }

    public void setOrgName(String orgName) {
        OrgName = orgName;
    }

    public Long getContact_no() {
        return contact_no;
    }

    public void setContact_no(Long contact_no) {
        this.contact_no = contact_no;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageValue() {
        return packageValue;
    }

    public void setPackageValue(String packageValue) {
        this.packageValue = packageValue;
    }

    public Long getAssign_to_Id() {
        return assign_to_Id;
    }

    public void setAssign_to_Id(Long assign_to_Id) {
        this.assign_to_Id = assign_to_Id;
    }
}
