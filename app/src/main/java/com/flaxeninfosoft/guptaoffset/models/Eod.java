package com.flaxeninfosoft.guptaoffset.models;

import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Eod {

    @SerializedName(Constants.ID)
    @Expose
    private Long id;

    @SerializedName(Constants.EMPLOYEE_ID)
    @Expose
    private Long empId;

    @SerializedName(Constants.DATE)
    @Expose
    private String date;

    @SerializedName(Constants.SCHOOL_VISITS)
    @Expose
    private String schoolVisits;

    @SerializedName(Constants.PETROL_EXPENSE)
    @Expose
    private String petrolExpense;

    @SerializedName(Constants.OTHER_EXPENSE)
    @Expose
    private String otherExpense;

    @SerializedName(Constants.IMAGE)
    @Expose
    private String expenseImage;

    @SerializedName(Constants.DAILY_ALLOWANCE)
    @Expose
    private String dailyAllowance;

    @SerializedName(Constants.LATITUDE)
    @Expose
    private Double latitude;

    @SerializedName(Constants.LONGITUDE)
    @Expose
    private Double longitude;

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

    public String getSchoolVisits() {
        return schoolVisits;
    }

    public void setSchoolVisits(String schoolVisits) {
        this.schoolVisits = schoolVisits;
    }

    public String getPetrolExpense() {
        return petrolExpense;
    }

    public void setPetrolExpense(String petrolExpense) {
        this.petrolExpense = petrolExpense;
    }

    public String getOtherExpense() {
        return otherExpense;
    }

    public void setOtherExpense(String otherExpense) {
        this.otherExpense = otherExpense;
    }

    public String getExpenseImage() {
        return expenseImage;
    }

    public void setExpenseImage(String expenseImage) {
        this.expenseImage = expenseImage;
    }

    public String getDailyAllowance() {
        return dailyAllowance;
    }

    public void setDailyAllowance(String dailyAllowance) {
        this.dailyAllowance = dailyAllowance;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
