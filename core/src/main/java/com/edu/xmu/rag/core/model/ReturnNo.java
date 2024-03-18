package com.edu.xmu.rag.core.model;

public enum ReturnNo {
    OK(0,"成功"),
    CREATED(1, "创建成功"),
    INTERNAL_SERVER_ERR(2,"服务器内部错误"),
    FIELD_NOT_VALID(3,"%s字段不合法"),
    RESOURCE_ID_NOT_EXIST(4,"%s对象(id=%d)不存在"),
    AUTH_INVALID_JWT(5,"JWT不合法"),
    AUTH_JWT_EXPIRED(6,"JWT过期"),
    STATE_NOT_ALLOW(7,"%s对象（id=%d）%s状态禁止此操作"),
    IMG_FORMAT_ERROR(8,"图片格式不正确"),
    IMG_SIZE_EXCEED(9,"图片大小超限"),
    PARAMETER_MISSED(10, "缺少必要参数"),
    AUTH_INVALID_ACCOUNT(11, "用户名不存在或者密码错误"),
    AUTH_ID_NOT_EXIST(12,"登录用户id不存在"),
    AUTH_USER_FORBIDDEN(13,"用户被禁止登录"),
    AUTH_NEED_LOGIN(14, "需要先登录"),
    ;

    private final int errNo;
    private final String message;

    ReturnNo(int code, String message) {
        this.errNo = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static ReturnNo getByCode(int code1) {
        ReturnNo[] all = ReturnNo.values();
        for (ReturnNo returnNo :all) {
            if (returnNo.errNo == code1) {
                return returnNo;
            }
        }
        return null;
    }
}
