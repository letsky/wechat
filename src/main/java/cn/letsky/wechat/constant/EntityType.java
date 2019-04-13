package cn.letsky.wechat.constant;

import lombok.Getter;

@Getter
public enum EntityType {

    ARTICLE(0, "帖子"),
    COMMENT(1, "评论"),
    ;

    private int code;

    private String msg;

    EntityType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static boolean contains(Integer entityType){
        for (EntityType type : EntityType.values()){
            if (type.getCode() == entityType){
                return true;
            }
        }
        return false;
    }
}
