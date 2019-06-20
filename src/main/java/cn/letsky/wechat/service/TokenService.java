package cn.letsky.wechat.service;

public interface TokenService {

    /**
     * 创建session
     *
     * @return 微信登录的session
     */
    String create();

    /**
     * 判断session是否过期
     *
     * @param session 微信登录获取的session
     * @return 过期返回true，未过期返回false
     */
    boolean isExpire(String session);

    /**
     * 保存session至redis
     *
     * @param session 微信登录获取的session
     * @param openid  微信用户id
     */
    void save(String session, String openid);

    /**
     * 从session中获取openid
     *
     * @param session 微信登录获取的session
     * @return 用户的openid
     */
    String getOpenid(String session);
}
