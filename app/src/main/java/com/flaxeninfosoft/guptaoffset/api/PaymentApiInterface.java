package com.flaxeninfosoft.guptaoffset.api;

import com.flaxeninfosoft.guptaoffset.models.Dealer;
import com.flaxeninfosoft.guptaoffset.models.PaymentRequest;
import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;
import com.flaxeninfosoft.guptaoffset.utils.Constants;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PaymentApiInterface {
    @POST(ApiEndpoints.ADD_PAYMENT)
    Call<PaymentRequest> addPayment(@Query(Constants.EMPLOYEE_ID) Long empId, @Body PaymentRequest paymentRequest);

}
