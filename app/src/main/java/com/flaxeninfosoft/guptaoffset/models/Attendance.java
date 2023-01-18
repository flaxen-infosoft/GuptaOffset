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
    private String snapIn;

    @SerializedName(Constants.SNAP_OUT)
    @Expose
    private String snapOut;

    @SerializedName(Constants.DATE)
    @Expose
    private Long date;

    @SerializedName(Constants.START_METER)
    @Expose
    private String startMeter;

    @SerializedName(Constants.END_METER)
    @Expose
    private String endMeter;

    @SerializedName(Constants.TOTAL_DISTANCE)
    @Expose
    private String totalDistance;

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

    public String getSnapIn() {
        return snapIn;
    }

    public void setSnapIn(String snapIn) {
        this.snapIn = snapIn;
    }

    public String getSnapOut() {
        return snapOut;
    }

    public void setSnapOut(String snapOut) {
        this.snapOut = snapOut;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getStartMeter() {
        return startMeter;
    }

    public void setStartMeter(String startMeter) {
        this.startMeter = startMeter;
    }

    public String getEndMeter() {
        return endMeter;
    }

    public void setEndMeter(String endMeter) {
        this.endMeter = endMeter;
    }

    public String getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(String totalDistance) {
        this.totalDistance = totalDistance;
    }

    public int getPunchStatus() {
        return punchStatus;
    }

    public void setPunchStatus(int punchStatus) {
        this.punchStatus = punchStatus;
    }

    @Override
    public String toString() {
        return "Attendance{" +
                "id=" + id +
                ", empId=" + empId +
                ", timeIn=" + timeIn +
                ", timeOut=" + timeOut +
                ", snapIn='" + snapIn + '\'' +
                ", snapOut='" + snapOut + '\'' +
                ", date=" + date +
                ", startMeter='" + startMeter + '\'' +
                ", endMeter='" + endMeter + '\'' +
                ", totalDistance='" + totalDistance + '\'' +
                ", punchStatus=" + punchStatus +
                '}';
    }
}
