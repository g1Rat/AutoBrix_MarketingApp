package com.autobrix.autobrix_marketingapp.common_classes;

public interface ResponseListener {
    public void ResponseSuccess(ResponseExtension response);
    public void ResponseFailure(int responseCode, String errorMsg);
}
