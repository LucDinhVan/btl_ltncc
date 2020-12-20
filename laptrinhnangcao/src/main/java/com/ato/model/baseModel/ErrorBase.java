package com.ato.model.baseModel;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorBase {
    public static final String RESULT_OK = "200";
    public static final String RESULT_ERROR_BUSINESS = "400";
    public static final String RESULT_ERROR_PART = "http://localhost:4200/auths/login";
    private String errorMessage;
    private String status;
    private Object data;
    private String part;



}
