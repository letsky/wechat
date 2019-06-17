package cn.letsky.wechat.config;

import com.qiniu.common.Zone;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(QiNiuProperties.class)
public class QiniuUploadConfiguration {

    private final QiNiuProperties qiNiuProperties;

    public QiniuUploadConfiguration(QiNiuProperties qiNiuProperties) {
        this.qiNiuProperties = qiNiuProperties;
    }

    @Bean
    public com.qiniu.storage.Configuration config() {
        return new com.qiniu.storage.Configuration(Zone.zone0());
    }

    @Bean
    public UploadManager uploadManager() {
        return new UploadManager(config());
    }

    @Bean
    public Auth auth() {
        return Auth.create(qiNiuProperties.getAccessKey(),
                qiNiuProperties.getSecretKey());
    }

}
