package com.flaxeninfosoft.guptaoffset.api;

import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;
import com.flaxeninfosoft.guptaoffset.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ClientApiInterface {

    @GET(ApiEndpoints.GET_CLIENT_BY_ID)
    Call<Client> getClientById(@Query(Constants.CLIENT_ID) Long clientId);

    @GET(ApiEndpoints.GET_ALL_CLIENTS)
    Call<List<Client>> getAllClients();

    @GET(ApiEndpoints.GET_EMPLOYEE_CLIENTS_BY_ID)
    Call<List<Client>> getEmployeeClientsById(@Query(Constants.EMPLOYEE_ID) Long empId);

    @POST(ApiEndpoints.UPDATE_CLIENT_BY_ID)
    Call<Client> updateClientById(@Body Client client);

    @POST(ApiEndpoints.ADD_CLIENT)
    Call<Client> addClient(@Body Client client);
}
