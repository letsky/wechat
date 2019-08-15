package cn.letsky.wechat.configure;

import cn.letsky.wechat.interceptor.ArticleInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web相关配置
 */
@Component
public class WechatWebConfiguration implements WebMvcConfigurer {

    private final ArticleInterceptor articleInterceptor;

    public WechatWebConfiguration(ArticleInterceptor articleInterceptor) {
        this.articleInterceptor = articleInterceptor;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }

}
