package com.flaxeninfosoft.guptaoffset.api;

import com.flaxeninfosoft.guptaoffset.models.Message;
import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ChatApiInterface {

    @GET(ApiEndpoints.GET_MESSAGES)
    Call<List<Message>> getMessages();

    @POST(ApiEndpoints.ADD_MESSAGE)
    Call<Message> addMessage(@Body Message message);
}
