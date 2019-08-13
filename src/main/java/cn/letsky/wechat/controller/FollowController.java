package cn.letsky.wechat.controller;

import cn.letsky.wechat.model.User;
import cn.letsky.wechat.model.UserHolder;
import cn.letsky.wechat.service.FollowService;
import cn.letsky.wechat.service.UserService;
import cn.letsky.wechat.util.ResultUtils;
import cn.letsky.wechat.vo.ResultVO;
import cn.letsky.wechat.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/follows")
@CrossOrigin(allowCredentials = "true")
public class FollowController {

    private final FollowService followService;
    private final UserService userService;
    private final UserHolder userHolder;

    public FollowController(
            FollowService followService,
            UserService userService,
            UserHolder userHolder) {
        this.followService = followService;
        this.userService = userService;
        this.userHolder = userHolder;
    }

    /**
     * 获取给定用户的关注用户
     *
     * @param openid 给定用户的openid
     * @return
     */
    @GetMapping(params = {"openid"})
    public ResultVO<List<UserVO>> getFollows(
            @RequestParam("openid") String openid) {
        Set<String> followIds = followService.getFollows(openid);
        List<UserVO> voList = getUsers(followIds);
        Map<String, Long> map = new HashMap<>();
        map.put("count", followService.getFollowCount(openid));

        return ResultUtils.success(voList, map);
    }

    /**
     * 获取粉丝
     *
     * @param openid
     * @return
     */
    @GetMapping("/{openid}/fans")
    public ResultVO<List<UserVO>> getFans(
            @PathVariable("openid") String openid) {
        Set<String> fansIds = followService.getFans(openid);
        List<UserVO> voList = getUsers(fansIds);
        Map<String, Long> map = new HashMap<>();
        map.put("count", followService.getFansCount(openid));

        return ResultUtils.success(voList, map);
    }

    @GetMapping()
    public ResultVO<Map<String, Long>> getFollowAndFans() {
        String openid = userHolder.get().getOpenid();
        Long followCount = followService.getFollowCount(openid);
        Long fansCount = followService.getFansCount(openid);
        Map<String, Long> map = new HashMap<>();
        map.put("followCount", followCount);
        map.put("fansCount", fansCount);
        return ResultUtils.success(map);
    }

    /**
     * 关注用户
     *
     * @param openid   当前用户
     * @param followId 被关注用户
     * @return
     */
    @PostMapping
    public ResultVO<Long> follow (
            @RequestParam("openid") String openid,
            @RequestParam("followId") String followId) {
        Long followCount = followService.follow(openid, followId);
        return ResultUtils.success(followCount);
    }

    /**
     * 取消关注
     *
     * @param openid
     * @param unFollowId
     * @return
     */
    @DeleteMapping
    public ResultVO<Long> unFollow(
            @RequestParam("openid") String openid,
            @RequestParam("unFollowId") String unFollowId) {
        Long followCount = followService.unFollow(openid, unFollowId);
        return ResultUtils.success(followCount);
    }

    /**
     * 将给定的id集合转换成UserVO对象
     *
     * @param ids 用户id集合
     * @return
     */
    private List<UserVO> getUsers(Set<String> ids) {
        List<UserVO> userVOList = ids.stream().map(id -> {
            User user = userService.getUser(id);
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            return userVO;
        }).collect(Collectors.toList());
        return userVOList;
    }
}
