package cn.letsky.wechat.configure.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 小程序相关配置
 */
@Data
@ConfigurationProperties(prefix = "wechat.miniapp")
public class WxMaProperties {

    /**
     * 设置微信小程序的appId
     */
    private String appId;

    /**
     * 设置微信小程序的secret
     */
    private String secret;
}
