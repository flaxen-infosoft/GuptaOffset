package com.flaxeninfosoft.guptaoffset.api;

import com.flaxeninfosoft.guptaoffset.models.Dealer;
import com.flaxeninfosoft.guptaoffset.models.PaymentRequest;
import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;
import com.flaxeninfosoft.guptaoffset.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PaymentApiInterface {
    @POST(ApiEndpoints.ADD_PAYMENT)
    Call<PaymentRequest> addPayment(@Query(Constants.EMPLOYEE_ID) Long empId, @Body PaymentRequest paymentRequest);

    @GET(ApiEndpoints.GET_ALL_PENDING_REQUESTS)
    Call<List<PaymentRequest>> getAllPendingPaymentRequests(@Query("date") String date);

    @GET(ApiEndpoints.GET_PENDING_REQUESTS_TO_EMPLOYEE)
    Call<List<PaymentRequest>> getPendingRequestsToEmployee(@Query(Constants.EMPLOYEE_ID) Long empId);

    @POST(ApiEndpoints.UPDATE_PAYMENT_REQUEST)
    Call<PaymentRequest> updatePaymentRequest(@Query(Constants.PAYMENT_REQUEST_ID) Long id, @Body PaymentRequest payment);
}
