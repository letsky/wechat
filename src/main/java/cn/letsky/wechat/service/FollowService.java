package cn.letsky.wechat.service;

import java.util.Set;

public interface FollowService {

    /**
     * 关注
     *
     * @param openid 关注人openid
     * @param follow 被关注人openid
     * @return
     */
    Long follow(String openid, String follow);

    /**
     * 取消关注
     *
     * @param openid   取消关注人openid
     * @param unFollow 被取消关注人openid
     * @return
     */
    Long unFollow(String openid, String unFollow);

    /**
     * 获取给定人的关注数
     *
     * @param openid
     * @return
     */
    Set<String> getFollows(String openid);

    /**
     * 获取给定人的粉丝数
     *
     * @param openid
     * @return
     */
    Set<String> getFans(String openid);

    /**
     * 获取关注用户人数
     *
     * @param openid
     * @return
     */
    Long getFollowCount(String openid);

    /**
     * 获取粉丝人数
     *
     * @param openid
     * @return
     */
    Long getFansCount(String openid);

    /**
     * 判断是否关注
     *
     * @param openid
     * @param anotherOpenid
     * @return
     */
    Integer isFollowed(String openid, String anotherOpenid);
}
