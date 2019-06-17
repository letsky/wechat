package cn.letsky.wechat.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.letsky.wechat.constant.ResultEnum;
import cn.letsky.wechat.exception.CommonException;
import cn.letsky.wechat.form.WxUserForm;
import cn.letsky.wechat.model.User;
import cn.letsky.wechat.service.TokenService;
import cn.letsky.wechat.service.UserService;
import cn.letsky.wechat.util.ResultUtils;
import cn.letsky.wechat.viewobject.ResultVO;
import cn.letsky.wechat.viewobject.UserVO;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/wx")
@RestController
public class WxUserController {

    private final WxMaService wxMaService;

    private final UserService userService;

    private final TokenService tokenService;

    public WxUserController(WxMaService wxMaService,
                            UserService userService,
                            TokenService tokenService) {
        this.wxMaService = wxMaService;
        this.userService = userService;
        this.tokenService = tokenService;
    }

    /**
     * 获取登录态的session
     *
     * @param code wx.login换取的code
     * @return 自定义的session
     */
    @GetMapping("/login")
    public ResultVO<Map<String, String>> login(@RequestParam("code") String code) {
        try {
            if (StringUtils.isEmpty(code)){
                throw new CommonException(ResultEnum.WECHAT_CODE_EMPTY);
            }
            System.out.println(code);
            WxMaJscode2SessionResult session = wxMaService.getUserService().getSessionInfo(code);
            Map<String, String> map = new HashMap<>();

            String wxSession = tokenService.create();
            map.put("wxSession", wxSession);
            map.put("openid", session.getOpenid());
            //传入redis
            tokenService.save(wxSession, session.getOpenid());
            return ResultUtils.success(map);
        } catch (WxErrorException e) {
            throw new CommonException(ResultEnum.WECHAT_LOGIN_ERROR);
        }
    }

    /**
     * 微信端用户注册
     *
     * @param userForm      微信端传来的表单
     * @param bindingResult 表单验证的结果
     * @return 相应的Json结果
     */
    @PostMapping("/register")
    public ResultVO register(@Valid WxUserForm userForm,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String errMsg = bindingResult.getFieldError().getDefaultMessage();
            throw new CommonException(errMsg);
        }
        User user = new User();
        BeanUtils.copyProperties(userForm, user);
        userService.save(user);
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
        if (tokenService.isExpire(key)) {
            return ResultUtils.success();
        }
        return ResultUtils.error(ResultEnum.SESSION_EXPIRED);
    }

    @GetMapping("/getuserinfo")
    public ResultVO getUserInfo(@RequestParam("openid") String openid){
        User user = userService.findById(openid);
        if (user == null){
            throw new CommonException(ResultEnum.NOT_REGISTER);
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return ResultUtils.success(userVO);
    }

}
