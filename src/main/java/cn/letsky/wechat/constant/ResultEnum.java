package cn.letsky.wechat.constant;

import lombok.Getter;

@Getter
public enum ResultEnum implements CodeEnum {

    DELETE(1, "文章被删除"),
    SUCCESS(200, "success"),
    FAIL(404, "接口调用失败"),
    ERROR(10000, "程序内部错误"),
    PARAM_ERROR(10001, "参数不正确"),
    OPENID_ERROR(10002, "openid错误"),
    ENTITY_NOT_FOUNT(10003, "实体不存在"),

    WECHAT_LOGIN_ERROR(80000, "微信登录异常"),
    SESSION_EXPIRED(90000, "客户端session过期"),
    ;

    private int code;

    private String msg;

    ResultEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
