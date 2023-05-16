package com.flaxeninfosoft.guptaoffset.models;

import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentStatus {

    @SerializedName(Constants.DATE)
    @Expose
    private String date;

    @SerializedName(Constants.ID)
    @Expose
    private Long id;

    @SerializedName(Constants.EMPLOYEE_ID)
    @Expose
    private Long empid;

    @SerializedName(Constants.TIME)
    @Expose
    private String time;

    @SerializedName(Constants.AMOUNT_PAID)
    @Expose
    private Long amount_paid;

    @SerializedName(Constants.REMAINING_AMOUNT)
    @Expose
    private Long remaining_amount;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmpid() {
        return empid;
    }

    public void setEmpid(Long empid) {
        this.empid = empid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getAmount_paid() {
        return amount_paid;
    }

    public void setAmount_paid(Long amount_paid) {
        this.amount_paid = amount_paid;
    }

    public Long getRemaining_amount() {
        return remaining_amount;
    }

    public void setRemaining_amount(Long remaining_amount) {
        this.remaining_amount = remaining_amount;
    }
}

