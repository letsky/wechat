package cn.letsky.wechat.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 小程序相关配置
 */
@Data
@ConfigurationProperties(prefix = "wx.miniapp.config")
public class WxMaProperties {

    /**
     * 设置微信小程序的appid
     */
    private String appid;

    /**
     * 设置微信小程序的secret
     */
    private String secret;
}
