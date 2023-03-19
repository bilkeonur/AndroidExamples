package com.ob.retrofitrxjava.model;

import com.google.gson.annotations.SerializedName;

public class LoginResponseModel {
    @SerializedName("code")
    int code;

    @SerializedName("message")
    String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
