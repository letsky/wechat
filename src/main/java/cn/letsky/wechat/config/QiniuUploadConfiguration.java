package cn.letsky.wechat.config;

import com.google.gson.Gson;
import com.qiniu.common.Zone;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "qiniu", name = "enabled", matchIfMissing = true)
@EnableConfigurationProperties(QiNiuProperties.class)
public class QiniuUploadConfiguration {

    private final QiNiuProperties qiNiuProperties;

    public QiniuUploadConfiguration(QiNiuProperties qiNiuProperties) {
        this.qiNiuProperties = qiNiuProperties;
    }

    @Bean
    public com.qiniu.storage.Configuration config(){
        return new com.qiniu.storage.Configuration(Zone.zone0());
    }

    @Bean
    public UploadManager uploadManager(){
        return new UploadManager(config());
    }

    @Bean
    public Auth auth(){
        return Auth.create(qiNiuProperties.getAccessKey(),
                qiNiuProperties.getSecretKey());
    }

}
