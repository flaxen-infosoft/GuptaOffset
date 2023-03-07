package com.flaxeninfosoft.guptaoffset.models;

import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Lr {

    @SerializedName(Constants.ID)
    @Expose
    private Long id;

    @SerializedName(Constants.IMAGE)
    @Expose
    private String image;

    @SerializedName(Constants.DATE)
    @Expose
    private String date;

    @SerializedName(Constants.EMPLOYEE_ID)
    @Expose
    private Long empId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    @Override
    public String toString() {
        return "Lr{" +
                "id=" + id +
                ", image='" + image + '\'' +
                ", date='" + date + '\'' +
                ", empId='" + empId + '\'' +
                '}';
    }
}
