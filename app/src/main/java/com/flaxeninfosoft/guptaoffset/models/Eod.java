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

    @SerializedName(Constants.PETROL_EXPENSE_IMAGE)
    @Expose
    private String petrolExpenseImage;

    @SerializedName(Constants.OTHER_EXPENSE)
    @Expose
    private String otherExpense;

    @SerializedName(Constants.EXPENSE_DESCRIPTION)
    @Expose
    private String expenseDescription;

    @SerializedName(Constants.EXPENSE_IMAGE)
    @Expose
    private String expenseImage;

    @SerializedName(Constants.DAILY_ALLOWANCE)
    @Expose
    private String dailyAllowance;

    @SerializedName(Constants.LOCATION)
    @Expose
    private Location location;

    public Eod() {
        location = new Location();
    }

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

    public String getPetrolExpenseImage() {
        return petrolExpenseImage;
    }

    public void setPetrolExpenseImage(String petrolExpenseImage) {
        this.petrolExpenseImage = petrolExpenseImage;
    }

    public String getOtherExpense() {
        return otherExpense;
    }

    public void setOtherExpense(String otherExpense) {
        this.otherExpense = otherExpense;
    }

    public String getExpenseDescription() {
        return expenseDescription;
    }

    public void setExpenseDescription(String expenseDescription) {
        this.expenseDescription = expenseDescription;
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Eod{" +
                "id=" + id +
                ", empId=" + empId +
                ", date='" + date + '\'' +
                ", schoolVisits='" + schoolVisits + '\'' +
                ", petrolExpense='" + petrolExpense + '\'' +
                ", otherExpense='" + otherExpense + '\'' +
                ", expenseImage='" + expenseImage + '\'' +
                ", dailyAllowance='" + dailyAllowance + '\'' +
                ", location=" + location +
                '}';
    }
}
