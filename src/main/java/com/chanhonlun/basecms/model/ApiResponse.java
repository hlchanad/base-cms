package com.chanhonlun.basecms.model;

import com.chanhonlun.basecms.constant.ApiResponseCode;
import lombok.Data;

@Data
public class ApiResponse {

    private ApiResponseStatus status;

    private Object data;

    public ApiResponse() {
        this(ApiResponseCode.STATUS_200_000_SUCCESS);
    }

    public ApiResponse(ApiResponseStatus status) {
        this(status, null);
    }

    public ApiResponse(ApiResponseStatus status, Object data) {
        this.status = status;
        this.data   = data;
    }

}
