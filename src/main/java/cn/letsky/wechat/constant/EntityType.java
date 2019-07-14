package cn.letsky.wechat.constant;

public class EntityType {

    /**
     * 文章实体类型
     */
    public static final Integer ARTICLE = 0;

    /**
     * 评论实体类型
     */
    public static final Integer COMMENT = 1;


    public static boolean contains(Integer entityType){
        if (entityType.equals(ARTICLE) || entityType.equals(COMMENT)) {
            return true;
        }
        return false;
    }
}
