package com.flaxeninfosoft.guptaoffset.models;

import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Image {

    @SerializedName(Constants.ID)
    @Expose
    private String id;

    @SerializedName(Constants.TYPE)
    @Expose
    private String type;

    @SerializedName(Constants.EOD_ID)
    @Expose
    private String eodId;

    @SerializedName(Constants.ATN_ID)
    @Expose
    private String atnId;

    @SerializedName(Constants.ORDER_ID)
    @Expose
    private String orderId;

    @SerializedName(Constants.SCHOOL_ID)
    @Expose
    private String schoolId;

    @SerializedName(Constants.DEALER_ID)
    @Expose
    private String dealerId;

    @SerializedName(Constants.IMAGE)
    @Expose
    private String image;


}
