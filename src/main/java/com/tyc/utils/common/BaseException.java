package com.tyc.utils.common;

/**
 * 类描述
 *
 * @author tyc
 * @version 1.0
 * @date 2022-06-13 15:46:07
 */
public class BaseException extends RuntimeException{
    private int code;
    private String msg;

    public BaseException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
}
