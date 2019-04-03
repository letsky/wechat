package cn.letsky.wechat.constant;

import lombok.Getter;

@Getter
public enum StatusEnum implements CodeEnum {

    ARTICLE_NORMAL(0, "文章正常"),
    ARTICLE_DELETE(1, "文章被删除"),
    ALLOW_COMMENT(0, "允许评论"),
    NOT_ALLOW_COMMENT(1, "不允许评论"),
    ;

    private int code;

    private String msg;

    StatusEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
