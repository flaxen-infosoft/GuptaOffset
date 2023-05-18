package com.flaxeninfosoft.guptaoffset.models;

import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmployeeBookOrder {

    @SerializedName(Constants.ID)
    @Expose
    private Long id;

    @SerializedName(Constants.EMPLOYEE_ID)
    @Expose
    private Long empId;

    @SerializedName(Constants.IMAGE)
    @Expose
    private String snap;

    @SerializedName(Constants.DATE)
    @Expose
    private String date;


    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

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

    public String getSnap() {
        return snap;
    }

    public void setSnap(String snap) {
        this.snap = snap;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
