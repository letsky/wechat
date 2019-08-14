package cn.letsky.wechat.service.Impl;

import cn.letsky.wechat.constant.status.LikeStatus;
import cn.letsky.wechat.service.LikeService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * {@link LikeService}实现类
 * 使用redis实现点赞，使用set
 * 相关命令：sismember, scard, sadd, srem
 * key：like:<entityType>:<entityId> value：userId
 */
@Service
public class LikeServiceImpl implements LikeService {

    private static final String SPLIT = ":";
    private static final String LIKE = "like";

    private final RedisTemplate<String, String> redisTemplate;

    public LikeServiceImpl(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Long like(String openid, Integer entityType, Integer entityId) {
        String likeKey = getKey(entityType, entityId);
        if (!redisTemplate.opsForSet().isMember(likeKey, openid)){
            redisTemplate.opsForSet().add(likeKey, openid);
        }
        return redisTemplate.opsForSet().size(likeKey);
    }

    @Override
    public Long cancelLike(String openid, Integer entityType, Integer entityId) {
        String likeKey = getKey(entityType, entityId);
        if (redisTemplate.opsForSet().isMember(likeKey, openid)) {
            redisTemplate.opsForSet().remove(likeKey, openid);
        }
        return redisTemplate.opsForSet().size(likeKey);
    }

    @Override
    public int getLikeStatus(String openid, Integer entityType, Integer entityId) {
        String likeKey = getKey(entityType, entityId);
        if (redisTemplate.opsForSet().isMember(likeKey, openid)) {
            return LikeStatus.LIKE;
        }
        return LikeStatus.UNLIKE;
    }

    @Override
    public Long getCount(Integer entityType, Integer entityId) {
        String likeKey = getKey(entityType, entityId);
        return redisTemplate.opsForSet().size(likeKey);
    }

    /**
     * 获取点赞的key
     * @param entityType
     * @param entityId
     * @return like:<entityType>:<entityId>
     */
    private String getKey(Integer entityType, Integer entityId) {
        StringBuilder sb = new StringBuilder();
        sb.append(LIKE).append(SPLIT).append(entityType).append(SPLIT).append(entityId);
        return sb.toString();
    }
}
