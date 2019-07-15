package cn.letsky.wechat.constant;

/**
 * 实体类型常量
 */
public class EntityType {

    /**
     * 文章实体类型
     */
    public static final Integer ARTICLE = 0;

    /**
     * 评论实体类型
     */
    public static final Integer COMMENT = 1;

    /**
     * 是否包含给定的实体类型
     *
     * @param entityType 实体类型编号
     * @return
     */
    public static boolean contains(Integer entityType){
        if (entityType.equals(ARTICLE) || entityType.equals(COMMENT)) {
            return true;
        }
        return false;
    }
}
