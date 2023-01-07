package com.flaxeninfosoft.guptaoffset.models;

import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dealer {

    @SerializedName(Constants.ID)
    @Expose
    private String id;

    @SerializedName(Constants.EMPLOYEE_ID)
    @Expose
    private String empId;

    @SerializedName(Constants.NAME)
    @Expose
    private String name;

    @SerializedName(Constants.PHONE)
    @Expose
    private String contact;

    @SerializedName(Constants.LATITUDE)
    @Expose
    private String latitude;

    @SerializedName(Constants.LONGITUDE)
    @Expose
    private String longitude;

    @SerializedName(Constants.IMAGE)
    @Expose
    private String image;

}
