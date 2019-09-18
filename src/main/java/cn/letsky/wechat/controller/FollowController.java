package cn.letsky.wechat.controller;

import cn.letsky.wechat.domain.form.FollowForm;
import cn.letsky.wechat.domain.model.User;
import cn.letsky.wechat.domain.vo.UserVO;
import cn.letsky.wechat.service.FollowService;
import cn.letsky.wechat.service.UserService;
import cn.letsky.wechat.util.ResultUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
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
    @PutMapping(value = "/following")
    public ResponseEntity follow(@RequestBody @Valid FollowForm followForm, BindingResult bindingResult) {
        followService.follow(followForm.getFromUser(), followForm.getToUser());
        return ResultUtils.ok();
    }

    /**
     * 取消关注
     *
     * @param followForm
     * @return
     */
    @DeleteMapping(value = "/following")
    public ResponseEntity unFollow(@RequestBody @Valid FollowForm followForm, BindingResult bindingResult) {
        followService.unFollow(followForm.getFromUser(), followForm.getToUser());
        return ResultUtils.ok();
    }

    /**
     * 获取给定用户的关注用户
     *
     * @param openid 给定用户的openid
     * @return
     */
    @GetMapping(value = "/users/{openid}/following")
    public ResponseEntity<List<UserVO>> getFollowing(@PathVariable("openid") String openid) {
        Set<String> followingIds = followService.getFollowing(openid);
        List<UserVO> collect = getUserVOS(followingIds);
        return ResultUtils.ok(collect);
    }

    /**
     * 获取粉丝
     *
     * @param openid
     * @return
     */
    @GetMapping(value = "/users/{openid}/followers")
    public ResponseEntity<List<UserVO>> getFollowers(@PathVariable("openid") String openid) {
        Set<String> followersIds = followService.getFollowers(openid);
        List<UserVO> collect = getUserVOS(followersIds);
        return ResultUtils.ok(collect);
    }

    /**
     * 共同关注
     *
     * @param one
     * @param other
     * @return
     */
    @GetMapping(value = "/following/common")
    public ResponseEntity<List<UserVO>> getCommonFriends(
            @RequestParam("one") String one, @RequestParam("other") String other) {
        Set<String> commonFollowing = followService.commonFollowing(one, other);
        List<UserVO> collect = getUserVOS(commonFollowing);
        return ResultUtils.ok(collect);
    }

    /**
     * 获取关注信息
     *
     * @param openid
     * @return
     */
    @GetMapping("/following/info")
    public ResponseEntity<Map<String, Long>> getInfo(@RequestParam("openid") String openid) {
        Long following = followService.getFollowingCount(openid);
        Long followers = followService.getFollowersCount(openid);
        Map<String, Long> map = new HashMap<>();
        map.put("following", following);
        map.put("followers", followers);
        return ResultUtils.ok(map);
    }


    private List<UserVO> getUserVOS(Set<String> ids) {
        return ids.stream().map(id -> {
            User user = userService.getUser(id).orElse(null);
            UserVO userVO = new UserVO();
            if (user != null) {
                BeanUtils.copyProperties(user, userVO);
                return userVO;
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }
}
