package com.flaxeninfosoft.guptaoffset.models;

import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmployeeAbsentLeave {

    @SerializedName(Constants.ID)
    @Expose
    private Long id;

    @SerializedName(Constants.EMPLOYEE_ID)
    @Expose
    private Long empId;

    @SerializedName(Constants.TOTAL_ABSENT)
    @Expose
    private String total_absent;

    @SerializedName(Constants.D_DATE)
    @Expose
    private String d_date;

    @SerializedName(Constants.LEAVE_DATES)
    @Expose
    private String leave_dates;

    @SerializedName(Constants.LEAVE_DAYS)
    @Expose
    private String leave_days;

    @SerializedName(Constants.FROM_DATE)
    @Expose
    private String from_date;

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

    public String getTotal_absent() {
        return total_absent;
    }

    public void setTotal_absent(String total_absent) {
        this.total_absent = total_absent;
    }

    public String getD_date() {
        return d_date;
    }

    public void setD_date(String d_date) {
        this.d_date = d_date;
    }

    public String getLeave_dates() {
        return leave_dates;
    }

    public void setLeave_dates(String leave_dates) {
        this.leave_dates = leave_dates;
    }

    public String getLeave_days() {
        return leave_days;
    }

    public void setLeave_days(String leave_days) {
        this.leave_days = leave_days;
    }

    public String getFrom_date() {
        return from_date;
    }

    public void setFrom_date(String from_date) {
        this.from_date = from_date;
    }

    public String getTo_date() {
        return to_date;
    }

    public void setTo_date(String to_date) {
        this.to_date = to_date;
    }

    @SerializedName(Constants.TO_DATE)
    @Expose
    private String to_date;





}