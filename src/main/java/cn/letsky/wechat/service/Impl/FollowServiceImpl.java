package cn.letsky.wechat.service.Impl;

import cn.letsky.wechat.constant.status.UserStatus;
import cn.letsky.wechat.service.FollowService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class FollowServiceImpl implements FollowService {

    private static final String SPLIT = ":";
    private static final String FOLLOW = "follow";
    private static final String FANS = "fans";

    private final RedisTemplate<String, String> redisTemplate;

    public FollowServiceImpl(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Long follow(String openid, String follow) {
        String followKey = getFollowKey(openid);
        String fansKey = getFansKey(follow);
        Long followCount = redisTemplate.opsForSet().add(followKey, follow);
        redisTemplate.opsForSet().add(fansKey, openid);
        return followCount;
    }

    @Override
    public Long unfollow(String openid, String unFollow) {
        String followKey = getFollowKey(openid);
        String fansKey = getFansKey(unFollow);
        Long followCount = redisTemplate.opsForSet().remove(followKey, unFollow);
        redisTemplate.opsForSet().remove(fansKey, openid);
        return followCount;
    }

    @Override
    public Set<String> getFollows(String openid) {
        return redisTemplate.opsForSet().members(getFollowKey(openid));
    }

    @Override
    public Set<String> getFans(String openid) {
        return redisTemplate.opsForSet().members(getFansKey(openid));
    }

    @Override
    public Long getFollowCount(String openid) {
        return redisTemplate.opsForSet().size(getFollowKey(openid));
    }

    @Override
    public Long getFansCount(String openid) {
        return redisTemplate.opsForSet().size(getFansKey(openid));
    }

    @Override
    public Integer isFollowed(String openid, String anotherOpenid) {
        String key = getFollowKey(openid);
        boolean result = redisTemplate.opsForSet().isMember(key, anotherOpenid);
        if (result){
            return UserStatus.FOLLOW;
        }
        return UserStatus.UNFOLLOW;
    }

    /**
     * 获取存储在redis上关注的key
     * 格式：follow:<openid>:<followId>
     * @param openid
     * @return
     */
    private String getFollowKey(String openid){
        StringBuffer sb = new StringBuffer();
        sb.append(FOLLOW)
                .append(SPLIT)
                .append(openid);
        return sb.toString();
    }

    /**
     * 获取粉丝的key
     * @param openid
     * @return
     */
    private String getFansKey(String openid){
        StringBuffer sb = new StringBuffer();
        sb.append(FANS)
                .append(SPLIT)
                .append(openid);
        return sb.toString();
    }
}
