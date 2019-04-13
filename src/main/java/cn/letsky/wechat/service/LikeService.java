package cn.letsky.wechat.service;

public interface LikeService {

    /**
     * 点赞
     * @param openid
     * @param entityType
     * @param entityId
     * @return
     */
    Integer like(String openid, Integer entityType, Integer entityId);

    /**
     * 取消点赞
     * @param openid
     * @param entityType
     * @param entityId
     * @return
     */
    Integer dislike(String openid, Integer entityType, Integer entityId);

    /**
     * 获取用户点赞状态
     * @param openid
     * @param entityType
     * @param entityId
     * @return
     */
    Integer getLikeStatus(String openid, Integer entityType, Integer entityId);
}
