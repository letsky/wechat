package cn.letsky.wechat.service.Impl;

import cn.letsky.wechat.exception.CommonException;
import cn.letsky.wechat.service.FollowService;
import cn.letsky.wechat.service.UserService;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class FollowServiceImpl implements FollowService {

    private static final String SPLIT = ":";
    //正在关注的
    private static final String FOLLOWING = "following";
    //关注我的
    private static final String FOLLOWERS = "followers";

    private final SetOperations<String, String> setOperations;
    private final UserService userService;

    public FollowServiceImpl(StringRedisTemplate redisTemplate, UserService userService) {
        this.setOperations = redisTemplate.opsForSet();
        this.userService = userService;
    }

    @Override
    public void follow(String fromUser, String toUser) {
        if (fromUser.equals(toUser)) {
            throw new CommonException("不能关注自己哦！");
        }
        boolean r1 = userService.checkUser(fromUser);
        boolean r2 = userService.checkUser(toUser);
        if (!(r1 && r2)) {
            throw new CommonException("用户不存在");
        }
        String followKey = getFollowingKey(fromUser);
        String fansKey = getFollowersKey(toUser);
        setOperations.add(followKey, toUser);
        setOperations.add(fansKey, fromUser);
    }

    @Override
    public void unFollow(String fromUser, String toUser) {
        String followKey = getFollowingKey(fromUser);
        String fansKey = getFollowersKey(toUser);
        setOperations.remove(followKey, toUser);
        setOperations.remove(fansKey, fromUser);
    }

    @Override
    public Set<String> getFollowing(String openid) {
        return setOperations.members(getFollowingKey(openid));
    }

    @Override
    public Set<String> getFollowers(String openid) {
        return setOperations.members(getFollowersKey(openid));
    }

    @Override
    public Long getFollowingCount(String openid) {
        return setOperations.size(getFollowingKey(openid));
    }

    @Override
    public Long getFollowersCount(String openid) {
        return setOperations.size(getFollowersKey(openid));
    }

    @Override
    public boolean isFollowing(String fromUser, String toUser) {
        String key = getFollowingKey(fromUser);
        return setOperations.isMember(key, toUser);
    }

    @Override
    public boolean isMutualFollowing(String fromUser, String toUser) {
        String followingKey = getFollowingKey(fromUser);
        String followersKey = getFollowersKey(fromUser);
        Boolean r1 = setOperations.isMember(followingKey, toUser);
        Boolean r2 = setOperations.isMember(followersKey, toUser);
        if (r1 && r2) {
            return true;
        }
        return false;
    }

    /**
     * 获取存储在redis上关注的key
     * @param openid
     * @return
     */
    private String getFollowingKey(String openid) {
        StringBuffer sb = new StringBuffer();
        sb.append(openid)
                .append(SPLIT)
                .append(FOLLOWING);
        return sb.toString();
    }

    /**
     * 获取粉丝的key
     * @param openid
     * @return
     */
    private String getFollowersKey(String openid) {
        StringBuffer sb = new StringBuffer();
        sb.append(openid)
                .append(SPLIT)
                .append(FOLLOWERS);
        return sb.toString();
    }
}
