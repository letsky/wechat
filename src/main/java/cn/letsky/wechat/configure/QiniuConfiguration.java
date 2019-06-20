package cn.letsky.wechat.configure;

import cn.letsky.wechat.properties.QiNiuProperties;
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

    private final QiNiuProperties qiNiuProperties;

    public QiniuConfiguration(QiNiuProperties qiNiuProperties) {
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
