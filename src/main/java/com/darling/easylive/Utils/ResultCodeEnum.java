package com.darling.easylive.Utils;

public enum ResultCodeEnum {
    SUCCESS(200,"success"),

    FAILED(600,"failed"),

    LOGIN_FAILED(400,"用户登录失败，账号或密码不正确"),

    USER_NOT_FOUND(7004,"user_not_found"),
    USER_HAVE_EXIST(701,"用户已经存在"),

    UPLOAD_FIALED(7001,"file_upload_failed"),
    USER_REPEAT(7007,"user_repeat"),

    CATID_EXIST(7008,"catId_already_exists"),

    CAGE_NOT_EMPTY(7009,"cage_not_empty"),

    AUTH_ERROR(7002,"passwd_error"),
    CAGE_NOT_FOUND(7005,"cage_not_found"),

    CAGE_EXIST(7006,"cage_already_exist"),
    INFO_NOT_FOUND(7003,"info_not_found" );



    private Integer code;
    private String message;
    private ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public Integer getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }
}
