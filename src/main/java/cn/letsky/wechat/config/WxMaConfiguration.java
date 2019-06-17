package cn.letsky.wechat.config;

import cn.letsky.wechat.properties.WxMaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.WxMaInMemoryConfig;

@Configuration
@EnableConfigurationProperties(WxMaProperties.class)
public class WxMaConfiguration {

    private final WxMaProperties properties;

    public WxMaConfiguration(WxMaProperties properties) {
        this.properties = properties;
    }


    @Bean
    public WxMaService wxMaService() {
        WxMaService wxMaService = new WxMaServiceImpl();
        WxMaInMemoryConfig wxMaInMemoryConfig = new WxMaInMemoryConfig();
        wxMaInMemoryConfig.setAppid(properties.getAppid());
        wxMaInMemoryConfig.setSecret(properties.getSecret());
        wxMaService.setWxMaConfig(wxMaInMemoryConfig);
        return wxMaService;
    }
}
