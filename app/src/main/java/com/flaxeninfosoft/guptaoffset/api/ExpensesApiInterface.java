package com.flaxeninfosoft.guptaoffset.api;

import com.flaxeninfosoft.guptaoffset.models.Expense;
import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;
import com.flaxeninfosoft.guptaoffset.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ExpensesApiInterface {

    @GET(ApiEndpoints.GET_EXPENSE_BY_ID)
    Call<Expense> getExpenseById(@Query(Constants.EXPENSE_ID) Long expId);

    @GET(ApiEndpoints.GET_EMPLOYEE_ALL_EXPENSES)
    Call<List<Expense>> getEmployeeExpenses(@Query(Constants.EMPLOYEE_ID) Long empId);

    @GET(ApiEndpoints.GET_ALL_EXPENSES)
    Call<List<Expense>> getAllExpenses();

    @POST(ApiEndpoints.ADD_EXPENSE)
    Call<Expense> addExpense(@Body Expense expense);

}
