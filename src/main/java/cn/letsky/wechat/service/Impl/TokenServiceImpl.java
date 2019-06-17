package cn.letsky.wechat.service.Impl;

import cn.letsky.wechat.service.TokenService;
import cn.letsky.wechat.util.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

@Service
public class TokenServiceImpl implements TokenService {

    private final RedisTemplate<String,String> redisTemplate;

    public TokenServiceImpl(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public String create() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    @Override
    public boolean isExpire(String session) {
        String key = "wx:session:" + session;
        if (redisTemplate.getExpire(key) <= 0) {
            return true;
        }
        return false;
    }

    @Override
    public void save(String session, String openid) {
        redisTemplate.opsForValue().set("wx:session:" + session, openid, Duration.ofDays(7));
    }

    @Override
    public String getOpenid(String session) {
        String key = "wx:session:" + session;
        String openid = redisTemplate.opsForValue().get(key);
        return openid;
    }
}
