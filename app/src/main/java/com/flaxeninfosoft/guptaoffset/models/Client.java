package com.flaxeninfosoft.guptaoffset.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//TODO
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

    @SerializedName("Assign_to_id")
    @Expose
    private Long assign_to_Id;

    public Client(){
    }
    public Long getId(){return id;}

    public void setid(Long Id) {this.id= id;}

    public String getClient_name(){return client_name;}

    public void setClientName(String clientName){this.client_name= clientName;}

    public String getOrgName(){return OrgName;}

    public void setOrgName(String orgName){this.OrgName= OrgName;}

    public Long getContact_no(){return contact_no;}

    public void setContact_no(String contactNo){this.contact_no= contact_no;}

    public String getAddress(){return Address;}

    public void setAddress(String address){this.Address= Address;}

    public Long getAssign_to_Id(){return assign_to_Id;}

    public void setAssign_to_Id(Long assign_to_Id){this.assign_to_Id=assign_to_Id;}


}
