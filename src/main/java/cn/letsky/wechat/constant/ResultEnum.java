package cn.letsky.wechat.constant;

import lombok.Getter;

/**
 * 返回结果常量
 */
@Getter
public enum ResultEnum {

    SUCCESS(200, "success"),
    FAIL(404, "接口调用失败"),

    ERROR(10000, "程序内部错误"),
    PARAM_ERROR(10001, "参数不正确"),
    OPENID_ERROR(10002, "openid错误"),
    ENTITY_TYPE_ERROR(10003, "实体类型不正确"),
    ENTITY_NOT_FOUNT(10004, "实体不存在"),
    UPLOAD_ERROR(10005, "服务端上传图片出错"),
    PAGE_PARAM_ERROR(10006, "分页参数不正确"),
    EMPTY_COMMENT(10007, "还没有评论"),
    NULL_WORD_FILE(10008, "未找到敏感词文件"),

    NOT_SUPPORT_CONTENT_TYPE(70000, "当前Content-Type不支持"),
    NOT_SUPPORT_SUFFIX(70001, "当前后缀名不支持"),
    EMPTY_FILENAME(70002, "文件名为空"),
    INCORRECT_FILENAME(70003, "文件名有误"),
    EMPTY_PICTURE(70004, "上传的图片内容为空"),
    NOT_SUPPORT_REQUEST_METHOD(70005, "当前请求方法不支持"),

    WECHAT_LOGIN_ERROR(80000, "微信登录异常"),
    WECHAT_CODE_EMPTY(80001, "微信code为空"),

    SESSION_EXPIRED(90000, "客户端session过期"),
    SESSION_ERROR(90001, "session无效"),
    EXCEED_PICTURE_LIMIT(90002, "图片上传超过限制"),
    NOT_PICTURE(90003, "上传的不是图片"),
    SEND_NULL_COMMENT(90004, "评论为空"),
    NOT_REGISTER(90006, "用户未注册"),
    SENSITIVE_WORD(90007, "存在敏感字符"),
    ;

    private int code;

    private String msg;

    ResultEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
