package cn.letsky.wechat.constant;

import lombok.Getter;

@Getter
public enum ResultEnum implements CodeEnum {

    SUCCESS(200, "success"),
    FAIL(404, "接口调用失败"),
    ERROR(10000, "程序内部错误"),
    PARAM_ERROR(10001, "参数不正确"),
    OPENID_ERROR(10002, "openid错误"),
    ENTITY_TYPE_ERROR(10003, "实体类型不正确"),
    ENTITY_NOT_FOUNT(10004, "实体不存在"),
    UPLOAD_ERROR(10005, "服务端上传图片出错"),
    PAGE_PARAM_ERROR(10006, "分页参数不正确"),
    NULL_COMMENT(10007, "还没有评论"),

    WECHAT_LOGIN_ERROR(80000, "微信登录异常"),

    SESSION_EXPIRED(90000, "客户端session过期"),
    NULL_PICTURE(90001, "上传的图片为空"),
    EXCEED_PICTURE_LIMIT(90002, "图片上传超过限制"),
    NOT_PICTURE(90003, "上传的不是图片"),
    ;

    private int code;

    private String msg;

    ResultEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}