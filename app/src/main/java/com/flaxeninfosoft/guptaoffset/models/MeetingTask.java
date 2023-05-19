package com.flaxeninfosoft.guptaoffset.models;

import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MeetingTask {

    @SerializedName(Constants.ID)
    @Expose
    private Long id;

    @SerializedName(Constants.EMPLOYEE_ID)
    @Expose
    private Long empId;

    @SerializedName(Constants.DATE)
    @Expose
    private String date;

    @SerializedName(Constants.TIME)
    @Expose
    private String time;

    @SerializedName("address")
    @Expose
    private String meeting_address;

    @SerializedName(Constants.TASK)
    @Expose
    private String task;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMeeting_address() {
        return meeting_address;
    }

    public void setMeeting_address(String meeting_address) {
        this.meeting_address = meeting_address;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}
