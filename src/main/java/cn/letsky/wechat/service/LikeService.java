package cn.letsky.wechat.service;

public interface LikeService {

    /**
     * 点赞
     *
     * @param openid     微信用户id
     * @param entityType 点赞的实体类型
     * @param entityId   点赞的实体id
     * @return 点赞数
     */
    Long like(String openid, Integer entityType, Integer entityId);

    /**
     * 取消点赞
     *
     * @param openid     微信用户id
     * @param entityType 取消点赞的实体类型
     * @param entityId   取消点赞的实体id
     * @return 点赞数
     */
    Long cancelLike(String openid, Integer entityType, Integer entityId);

    /**
     * 获取用户点赞状态
     *
     * @param openid     微信用户id
     * @param entityType 需要获取点赞状态的实体类型
     * @param entityId   需要获取点赞状态的实体id
     * @return 已点赞返回1，未点赞返回0
     */
    int getLikeStatus(String openid, Integer entityType, Integer entityId);

    /**
     * 获取点赞数
     *
     * @param entityType 实体类型
     * @param entityId   实体id
     * @return 点赞数
     */
    Long getCount(Integer entityType, Integer entityId);
}
