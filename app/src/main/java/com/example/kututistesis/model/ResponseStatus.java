package com.example.kututistesis.model;

import com.google.gson.annotations.SerializedName;

public class ResponseStatus {
    private String status;
    private String code;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
