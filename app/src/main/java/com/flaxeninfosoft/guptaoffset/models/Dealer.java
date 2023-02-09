package com.flaxeninfosoft.guptaoffset.models;

import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dealer {

    @SerializedName(Constants.ID)
    @Expose
    private Long id;

    @SerializedName(Constants.EMPLOYEE_ID)
    @Expose
    private Long empId;

    @SerializedName(Constants.NAME)
    @Expose
    private String name;

    @SerializedName(Constants.PHONE)
    @Expose
    private String contact;

    @SerializedName(Constants.IMAGE)
    @Expose
    private String image;

    @SerializedName(Constants.DATE)
    @Expose
    private String date;

    @SerializedName(Constants.LOCATION)
    @Expose
    private Location location;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Dealer{" +
                "id=" + id +
                ", empId=" + empId +
                ", name='" + name + '\'' +
                ", contact='" + contact + '\'' +
                ", image='" + image + '\'' +
                ", date='" + date + '\'' +
                ", location=" + location +
                '}';
    }
}
