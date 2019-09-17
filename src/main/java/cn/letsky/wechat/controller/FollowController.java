package cn.letsky.wechat.controller;

import cn.letsky.wechat.form.FollowForm;
import cn.letsky.wechat.model.User;
import cn.letsky.wechat.service.FollowService;
import cn.letsky.wechat.service.UserService;
import cn.letsky.wechat.util.ResultUtils;
import cn.letsky.wechat.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/following")
public class FollowController {

    private final FollowService followService;
    private final UserService userService;

    public FollowController(FollowService followService, UserService userService) {
        this.followService = followService;
        this.userService = userService;
    }

    /**
     * 关注用户
     *
     * @param followForm
     * @return
     */
    @PostMapping
    public ResponseEntity<Long> follow(@RequestBody @Valid FollowForm followForm, BindingResult bindingResult) {
        followService.follow(followForm.getFromUser(), followForm.getToUser());
        return ResultUtils.ok();
    }

    /**
     * 取消关注
     *
     * @param followForm
     * @return
     */
    @DeleteMapping
    public ResponseEntity<Long> unFollow(@RequestBody @Valid FollowForm followForm, BindingResult bindingResult) {
        followService.unFollow(followForm.getFromUser(), followForm.getToUser());
        return ResultUtils.ok();
    }

//    /**
//     * 获取给定用户的关注用户
//     *
//     * @param openid 给定用户的openid
//     * @return
//     */
//    @GetMapping("/{openid}/following")
//    public ResultVO<List<UserVO>> getFollows(
//            @PathVariable("openid") String openid) {
//        Set<String> followIds = followService.getFollows(openid);
//        List<UserVO> voList = getUsers(followIds);
//        Map<String, Long> map = new HashMap<>();
//        map.put("count", followService.getFollowCount(openid));
//
//        return ResultUtils.success(voList, map);
//    }

//    /**
//     * 获取粉丝
//     *
//     * @param openid
//     * @return
//     */
//    @GetMapping("/{openid}/followers")
//    public ResultVO<List<UserVO>> getFans(@PathVariable("openid") String openid) {
//        Set<String> fansIds = followService.getFans(openid);
//        List<UserVO> voList = getUsers(fansIds);
//        Map<String, Long> map = new HashMap<>();
//        map.put("count", followService.getFansCount(openid));
//
//        return ResultUtils.success(voList, map);
//    }
//
//    @GetMapping("/getFollowInfo/{openid}")
//    public ResponseEntity<Map<String, Long>> getFollowAndFans(@PathVariable String openid) {
//        Long followCount = followService.getFollowCount(openid);
//        Long fansCount = followService.getFansCount(openid);
//        Map<String, Long> map = new HashMap<>();
//        map.put("followCount", followCount);
//        map.put("fansCount", fansCount);
//        return ResultUtils.ok(map);
//    }



    /**
     * 将给定的id集合转换成UserVO对象
     *
     * @param ids 用户id集合
     * @return
     */
    private List<UserVO> getUsers(Set<String> ids) {
        List<UserVO> userVOList = ids.stream().map(id -> {
            User user = userService.getUser(id).orElseThrow(EntityNotFoundException::new);
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            return userVO;
        }).collect(Collectors.toList());
        return userVOList;
    }
}
