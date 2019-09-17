package cn.letsky.wechat.controller;

import cn.letsky.wechat.model.User;
import cn.letsky.wechat.service.UserService;
import cn.letsky.wechat.util.ResultUtils;
import cn.letsky.wechat.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{openid}")
    public ResponseEntity<UserVO> getUserInfo(@PathVariable String openid) {
        //TODO
        User user = userService.getUser(openid)
                .orElseThrow(EntityNotFoundException::new);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return ResultUtils.ok(userVO);
    }
}
