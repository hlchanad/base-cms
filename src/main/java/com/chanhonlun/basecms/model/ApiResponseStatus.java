package com.chanhonlun.basecms.model;

import lombok.Data;

@Data
public class ApiResponseStatus {

    private double code;
    private String message;
    private String systemMessage;


    public ApiResponseStatus(double code, String message, String systemMessage) {
        this.code = code;
        this.message = message;
        this.systemMessage = systemMessage;
    }
}
