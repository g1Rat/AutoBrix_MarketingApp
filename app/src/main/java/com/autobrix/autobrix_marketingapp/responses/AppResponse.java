package com.autobrix.autobrix_marketingapp.responses;

import com.autobrix.autobrix_marketingapp.responses.ResponseModel;
import com.google.gson.annotations.SerializedName;

public class AppResponse
{
    @SerializedName("responseType")
    private String responseType;
    @SerializedName("response")
    private ResponseModel response;
    @SerializedName("message")
    private String message;

    public String getResponseType() {
        return responseType;
    }
    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }
    public ResponseModel getResponseModel() {
        return response;
    }
    public void setResponseModel(ResponseModel responseModel) {
        this.response = responseModel;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
