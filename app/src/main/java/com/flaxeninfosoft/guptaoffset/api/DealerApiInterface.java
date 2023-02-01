package com.flaxeninfosoft.guptaoffset.api;

import com.flaxeninfosoft.guptaoffset.models.Dealer;
import com.flaxeninfosoft.guptaoffset.models.Eod;
import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;
import com.flaxeninfosoft.guptaoffset.utils.Constants;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface DealerApiInterface {

    @POST(ApiEndpoints.ADD_DEALER)
    Call<Dealer> addDealer(@Query(Constants.EMPLOYEE_ID) Long empId, @Body Dealer dealer);

    @GET(ApiEndpoints.GET_DEALER_BY_ID)
    Call<Dealer>getDealerById(@Query(Constants.DEALER_ID) Long dealerId);

}
