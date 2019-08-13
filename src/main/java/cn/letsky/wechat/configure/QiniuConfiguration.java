package cn.letsky.wechat.configure;

import cn.letsky.wechat.configure.properties.QiNiuProperties;
import com.qiniu.common.Zone;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 七牛云对象存储自动配置
 */
@Configuration
@EnableConfigurationProperties(QiNiuProperties.class)
public class QiniuConfiguration {

    private final QiNiuProperties properties;

    public QiniuConfiguration(QiNiuProperties properties) {
        this.properties = properties;
    }

    @Bean
    public UploadManager uploadManager() {
        com.qiniu.storage.Configuration config =
                new com.qiniu.storage.Configuration(Zone.zone0());
        return new UploadManager(config);
    }

    @Bean("getToken")
    public String getToken() {
        Auth auth = Auth.create(properties.getAccessKey(),
                properties.getSecretKey());
        return auth.uploadToken(properties.getBucket());
    }

}
