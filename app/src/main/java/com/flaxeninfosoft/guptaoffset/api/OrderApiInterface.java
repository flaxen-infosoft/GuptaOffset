package com.flaxeninfosoft.guptaoffset.api;

import com.flaxeninfosoft.guptaoffset.models.Order;
import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;
import com.flaxeninfosoft.guptaoffset.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface OrderApiInterface {

    @GET(ApiEndpoints.GET_ORDER_BY_ID)
    Call<Order> getOrderById(@Query(Constants.ORDER_ID) Long orderId);

    @GET(ApiEndpoints.GET_EMPLOYEE_ORDERS)
    Call<List<Order>> getEmployeeOrders(@Query(Constants.EMPLOYEE_ID) Long empId);

    @GET(ApiEndpoints.GET_ALL_ORDERS)
    Call<List<Order>> getAllOrders();

    @POST(ApiEndpoints.ADD_ORDER)
    Call<Order> addOrder(@Body Order order);

}
