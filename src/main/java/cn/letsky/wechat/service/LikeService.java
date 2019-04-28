package cn.letsky.wechat.service;

public interface LikeService {

    /**
     * 点赞
     * @param openid
     * @param entityType
     * @param entityId
     * @return
     */
    Long like(String openid, Integer entityType, Integer entityId);

    /**
     * 取消点赞
     * @param openid
     * @param entityType
     * @param entityId
     * @return
     */
    Long cancelLike(String openid, Integer entityType, Integer entityId);

    /**
     * 获取用户点赞状态
     * @param openid
     * @param entityType
     * @param entityId
     * @return 点赞返回1，未点赞返回0
     */
    int getLikeStatus(String openid, Integer entityType, Integer entityId);

    /**
     * 获取点赞数
     * @param entityType
     * @param entityId
     * @return 点赞数
     */
    Long likeCount(Integer entityType, Integer entityId);
}
