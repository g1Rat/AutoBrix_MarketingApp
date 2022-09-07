package com.autobrix.autobrix_marketingapp.pojos;

import com.google.gson.annotations.SerializedName;

public class Credential {
    @SerializedName("aws_secret")
    private String aws_secret;
    @SerializedName("aws_key")
    private String aws_key;

    public String getAws_secret() {
        return aws_secret;
    }

    public void setAws_secret(String aws_secret) {
        this.aws_secret = aws_secret;
    }

    public String getAws_key() {
        return aws_key;
    }

    public void setAws_key(String aws_key) {
        this.aws_key = aws_key;
    }
}
