package cn.letsky.wechat.controller.admin;

import cn.letsky.wechat.constant.ResultEnum;
import cn.letsky.wechat.exception.CommonException;
import cn.letsky.wechat.model.User;
import cn.letsky.wechat.service.UserService;
import cn.letsky.wechat.util.ResultUtils;
import cn.letsky.wechat.viewobject.ResultVO;
import cn.letsky.wechat.viewobject.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/users")
@CrossOrigin(allowCredentials = "true")
public class UserAdminController {

    @Autowired
    private UserService userService;

    @RequestMapping
    public ResultVO getList(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size) {
        Page<User> userPage = userService.findAll(page, size);
        List<UserVO> list = userPage.get()
                .map(e -> transform(e, new UserVO()))
                .collect(Collectors.toList());
        Long count = userPage.getTotalElements();
        Map<String, Long> map = new HashMap<>();
        map.put("count", count);
        return ResultUtils.success(list, map);
    }

    @GetMapping("/{openid}")
    public ResultVO get(@PathVariable("openid") String openid){
        User user = userService.findById(openid);
        UserVO userVO = new UserVO();
        transform(user, userVO);
        return ResultUtils.success(userVO);
    }

    private UserVO transform(User user, UserVO userVO) {
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }
}
