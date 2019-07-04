package cn.letsky.wechat.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EntityType {

    ARTICLE(0, "帖子"),
    COMMENT(1, "评论"),
    ;

    private int type;
    private String msg;

    public static boolean contains(Integer entityType){
        for (EntityType type : EntityType.values()){
            if (type.getType() == entityType){
                return true;
            }
        }
        return false;
    }
}
