package cn.letsky.wechat.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.WxMaUserService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.letsky.wechat.constant.ResultEnum;
import cn.letsky.wechat.exception.CommonException;
import cn.letsky.wechat.form.WxUserForm;
import cn.letsky.wechat.model.User;
import cn.letsky.wechat.model.UserHolder;
import cn.letsky.wechat.service.TokenService;
import cn.letsky.wechat.service.UserService;
import cn.letsky.wechat.util.ResultUtils;
import cn.letsky.wechat.vo.ResultVO;
import cn.letsky.wechat.vo.UserVO;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/wx")
public class WxController {

    private final WxMaService wxMaService;
    private final UserService userService;
    private final TokenService tokenService;
    private final UserHolder userHolder;

    public WxController(WxMaService wxMaService, UserService userService,
                        TokenService tokenService, UserHolder userHolder) {
        this.wxMaService = wxMaService;
        this.userService = userService;
        this.tokenService = tokenService;
        this.userHolder = userHolder;
    }

    @GetMapping("/login")
    public ResultVO<Map<String, String>> login(@RequestParam("code") String code) {
        try {
            if (StringUtils.isEmpty(code)) {
                throw new CommonException(ResultEnum.WECHAT_CODE_EMPTY);
            }
            WxMaUserService wxMaUserService = wxMaService.getUserService();
            WxMaJscode2SessionResult result = wxMaUserService.getSessionInfo(code);
            Map<String, String> map = new HashMap<>();
            map.put("openid", result.getOpenid());
            return ResultUtils.success(map);
        } catch (WxErrorException e) {
            throw new CommonException(ResultEnum.WECHAT_LOGIN_ERROR);
        }
    }

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
        Map<String, String> map = new HashMap<>();
        String wxSession = tokenService.create();
        tokenService.save(wxSession, user.getOpenid());
        map.put("wxSession", wxSession);
        return ResultUtils.success(map);
    }

    @GetMapping("/isexpire")
    public ResultVO isExpire(@RequestParam("sessionId") String sessionId) {
        String key = "wx:session:" + sessionId;
        if (tokenService.isExpire(key)) {
            return ResultUtils.success();
        }
        userHolder.clear();
        return ResultUtils.error(ResultEnum.SESSION_EXPIRED);
    }

    @GetMapping("/users/{openid}")
    public ResultVO getUserInfo(@PathVariable String openid) {
        //TODO
        User user = userService.getUser(openid)
                .orElseThrow(NoSuchElementException::new);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return ResultUtils.success(userVO);
    }

}
