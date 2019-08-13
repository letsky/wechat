package cn.letsky.wechat.configure.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 七牛对象存储相关配置
 */
@Data
@ConfigurationProperties(prefix = "wechat.qiniu")
public class QiNiuProperties {

    /**
     * 七牛云access key
     */
    private String accessKey;

    /**
     * 七牛云secret key
     */
    private String secretKey;

    /**
     * 七牛云bucket
     */
    private String bucket;

    /**
     * 七牛云domain
     */
    private String domain;
}
