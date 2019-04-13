package cn.letsky.wechat.service.Impl;

import cn.letsky.wechat.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


@Service
public class LikeServiceImpl implements LikeService {

    private static final String SPLIT = ":";
    private static final String LIKE = "like";
    private static final String DISLIKE = "dislike";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public Integer like(String openid, Integer entityType, Integer entityId) {
        return null;
    }

    @Override
    public Integer dislike(String openid, Integer entityType, Integer entityId) {
        return null;
    }

    @Override
    public Integer getLikeStatus(String openid, Integer entityType, Integer entityId) {
        return null;
    }

    private static String getLikeKey(Integer entityType, Integer entityId){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(LIKE).append(SPLIT).append(entityType).append(entityId);
        return stringBuilder.toString();
    }

    private static String getDislikeKey(Integer entityType, Integer entityId){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(DISLIKE).append(SPLIT).append(entityType).append(entityId);
        return stringBuilder.toString();
    }
}
