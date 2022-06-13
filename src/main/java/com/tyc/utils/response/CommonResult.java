package com.tyc.utils.response;

/**
 * 类描述
 *
 * @author tyc
 * @version 1.0
 * @date 2022-06-13 11:15:14
 */
public class CommonResult<T> {
    private int code;
    private String message;
    private T resultData;

    public static <T> CommonResult<T> success(T t) {
        return new CommonResult<>(200, "success", t);
    }

    public static <T> CommonResult<T> failed(String message) {
        return new CommonResult<>(500, message);
    }

    public static <T> CommonResult<T> failed() {
        return new CommonResult<>(500);
    }

    public static <T> CommonResult<T> failed(int code, String message) {
        return new CommonResult<>(code, message);
    }


    public CommonResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public CommonResult(int code, T resultData) {
        this.code = code;
        this.resultData = resultData;
    }

    public CommonResult(int code, String message, T resultData) {
        this.code = code;
        this.message = message;
        this.resultData = resultData;
    }

    public CommonResult(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResultData() {
        return resultData;
    }

    public void setResultData(T resultData) {
        this.resultData = resultData;
    }

}
