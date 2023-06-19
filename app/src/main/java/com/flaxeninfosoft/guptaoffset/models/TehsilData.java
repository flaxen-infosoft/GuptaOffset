package com.flaxeninfosoft.guptaoffset.models;

import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TehsilData {


    @SerializedName(Constants.TEHSIL_ID)
    @Expose
    private Long tehsil_id;

    @SerializedName(Constants.TEHSIL_NAME)
    @Expose
    private String tehsil_name;

    @SerializedName(Constants.TEHSIL_VISITED)
    @Expose
    private int visited_tehsil;

    @SerializedName(Constants.TEHSIL_SCHOOL_COUNT)
    @Expose
    private String tehsil_school_count;


    public Long getTehsil_id() {
        return tehsil_id;
    }

    public void setTehsil_id(Long tehsil_id) {
        this.tehsil_id = tehsil_id;
    }

    public String getTehsil_name() {
        return tehsil_name;
    }

    public void setTehsil_name(String tehsil_name) {
        this.tehsil_name = tehsil_name;
    }

    public int getVisited_tehsil() {
        return visited_tehsil;
    }

    public void setVisited_tehsil(int visited_tehsil) {
        this.visited_tehsil = visited_tehsil;
    }

    public String getTehsil_school_count() {
        return tehsil_school_count;
    }

    public void setTehsil_school_count(String tehsil_school_count) {
        this.tehsil_school_count = tehsil_school_count;
    }
}
