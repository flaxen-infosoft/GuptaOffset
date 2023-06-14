package com.flaxeninfosoft.guptaoffset.models;

import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Date;

public class PaymentRequest {

    @SerializedName(Constants.ID)
    @Expose
    private Long id;

    @SerializedName(Constants.EMPLOYEE_ID)
    @Expose
    private Long empId;

    @SerializedName(Constants.AMOUNT)
    @Expose
    private String amount;

    @SerializedName(Constants.STATUS)
    @Expose
    private String status;

    @SerializedName(Constants.DATE)
    @Expose
    private String date;

    @SerializedName(Constants.LOCATION)
    @Expose
    private Location location;

    @SerializedName(Constants.EMPLOYEE)
    @Expose
    private Employee employee;

    @SerializedName(Constants.AMOUNT_PAID)
    @Expose
    private String received;

    @SerializedName(Constants.PENDING_AMOUNT)
    @Expose
    private String pending;

    @SerializedName(Constants.MESSAGE)
    @Expose
    private String message;

    public String getPending() {
        return pending;
    }

    public void setPending(String pending) {
        this.pending = pending;
    }



    public PaymentRequest() {
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

    public String getAmount() {
        return amount;
//        Yeah Boiii!!!
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getReceived() {
        return received;
    }

    public void setReceived(String received) {
        this.received = received;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "PaymentRequest{" +
                "id=" + id +
                ", empId=" + empId +
                ", amount='" + amount + '\'' +
                ", status='" + status + '\'' +
                ", date='" + date + '\'' +
                ", location=" + location +
                ", employee=" + employee +
                ", received='" + received + '\'' +
                '}';
    }
}
