package cn.letsky.wechat.controller.admin;

import cn.letsky.wechat.model.User;
import cn.letsky.wechat.service.UserService;
import cn.letsky.wechat.util.ResultUtils;
import cn.letsky.wechat.vo.ResultVO;
import cn.letsky.wechat.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/users")
@CrossOrigin(allowCredentials = "true")
public class UserAdminController {

    private final UserService userService;

    public UserAdminController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping
    public ResultVO getList(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size) {
        Page<User> userPage = userService.getUsers(page, size);
        List<UserVO> list = userPage.get()
                .map(e -> transform(e, new UserVO()))
                .collect(Collectors.toList());
        Long count = userPage.getTotalElements();
        Map<String, Long> map = new HashMap<>();
        map.put("count", count);
        return ResultUtils.success(list, map);
    }

    @GetMapping("/{openid}")
    public ResponseEntity<UserVO> get(@PathVariable("openid") String openid) {
        User user = userService.getUser(openid)
                .orElseThrow(EntityNotFoundException::new);
        UserVO userVO = new UserVO();
        transform(user, userVO);
        return ResultUtils.ok(userVO);
    }

    private UserVO transform(User user, UserVO userVO) {
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }
}
