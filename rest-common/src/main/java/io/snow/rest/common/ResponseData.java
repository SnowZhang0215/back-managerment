package io.snow.rest.common;


import java.io.Serializable;

public class ResponseData<T> implements Serializable {


    private int errorCode;
    private String errorMsg;

    private T data;

    public static <T>ResponseData<T> unAuthorized(String errorMsg) {
        ResponseData<T> responseData = new ResponseData<>();
        responseData.errorCode = 401;
        responseData.errorMsg = errorMsg;
        return responseData;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public T getData() {
        return data;
    }

    private void setData(T data) {
        this.data = data;
    }

    public static <T> ResponseData<T> ok(T body) {
        ResponseData<T> responseData = new ResponseData<>();
        responseData.errorCode = 200;
        responseData.errorMsg = "success";
        responseData.setData(body);
        return responseData;
    }

    public static <T> ResponseData<T> error(String errorMsg) {
        ResponseData<T> responseData = new ResponseData<>();
        responseData.errorCode = 500;
        responseData.errorMsg = errorMsg;
        responseData.setData(null);
        return responseData;
    }

    public static <T> ResponseData<T> error(int errorCode,String errorMsg) {
        ResponseData<T> responseData = new ResponseData<>();
        responseData.errorCode = errorCode;
        responseData.errorMsg = errorMsg;
        responseData.setData(null);
        return responseData;
    }



    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
