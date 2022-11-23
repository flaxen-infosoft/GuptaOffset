package com.flaxeninfosoft.guptaoffset.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Attendance {

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("time_in")
    @Expose
    private String timeIn;

    @SerializedName("time_out")
    @Expose
    private String timeOut;

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("empId")
    @Expose
    private Long empId;

    public Attendance(){
    }
    public Long getId(){return id;}

    public void setId(Long id) {this.id = id;}

    public String getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(String timeIn) {this.timeIn = timeIn;}

    public String setTimeOut() {return timeOut;}

    public void setTimeOut(String timeOut) {this.timeOut =timeOut;}

    public String getDate(){return  date;}

    public void setDate(String date) {this.date = date;}

    public Long getEmpId() {return empId;}

    public void setEmpId(Long empId) {
        this.empId = empId;}
}
