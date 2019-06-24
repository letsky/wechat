package cn.letsky.wechat.interceptor;

import cn.letsky.wechat.model.User;
import cn.letsky.wechat.model.UserHolder;
import cn.letsky.wechat.service.TokenService;
import cn.letsky.wechat.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ArticleInterceptor implements HandlerInterceptor {

    private final TokenService tokenService;
    private final UserService userService;
    private final UserHolder userHolder;

    public ArticleInterceptor(TokenService tokenService,
                              UserService userService,
                              UserHolder userHolder) {
        this.tokenService = tokenService;
        this.userService = userService;
        this.userHolder = userHolder;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String session = request.getHeader("wxSession");
        //session未过期
        if (StringUtils.isEmpty(session)){
            return true;
        }
        if (!tokenService.isExpire(session)) {
            String openid = tokenService.getOpenid(session);
            User user = userService.findById(openid);
            userHolder.set(user);
            return true;
        }
        return false;
    }
}
