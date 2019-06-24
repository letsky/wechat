package cn.letsky.wechat.configure;

import cn.letsky.wechat.interceptor.ArticleInterceptor;
import cn.letsky.wechat.interceptor.TokenInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web相关配置
 */
@Component
public class WechatWebConfiguration implements WebMvcConfigurer {

    private final TokenInterceptor tokenInterceptor;
    private final ArticleInterceptor articleInterceptor;

    public WechatWebConfiguration(TokenInterceptor tokenInterceptor,
                                  ArticleInterceptor articleInterceptor) {
        this.tokenInterceptor = tokenInterceptor;
        this.articleInterceptor = articleInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/wx/login", "/wx/register", "/wx/isexpire")
                .excludePathPatterns("/articles")
                .excludePathPatterns("/admin/**");

        registry.addInterceptor(articleInterceptor)
                .addPathPatterns("/articles");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }

}
