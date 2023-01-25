package com.flaxeninfosoft.guptaoffset.api;

import com.flaxeninfosoft.guptaoffset.models.Message;
import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;
import com.flaxeninfosoft.guptaoffset.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MessageApiInterface {

    @GET(ApiEndpoints.GET_MESSAGES)
    Call<List<Message>> getMessages(@Query(Constants.ID) Long id);

    @POST(ApiEndpoints.SEND_MESSAGE)
    Call<Message> sendMessage(@Query(Constants.EMPLOYEE_ID) Long empId, @Body Message message);
}
