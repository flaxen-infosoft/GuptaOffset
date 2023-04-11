package com.flaxeninfosoft.guptaoffset.models;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.flaxeninfosoft.guptaoffset.R;

public class AllOrder {

    private Long id;
    private Long empId;
    private String snap;
    private String date;
    private String dbo;
    private String name;
    private String address;

    public AllOrder() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public String getSnap() {
        return snap;
    }

    public void setSnap(String snap) {
        this.snap = snap;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDbo() {
        return dbo;
    }

    public void setDbo(String dbo) {
        this.dbo = dbo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @BindingAdapter("set_image")
    public static void set_image(ImageView view, String imageUrl) {
        if (imageUrl != null && !imageUrl.isEmpty()){
            Glide.with(view).load(imageUrl).placeholder(R.drawable.loading_image).into(view);
        }
    }
}
