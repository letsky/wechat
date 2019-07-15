package cn.letsky.wechat.controller;

import cn.letsky.wechat.model.UserHolder;
import cn.letsky.wechat.service.LikeService;
import cn.letsky.wechat.util.ResultUtils;
import cn.letsky.wechat.vo.ResultVO;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/like")
public class LikeController {

    private final LikeService likeService;
    private final UserHolder userHolder;

    public LikeController(LikeService likeService,
                          UserHolder userHolder) {
        this.likeService = likeService;
        this.userHolder = userHolder;
    }

    /**
     * 获取点赞状态
     *
     * @param entityType
     * @param entityId
     * @return 0未点赞，1已点赞
     */
    @GetMapping("/status")
    public ResultVO getLikeStatus(@RequestParam("entityType") Integer entityType,
                                  @RequestParam("entityId") Integer entityId) {

        Map<String, Integer> map = new HashMap<>();
        Integer liked = likeService.getLikeStatus(userHolder.get().getOpenid(), entityType, entityId);
        map.put("liked", liked);
        return ResultUtils.success(map);
    }

    /**
     * 点赞
     *
     * @param entityType
     * @param entityId
     * @return 点赞数
     */
    @PostMapping("/send")
    public ResultVO like(@RequestParam("entityType") Integer entityType,
                         @RequestParam("entityId") Integer entityId) {
        Map<String, Long> map = new HashMap<>();
        Long likeNum = likeService.like(userHolder.get().getOpenid(), entityType, entityId);
        map.put("likeNum", likeNum);
        return ResultUtils.success(map);
    }

    /**
     * 取消点赞
     *
     * @param entityType
     * @param entityId
     * @return 点赞数
     */
    @PostMapping("/cancel")
    public ResultVO cancel(@RequestParam("entityType") Integer entityType,
                           @RequestParam("entityId") Integer entityId) {
        Map<String, Long> map = new HashMap<>();
        Long likeNum = likeService.cancelLike(userHolder.get().getOpenid(), entityType, entityId);
        map.put("likeNum", likeNum);
        return ResultUtils.success(map);
    }

    /**
     * 文章的点赞总数
     *
     * @param entityType
     * @param entityId
     * @return
     */
    @GetMapping(params = {"entityType", "entityId"})
    public ResultVO likeCount(@RequestParam("entityType") Integer entityType,
                              @RequestParam("entityId") Integer entityId) {
        Map<String, Long> map = new HashMap<>();
        Long likeNum = likeService.likeCount(entityType, entityId);
        map.put("likeNum", likeNum);
        return ResultUtils.success(map);
    }
}
