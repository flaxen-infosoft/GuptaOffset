package com.flaxeninfosoft.guptaoffset.api;

import com.flaxeninfosoft.guptaoffset.models.Client;
import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ClientApiInterface {

    @GET(ApiEndpoints.GET_ALL_CLIENTS)
    Call<List<Client>> getAllClients();

    @GET(ApiEndpoints.GET_CLIENT_BY_ID)
    Call<Client> getClientById(@Query("client_id") Long clientId);

    @POST(ApiEndpoints.UPDATE_CLIENT_BY_ID)
    Call<Client> updateClientById(@Query("client_id") Long clientId, @Body Client client);

    @GET(ApiEndpoints.GET_EMPLOYEE_CLIENTS_BY_ID)
    Call<List<Client>> getEmployeeClientsById(@Query("emp_id") Long empId);
}
