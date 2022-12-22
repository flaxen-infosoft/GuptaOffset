package com.flaxeninfosoft.guptaoffset.models;

import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Date;
import java.sql.Time;

public class Eod {

    @SerializedName(Constants.ID)
    @Expose
    private Long id;

    @SerializedName(Constants.EMPLOYEE_ID)
    @Expose
    private Long empId;

    @SerializedName(Constants.DATE)
    @Expose
    private Date date;

    @SerializedName(Constants.TIME_IN)
    @Expose
    private Time timeIn;

    @SerializedName(Constants.TIME_OUT)
    @Expose
    private Time timeOut;

    @SerializedName(Constants.START_METER)
    @Expose
    private String startMeter;

    @SerializedName(Constants.END_METER)
    @Expose
    private String endMeter;

    @SerializedName(Constants.START_METER_IMAGE)
    @Expose
    private String startMeterImage;

    @SerializedName(Constants.END_METER_IMAGE)
    @Expose
    private String endMeterImage;

}
