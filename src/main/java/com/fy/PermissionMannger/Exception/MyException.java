package com.fy.PermissionMannger.Exception;

/**
 * 〈自定义异常处理〉
 *
 * @author fangyan
 * @create 2019/5/4
 * @since 1.0.0
 */
public class MyException extends RuntimeException {
    String code;
    String msg;

    public MyException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
