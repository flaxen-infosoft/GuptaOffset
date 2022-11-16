package com.flaxeninfosoft.guptaoffset.listeners;

public interface ApiResponseListener<X, Y> {

    void onSuccess(X response);

    void onFailure(Y error);

}
