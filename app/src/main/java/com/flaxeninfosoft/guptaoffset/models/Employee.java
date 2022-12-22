package com.flaxeninfosoft.guptaoffset.models;

import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Employee {

    @SerializedName(Constants.ID)
    @Expose
    private Long id;

    @SerializedName(Constants.NAME)
    @Expose
    private String name;

    @SerializedName(Constants.EMAIL)
    @Expose
    private String email;

    @SerializedName(Constants.PASSWORD)
    @Expose
    private String password;

    @SerializedName(Constants.DESIGNATION)
    @Expose
    private String designation;

    @SerializedName(Constants.AREA)
    @Expose
    private String area;

    @SerializedName(Constants.DAILY_ALLOWANCE)
    @Expose
    private String dailyAllowance;

    @SerializedName(Constants.STATUS)
    @Expose
    private String status;

    @SerializedName(Constants.PHONE)
    @Expose
    private String phone;

    @SerializedName(Constants.SALARY)
    @Expose
    private String salary;

    @SerializedName(Constants.TOKEN)
    @Expose
    private String token;

    @SerializedName(Constants.ASSIGNED_TO)
    @Expose
    private Long assignedTo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDailyAllowance() {
        return dailyAllowance;
    }

    public void setDailyAllowance(String dailyAllowance) {
        this.dailyAllowance = dailyAllowance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(Long assignedTo) {
        this.assignedTo = assignedTo;
    }
}
