package cn.letsky.wechat.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "qiniu")
public class QiNiuProperties {

    private boolean enabled = true;

    private String accessKey;

    private String secretKey;

    private String bucket;

    private String domain;
}
