package com.flaxeninfosoft.guptaoffset.models;

import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DistrictData {


    @SerializedName(Constants.DISTRICT_NAME)
    @Expose
    private String district_name;

    @SerializedName(Constants.DISTRICT_IMAGE)
    @Expose
    private String district_image;

    @SerializedName(Constants.DISTRICT_ID)
    @Expose
    private Long district_id;


    public Long getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(Long district_id) {
        this.district_id = district_id;
    }

    public String getDistrict_name() {
        return district_name;
    }

    public void setDistrict_name(String district_name) {
        this.district_name = district_name;
    }

    public String getDistrict_image() {
        return district_image;
    }

    public void setDistrict_image(String district_image) {
        this.district_image = district_image;
    }

    public DistrictData(String district_name, String district_image, Long district_id) {
        this.district_name = district_name;
        this.district_image = district_image;
        this.district_id = district_id;
    }
}
