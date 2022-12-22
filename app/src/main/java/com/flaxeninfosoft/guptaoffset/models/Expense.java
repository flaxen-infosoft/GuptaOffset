package com.flaxeninfosoft.guptaoffset.models;

import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Date;

public class Expense {

    @SerializedName(Constants.ID)
    @Expose
    private Long id;

    @SerializedName(Constants.EMPLOYEE_ID)
    @Expose
    private Long empId;

    @SerializedName(Constants.START_LOCATION)
    @Expose
    private String startLocation;

    @SerializedName(Constants.END_LOCATION)
    @Expose
    private String endLocation;

    @SerializedName(Constants.START_METER)
    @Expose
    private long startMeter;

    @SerializedName(Constants.END_METER)
    @Expose
    private long endMeter;

    @SerializedName(Constants.TOTAL_DISTANCE)
    @Expose
    private long totalDistance;

    @SerializedName(Constants.FUEL_COST)
    @Expose
    private String fuelCost;

    @SerializedName(Constants.EXTRA_EXPENSE)
    @Expose
    private String extraExpense;

    @SerializedName(Constants.TOTAL_EXPENSE)
    @Expose
    private String totalExpense;

    @SerializedName(Constants.DATE)
    @Expose
    private Date date;

    @SerializedName(Constants.REMARK)
    @Expose
    private String remark;

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

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public long getStartMeter() {
        return startMeter;
    }

    public void setStartMeter(long startMeter) {
        this.startMeter = startMeter;
    }

    public long getEndMeter() {
        return endMeter;
    }

    public void setEndMeter(long endMeter) {
        this.endMeter = endMeter;
    }

    public long getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(long totalDistance) {
        this.totalDistance = totalDistance;
    }

    public String getFuelCost() {
        return fuelCost;
    }

    public void setFuelCost(String fuelCost) {
        this.fuelCost = fuelCost;
    }

    public String getExtraExpense() {
        return extraExpense;
    }

    public void setExtraExpense(String extraExpense) {
        this.extraExpense = extraExpense;
    }

    public String getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(String totalExpense) {
        this.totalExpense = totalExpense;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
