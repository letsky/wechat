package cn.letsky.wechat.interceptor;

import cn.letsky.wechat.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 文章拦截器
 */
@Component
public class ArticleInterceptor implements HandlerInterceptor {

    private final UserService userService;

    public ArticleInterceptor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String openid = request.getHeader("openid");

        if (!StringUtils.isEmpty(openid)) {
            String servletPath = request.getServletPath();
            request.getRequestDispatcher(servletPath + "?openid=" + openid);
            return true;
        }
        return true;

//        String session = request.getHeader("wxSession");
//        //session未过期
//        if (StringUtils.isEmpty(session)){
//            return true;
//        }
//        if (!tokenService.isExpire(session)) {
//            String openid = tokenService.getOpenid(session);
//            User user = userService.getUser(openid)
//                    .orElseThrow(NoSuchElementException::new);
//            return true;
//        }
//        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
    }
}
