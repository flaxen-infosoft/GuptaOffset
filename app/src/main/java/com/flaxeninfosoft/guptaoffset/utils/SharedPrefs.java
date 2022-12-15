package com.flaxeninfosoft.guptaoffset.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.flaxeninfosoft.guptaoffset.models.Employee;
import com.flaxeninfosoft.guptaoffset.models.LoginModel;

public class SharedPrefs {

    private final SharedPreferences pref;
    private Employee currentEmployee;

    public static final String PREFERENCE = "GUPTA_OFFSET";
    public static final int MODE = Context.MODE_PRIVATE;

    private static final String EMAIL = "EMP_EMAIL";
    private static final String PASSWORD = "EMP_PASSWORD";

    private static SharedPrefs instance;

    private SharedPrefs(Context context){
        pref = context.getSharedPreferences(PREFERENCE,MODE);
    }

    public static SharedPrefs getInstance(Context context){
        if (instance==null){
            instance = new SharedPrefs(context);
        }

        return instance;
    }

    public void clearCredentials(){
        SharedPreferences.Editor editor = pref.edit();
        editor.clear().apply();
        currentEmployee = null;
    }

    public void setCredentials(LoginModel credentials){
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(EMAIL, credentials.getEmail());
        editor.putString(PASSWORD, credentials.getPassword());
        editor.apply();
    }

    public LoginModel getCredentials(){
        LoginModel model = new LoginModel();
        model.setEmail(pref.getString(EMAIL,null));
        model.setPassword(pref.getString(PASSWORD, null));
        Log.e("TEST", model.getEmail());
        Log.e("TEST", model.getPassword());
        if (model.getEmail()==null || model.getPassword()==null){
            return null;
        }
        return model;
    }

    public boolean isLoggedIn(){
        LoginModel model = new LoginModel();
        model.setEmail(pref.getString(EMAIL,null));
        model.setPassword(pref.getString(PASSWORD, null));
        return model.getEmail() != null && model.getPassword() != null;
    }

    public Employee getCurrentEmployee(){
        return currentEmployee;
    }

    public void setCurrentEmployee(Employee employee){
        currentEmployee = employee;
    }
}
