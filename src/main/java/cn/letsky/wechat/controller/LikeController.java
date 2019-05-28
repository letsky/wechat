package cn.letsky.wechat.controller;

import cn.letsky.wechat.service.LikeService;
import cn.letsky.wechat.util.ResultUtils;
import cn.letsky.wechat.viewobject.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/like")
@RestController
public class LikeController {

    @Autowired
    private LikeService likeService;

    /**
     * 获取点赞状态
     *
     * @param openid
     * @param entityType
     * @param entityId
     * @return 0未点赞，1已点赞
     */
    @GetMapping(params = {"openid", "entityType", "entityId"})
    public ResultVO getLikeStatus(@RequestParam("openid") String openid,
                                  @RequestParam("entityType") Integer entityType,
                                  @RequestParam("entityId") Integer entityId) {
        Map<String, Integer> map = new HashMap<>();
        Integer liked = likeService.getLikeStatus(openid, entityType, entityId);
        map.put("liked", liked);
        return ResultUtils.success(map);
    }

    /**
     * 点赞
     *
     * @param openid
     * @param entityType
     * @param entityId
     * @return 点赞数
     */
    @PostMapping("/send")
    public ResultVO like(@RequestParam("openid") String openid,
                               @RequestParam("entityType") Integer entityType,
                               @RequestParam("entityId") Integer entityId) {
        Map<String, Long> map = new HashMap<>();
        Long likeNum = likeService.like(openid, entityType, entityId);
        map.put("likeNum", likeNum);
        return ResultUtils.success(map);
    }

    /**
     * 取消点赞
     *
     * @param openid
     * @param entityType
     * @param entityId
     * @return 点赞数
     */
    @PostMapping("/cancel")
    public ResultVO cancel(@RequestParam("openid") String openid,
                                 @RequestParam("entityType") Integer entityType,
                                 @RequestParam("entityId") Integer entityId) {
        Map<String, Long> map = new HashMap<>();
        Long likeNum = likeService.cancelLike(openid, entityType, entityId);
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
