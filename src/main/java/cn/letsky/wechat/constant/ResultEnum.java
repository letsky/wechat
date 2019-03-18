package cn.letsky.wechat.constant;

import lombok.Getter;

@Getter
public enum ResultEnum {

    DELETE(1, "文章被删除"),
    SUCCESS(200, "success"),
    ERROR(10000, "程序内部错误"),
    PARAM_ERROR(10001, "参数不正确"),
    OPENID_ERROR(10002, "openid错误"),
    ENTITY_NOT_FOUNT(10003, "实体不存在"),
    ;

    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
