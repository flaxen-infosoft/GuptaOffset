package com.flaxeninfosoft.guptaoffset.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShowNotes {
    @SerializedName("note")
    @Expose
   private String note;

    @SerializedName("id")
    @Expose
   private static Long id;

    @SerializedName("date")
    @Expose
   private String date;

    @SerializedName("time")
    @Expose
   private String time;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


    public void setNotes(String note) {
        this.note = note;
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

    public ShowNotes(String note, Long id, String date, String time) {
        this.note = note;
        this.id = id;
        this.date = date;
        this.time = time;
    }
}
