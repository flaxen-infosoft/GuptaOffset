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

    @SerializedName(Constants.DAYS_LEAVE)
    @Expose
    private String daysLeave;

    @SerializedName(Constants.MESSAGE)
    @Expose
    private String message;

    @SerializedName(Constants.STATUS)
    @Expose
    private String status;

    @SerializedName(Constants.LOCATION)
    @Expose
    private Location location;

    public Leave() {
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
//        Yeah Boiii!!!
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getDaysLeave() {
        return daysLeave;
    }

    public void setDaysLeave(String daysLeave) {
        this.daysLeave = daysLeave;
    }

    @BindingAdapter("android:text")
    public static String setText(TextView textView, Date date) {
        if (date != null) {
            Log.d("CRM-LOG", date.toString());
            String dateText = date.toString();
            textView.setText(dateText);
            return dateText;
        } else {
            Log.d("CRM-LOG", "Date is null");
            return "";
        }
    }
}
