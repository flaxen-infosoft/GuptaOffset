package com.flaxeninfosoft.guptaoffset.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EOD {
    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("date")
    @Expose
    private Long date;

    @SerializedName("starting_meter")
    @Expose
    private Long startingMeter;

    @SerializedName("ending_meter")
    @Expose
    private Long endingMeter;

    @SerializedName("starting_meter_image")
    @Expose
    private String startingMeterImage;

    @SerializedName("ending_meter_image")
    @Expose
    private String endingMeterImage;

    public EOD(){
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public Long getStartingMeter() {
        return startingMeter;
    }

    public void setStartingMeter(Long startingMeter) {
        this.startingMeter = startingMeter;
    }

    public Long getEndingMeter() {
        return endingMeter;
    }

    public void setEndingMeter(Long endingMeter) {
        this.endingMeter = endingMeter;
    }

    public String getStartingMeterImage() {
        return startingMeterImage;
    }

    public void setStartingMeterImage(String startingMeterImage) {
        this.startingMeterImage = startingMeterImage;
    }

    public String getEndingMeterImage() {
        return endingMeterImage;
    }

    public void setEndingMeterImage(String endingMeterImage) {
        this.endingMeterImage = endingMeterImage;
    }
}
