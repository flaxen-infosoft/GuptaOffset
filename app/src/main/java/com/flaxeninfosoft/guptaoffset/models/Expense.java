package com.flaxeninfosoft.guptaoffset.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Expense {

    @SerializedName("Id")
    @Expose
    private Long id;

    @SerializedName("empId")
    @Expose
    private Long empId;

    @SerializedName("start_location")
    @Expose
    private String startLocation;

    @SerializedName("end_location")
    @Expose
    private String endLocation;

    @SerializedName("starting_meter")
    @Expose
    private long startMeter;

    @SerializedName("end_meter")
    @Expose
    private long endMeter;

    @SerializedName("total_meter")
    @Expose
    private long totalMeter;

    @SerializedName("fuel_cost")
    @Expose
    private String fuelCost;

    @SerializedName("extra_expenses")
    @Expose
    private String extraExpenses;

    @SerializedName("total_expenses")
    @Expose
    private String totalExpenses;

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

    public long getTotalMeter() {
        return totalMeter;
    }

    public void setTotalMeter(long totalMeter) {
        this.totalMeter = totalMeter;
    }

    public String getFuelCost() {
        return fuelCost;
    }

    public void setFuelCost(String fuelCost) {
        this.fuelCost = fuelCost;
    }

    public String getExtraExpenses() {
        return extraExpenses;
    }

    public void setExtraExpenses(String extraExpenses) {
        this.extraExpenses = extraExpenses;
    }

    public String getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(String totalExpenses) {
        this.totalExpenses = totalExpenses;
    }
}
