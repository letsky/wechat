package cn.letsky.wechat.controller;

import cn.letsky.wechat.constant.StatusEnum;
import cn.letsky.wechat.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/like")
@RestController
public class LikeController {

    @Autowired
    private LikeService likeService;

    /**
     * 获取点赞状态
     * @param openid
     * @param entityType
     * @param entityId
     * @return 0未点赞，1已点赞
     */
    @GetMapping(params = {"openid", "entityType", "entityId"})
    public Integer getLikeStatus(@RequestParam("openid") String openid,
                                 @RequestParam("entityType") Integer entityType,
                                 @RequestParam("entityId") Integer entityId) {
        return  likeService.getLikeStatus(openid, entityType, entityId);
    }

    /**
     * 点赞
     * @param openid
     * @param entityType
     * @param entityId
     * @return 点赞数
     */
    @PostMapping("/send")
    public Long like(@RequestParam("openid") String openid,
                     @RequestParam("entityType") Integer entityType,
                     @RequestParam("entityId") Integer entityId) {
        return likeService.like(openid, entityType, entityId);
    }

    /**
     * 取消点赞
     * @param openid
     * @param entityType
     * @param entityId
     * @return 点赞数
     */
    @PostMapping("/cancel")
    public Long cancel(@RequestParam("openid") String openid,
                       @RequestParam("entityType") Integer entityType,
                       @RequestParam("entityId") Integer entityId) {
        return likeService.cancelLike(openid, entityType, entityId);
    }

    /**
     * 文章的点赞总数
     * @param entityType
     * @param entityId
     * @return
     */
    @GetMapping(params = {"entityType", "entityId"})
    public Long likeCount(@RequestParam("entityType") Integer entityType,
                          @RequestParam("entityId") Integer entityId) {
        return likeService.likeCount(entityType, entityId);
    }
}
