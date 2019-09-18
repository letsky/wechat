package cn.letsky.wechat.service;

import java.util.Set;

public interface FollowService {

    /**
     * 关注
     *
     * @param fromUser 关注人openid
     * @param toUser 被关注人openid
     * @return
     */
    void follow(String fromUser, String toUser);

    /**
     * 取消关注
     *
     * @param fromUser   取消关注人openid
     * @param toUser 被取消关注人openid
     * @return
     */
    void unFollow(String fromUser, String toUser);

    /**
     * 获取已关注的用户
     *
     * @param openid
     * @return
     */
    Set<String> getFollowing(String openid);

    /**
     * 获取粉丝
     *
     * @param openid
     * @return
     */
    Set<String> getFollowers(String openid);

    /**
     * 获取关注用户人数
     *
     * @param openid
     * @return
     */
    long getFollowingCount(String openid);

    /**
     * 获取粉丝人数
     *
     * @param openid
     * @return
     */
    long getFollowersCount(String openid);

    /**
     * 判断是否关注
     *
     * @param fromUser
     * @param toUser
     * @return
     */
    boolean isFollowing(String fromUser, String toUser);

    /**
     * 是否互相关注
     *
     * @param fromUser
     * @param toUser
     * @return
     */
    boolean isMutualFollowing(String fromUser, String toUser);

    /**
     * 共同关注
     *
     * @param user  用户
     * @param other 用户
     * @return
     */
    Set<String> commonFollowing(String user, String other);
}
