package com.flaxeninfosoft.guptaoffset.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShowNotes {
    @SerializedName("notes")
    @Expose
   private String notes;

    @SerializedName("id")
    @Expose
   private static Long id;

    @SerializedName("date")
    @Expose
   private String date;

    @SerializedName("time")
    @Expose
   private String time;

    public ShowNotes() {
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public static Long getId() {
        return id;
    }

    public void setId(Long id) {
        ShowNotes.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ShowNotes(String notes, Long id, String date, String time) {
        this.notes = notes;
        this.id = id;
        this.date = date;
        this.time = time;
    }
}
