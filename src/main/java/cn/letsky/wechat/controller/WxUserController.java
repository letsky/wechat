package cn.letsky.wechat.controller;

import java.time.Duration;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.letsky.wechat.converter.Form2Model;
import cn.letsky.wechat.exception.OperationException;
import cn.letsky.wechat.form.WxUserForm;
import cn.letsky.wechat.model.User;
import cn.letsky.wechat.service.UserService;
import cn.letsky.wechat.util.ResultUtils;
import cn.letsky.wechat.util.SessionUtils;
import cn.letsky.wechat.viewobject.ResultVO;
import me.chanjar.weixin.common.error.WxErrorException;

@RequestMapping("/wx")
@RestController
public class WxUserController {

    @Autowired
    private WxMaService wxMaService;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate<String, String> redisClient;

    /**
     * 获取登录态的session
     *
     * @param code wx.login换取的code
     * @return 自定义的session
     */
    @GetMapping("/login")
    public ResultVO<String> login(@RequestParam("code") String code) {
        if (code != null) {
            try {
                WxMaJscode2SessionResult session = wxMaService.getUserService().getSessionInfo(code);
                String wxSession = SessionUtils.create();
                redisClient.opsForValue().set("wx:session:" + wxSession, session.getOpenid(), Duration.ofDays(7));

                return ResultUtils.success(wxSession);
            } catch (WxErrorException e) {
                throw new OperationException(1, "微信异常");
            }
        }
        return null;
    }

    /**
     * 微信端用户注册
     *
     * @param userForm      微信端传来的表单
     * @param bindingResult 表单验证的结果
     * @return 相应的Json结果
     */
    @PostMapping("/register")
    public ResultVO register(@Valid WxUserForm userForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errMsg = bindingResult.getFieldError().getDefaultMessage();
            return ResultUtils.error(0, errMsg);
        }
        userService.saveUser(Form2Model.convert(userForm, User.class));
        return ResultUtils.success();
    }

    /**
     * 判断用户session是否过期
     *
     * @param sessionId 服务端派发的session
     * @return 返回是否成功的状态码和消息
     */
    @GetMapping("/isexpire")
    public ResultVO isExpire(@RequestParam("sessionId") String sessionId) {
        String key = "wx:session:" + sessionId;
        if (redisClient.getExpire(key) > 0) {
            return ResultUtils.success();
        }
        return ResultUtils.error(1, "过期了");
    }
}
