package com.chanhonlun.basecms.exception;

import com.chanhonlun.basecms.model.ApiResponseStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ApiException extends Exception {

    private ApiResponseStatus status;

    private Object data;

    public ApiException(ApiResponseStatus status) {
        this.status = status;
        this.data   = null;
    }

    public ApiException(ApiResponseStatus status, Object data) {
        this.status = status;
        this.data   = data;
    }
}
