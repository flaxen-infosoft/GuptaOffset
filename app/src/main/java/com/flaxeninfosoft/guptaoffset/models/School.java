package com.flaxeninfosoft.guptaoffset.models;

import android.net.Uri;

import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class School {

    @SerializedName(Constants.ID)
    @Expose
    private Long id;

    @SerializedName(Constants.EMPLOYEE_ID)
    @Expose
    private Long empId;

    @SerializedName(Constants.NAME)
    @Expose
    private String name;

    @SerializedName(Constants.STUDENT_STRENGTH)
    @Expose
    private String strength;

    @SerializedName(Constants.IMAGE)
    @Expose
    private String snap;

    @SerializedName(Constants.SPECIMEN)
    @Expose
    private String specimen;

    @SerializedName(Constants.PHONE)
    @Expose
    private String contact;

    @SerializedName(Constants.DATE)
    @Expose
    private String date;

    @SerializedName(Constants.ADDRESS)
    @Expose
    private Location location;

    private Uri hoadingImageUri;
    private Uri specimenImageUri;

    public School() {
        location = new Location();
    }

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

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public String getSnap() {
        return snap;
    }

    public void setSnap(String snap) {
        this.snap = snap;
    }

    public String getSpecimen() {
        return specimen;
    }

    public void setSpecimen(String specimen) {
        this.specimen = specimen;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
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

    public Uri getHoadingImageUri() {
        return hoadingImageUri;
    }

    public void setHoadingImageUri(Uri hoadingImageUri) {
        this.hoadingImageUri = hoadingImageUri;
    }

    public Uri getSpecimenImageUri() {
        return specimenImageUri;
    }

    public void setSpecimenImageUri(Uri specimenImageUri) {
        this.specimenImageUri = specimenImageUri;
    }

    @Override
    public String toString() {
        return "School{" +
                "id=" + id +
                ", empId=" + empId +
                ", name='" + name + '\'' +
                ", strength='" + strength + '\'' +
                ", snap='" + snap + '\'' +
                ", specimen='" + specimen + '\'' +
                ", contact='" + contact + '\'' +
                ", date='" + date + '\'' +
                ", location=" + location +
                ", hoadingImageUri=" + hoadingImageUri +
                ", specimenImageUri=" + specimenImageUri +
                '}';
    }
}
