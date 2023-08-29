package com.flaxeninfosoft.guptaoffset.models;

import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Products {


    @SerializedName(Constants.PRODUCT_ID)
    @Expose
    private Long product_id;

    @SerializedName(Constants.PRODUCT_NAME)
    @Expose
    private String product_name;

    public Products(Long product_id, String product_name) {
        this.product_id = product_id;
        this.product_name = product_name;
    }

    public Products() {
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }
}
