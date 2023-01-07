package com.flaxeninfosoft.guptaoffset.models;

import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class School {

    @SerializedName(Constants.ID)
    @Expose
    private Long id;

    @SerializedName(Constants.EMPLOYEE_ID)
    @Expose
    private Long empId;

    @SerializedName(Constants.NAME)
    @Expose
    private String name;

    @SerializedName(Constants.STUDENT_STRENGTH)
    @Expose
    private String strength;

    @SerializedName(Constants.LATITUDE)
    @Expose
    private String latitude;

    @SerializedName(Constants.LONGITUDE)
    @Expose
    private String longitude;

    @SerializedName(Constants.ADDRESS)
    @Expose
    private String address;

    @SerializedName(Constants.IMAGE)
    @Expose
    private String image;

    @SerializedName(Constants.PHONE)
    @Expose
    private String contact;



}
