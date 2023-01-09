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
    private long empId;

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

    @SerializedName(Constants.START_METER)
    @Expose
    private Long startMeter;

    @SerializedName(Constants.END_METER)
    @Expose
    private Long endMeter;

    @SerializedName(Constants.TOTAL_DISTANCE)
    @Expose
    private Long totalDistance;

    @SerializedName(Constants.PUNCH_STATUS)
    @Expose
    private int punchStatus;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getEmpId() {
        return empId;
    }

    public void setEmpId(long empId) {
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

    public Long getStartMeter() {
        return startMeter;
    }

    public void setStartMeter(Long startMeter) {
        this.startMeter = startMeter;
    }

    public Long getEndMeter() {
        return endMeter;
    }

    public void setEndMeter(Long endMeter) {
        this.endMeter = endMeter;
    }

    public Long getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(Long totalDistance) {
        this.totalDistance = totalDistance;
    }

    public int getPunchStatus() {
        return punchStatus;
    }

    public void setPunchStatus(int punchStatus) {
        this.punchStatus = punchStatus;
    }
}
