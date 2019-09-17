package cn.letsky.wechat.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.WxMaUserService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.letsky.wechat.constant.ResultEnum;
import cn.letsky.wechat.exception.CommonException;
import cn.letsky.wechat.form.WxUserForm;
import cn.letsky.wechat.model.User;
import cn.letsky.wechat.service.UserService;
import cn.letsky.wechat.util.ResultUtils;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/wx")
public class WxController {

    private final WxMaService wxMaService;
    private final UserService userService;

    public WxController(WxMaService wxMaService, UserService userService) {
        this.wxMaService = wxMaService;
        this.userService = userService;
    }

    @GetMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestParam("code") String code) {
        if (StringUtils.isEmpty(code)) {
            throw new CommonException(ResultEnum.WECHAT_CODE_EMPTY);
        }
        try {
            WxMaUserService wxMaUserService = wxMaService.getUserService();
            WxMaJscode2SessionResult result = wxMaUserService.getSessionInfo(code);
            Map<String, String> map = new HashMap<>();
            map.put("openid", result.getOpenid());
            return ResultUtils.ok(map);
        } catch (WxErrorException e) {
            throw new CommonException(ResultEnum.WECHAT_LOGIN_ERROR);
        }
    }

    @PostMapping("/register")
    public ResponseEntity register(
            @Valid WxUserForm userForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            //TODO 可能空指针
            String errMsg = bindingResult.getFieldError().getDefaultMessage();
            throw new CommonException(errMsg);
        }
        User user = new User();
        BeanUtils.copyProperties(userForm, user);
        userService.save(user);
        return ResultUtils.ok();
    }
}
