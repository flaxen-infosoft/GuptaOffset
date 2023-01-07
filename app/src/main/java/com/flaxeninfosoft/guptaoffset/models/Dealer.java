package com.flaxeninfosoft.guptaoffset.models;

import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dealer {

    @SerializedName(Constants.ID)
    @Expose
    private Long id;

    @SerializedName(Constants.EMPLOYEE_ID)
    @Expose
    private Long empId;

    @SerializedName(Constants.NAME)
    @Expose
    private String name;

    @SerializedName(Constants.PHONE)
    @Expose
    private String contact;

    @SerializedName(Constants.LATITUDE)
    @Expose
    private Double latitude;

    @SerializedName(Constants.LONGITUDE)
    @Expose
    private Double longitude;

    @SerializedName(Constants.IMAGE)
    @Expose
    private String image;

}
