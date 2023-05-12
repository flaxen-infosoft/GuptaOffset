package com.flaxeninfosoft.guptaoffset.models;

import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Date;

public class Attendance {

    @SerializedName(Constants.ID)
    @Expose
    private Long id;

    @SerializedName(Constants.EMPLOYEE_ID)
    @Expose
    private Long empId;

    @SerializedName(Constants.TIME_IN)
    @Expose
    private String timeIn;

    @SerializedName(Constants.TIME_OUT)
    @Expose
    private String timeOut;

    @SerializedName(Constants.SNAP_IN)
    @Expose
    private String snapIn;

    @SerializedName(Constants.SNAP_OUT)
    @Expose
    private String snapOut;

    @SerializedName(Constants.DATE)
    @Expose
    private String date;

    @SerializedName(Constants.START_METER)
    @Expose
    private String startMeter;

    @SerializedName(Constants.START_LOCATION)
    @Expose
    private Location startLocation;

    @SerializedName(Constants.END_METER)
    @Expose
    private String endMeter;

    @SerializedName(Constants.END_LOCATION)
    @Expose
    private Location endLocation;

    @SerializedName(Constants.TOTAL_DISTANCE)
    @Expose
    private String totalDistance;

    @SerializedName(Constants.PUNCH_STATUS)
    @Expose
    private int punchStatus;

    @SerializedName(Constants.LOCATION)
    @Expose
    private Location location;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Attendance() {
        startLocation = new Location();
        endLocation = new Location();
    }

    public Long getId() {
//        Yeah Boiii!!!
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

    public String getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(String timeIn) {
        this.timeIn = timeIn;
    }

    public String getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(String timeOut) {
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartMeter() {
        return startMeter;
    }

    public void setStartMeter(String startMeter) {
        this.startMeter = startMeter;
    }

    public Location getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(Location startLocation) {
        this.startLocation = startLocation;
    }

    public String getEndMeter() {
        return endMeter;
    }

    public void setEndMeter(String endMeter) {
        this.endMeter = endMeter;
    }

    public Location getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(Location endLocation) {
        this.endLocation = endLocation;
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
