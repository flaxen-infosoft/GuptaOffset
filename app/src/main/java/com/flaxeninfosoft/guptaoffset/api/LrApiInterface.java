package com.flaxeninfosoft.guptaoffset.api;

import com.flaxeninfosoft.guptaoffset.models.Lr;
import com.flaxeninfosoft.guptaoffset.models.Order;
import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LrApiInterface {

    @POST(ApiEndpoints.SEND_LR)
    Call<Lr> sendLr(@Body Lr lr);
}
