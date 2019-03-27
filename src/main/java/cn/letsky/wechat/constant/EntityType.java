package cn.letsky.wechat.constant;

import lombok.Getter;

@Getter
public enum EntityType {

    ARTITLE(0, "帖子")
    ;

    private int code;

    private String msg;

    EntityType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
