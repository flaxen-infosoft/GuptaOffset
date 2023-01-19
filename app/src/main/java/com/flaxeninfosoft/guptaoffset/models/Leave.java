package com.flaxeninfosoft.guptaoffset.models;

import android.util.Log;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Date;

public class Leave {

    @SerializedName(Constants.ID)
    @Expose
    private Long id;

    @SerializedName(Constants.EMPLOYEE_ID)
    @Expose
    private Long empId;

    @SerializedName(Constants.FROM_DATE)
    @Expose
    private String fromDate;

    @SerializedName(Constants.TO_DATE)
    @Expose
    private String toDate;

    @SerializedName(Constants.APPLY_DATE)
    @Expose
    private String applyDate;

    @SerializedName(Constants.MESSAGE)
    @Expose
    private String message;

    @SerializedName(Constants.STATUS)
    @Expose
    private String status;

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

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @BindingAdapter("android:text")
    public static String setText(TextView textView, Date date){
        if (date != null){
            Log.d("CRM-LOG", date.toString());
            String dateText = date.toString();
            textView.setText(dateText);
            return dateText;
        }else{
            Log.d("CRM-LOG", "Date is null");
            return "";
        }
    }
}
