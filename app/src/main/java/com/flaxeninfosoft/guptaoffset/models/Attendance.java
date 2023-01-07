package com.flaxeninfosoft.guptaoffset.models;

import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Date;

public class Attendance {

    @SerializedName(Constants.ID)
    @Expose
    private int id;

    @SerializedName(Constants.EMPLOYEE_ID)
    @Expose
    private int empId;

    @SerializedName(Constants.TIME_IN)
    @Expose
    private Date timeIn;

    @SerializedName(Constants.TIME_OUT)
    @Expose
    private Date timeOut;

    @SerializedName(Constants.SNAP_IN)
    @Expose
    private Long snapIn;

    @SerializedName(Constants.SNAP_OUT)
    @Expose
    private Long snapOut;

    @SerializedName(Constants.DATE)
    @Expose
    private Long date;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public Date getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(Date timeIn) {
        this.timeIn = timeIn;
    }

    public Date getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Date timeOut) {
        this.timeOut = timeOut;
    }

    public Long getSnapIn() {
        return snapIn;
    }

    public void setSnapIn(Long snapIn) {
        this.snapIn = snapIn;
    }

    public Long getSnapOut() {
        return snapOut;
    }

    public void setSnapOut(Long snapOut) {
        this.snapOut = snapOut;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }
}
