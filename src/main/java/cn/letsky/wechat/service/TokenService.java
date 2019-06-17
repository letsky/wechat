package cn.letsky.wechat.service;

public interface TokenService {

    /**
     * 创建session
     * @return
     */
    String create();

    /**
     * 判断session是否过期
     * @param session
     * @return 过期返回true，未过期返回false
     */
    boolean isExpire(String session);

    /**
     * 保存session
     * @param session
     */
    void save(String session, String openid);

    /**
     * 获取openid
     * @param session
     * @return
     */
    String getOpenid(String session);
}
