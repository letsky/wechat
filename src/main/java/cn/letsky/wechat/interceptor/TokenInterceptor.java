package cn.letsky.wechat.interceptor;

import cn.letsky.wechat.constant.ResultEnum;
import cn.letsky.wechat.exception.CommonException;
import cn.letsky.wechat.model.UserHolder;
import cn.letsky.wechat.model.User;
import cn.letsky.wechat.service.TokenService;
import cn.letsky.wechat.service.UserService;
import cn.letsky.wechat.util.ResultUtils;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    private final TokenService tokenService;
    private final UserService userService;
    private final UserHolder userHolder;

    public TokenInterceptor(TokenService tokenService,
                            UserService userService,
                            UserHolder userHolder) {
        this.tokenService = tokenService;
        this.userService = userService;
        this.userHolder = userHolder;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String session = request.getHeader("wxSession");
        //session未过期
        if (!tokenService.isExpire(session)) {
            String openid = tokenService.getOpenid(session);
            User user = userService.findById(openid);
            userHolder.set(user);
            return true;
        }
        //返回错误的信息
        ObjectMapper mapper = new ObjectMapper();
        String msg = mapper.writeValueAsString(ResultUtils.error(ResultEnum.SESSION_ERROR));
        response.setContentType("application/json; charset=utf-8");
        PrintWriter pw = response.getWriter();
        pw.write(msg);
        pw.flush();
        pw.close();
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        userHolder.clear();
    }
}
