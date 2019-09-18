package cn.letsky.wechat.controller;

import cn.letsky.wechat.constant.EntityType;
import cn.letsky.wechat.domain.form.LikeForm;
import cn.letsky.wechat.service.LikeService;
import cn.letsky.wechat.util.ResultUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/like")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    /**
     * 点赞
     *
     * @param likeForm
     * @return 点赞数
     */
    @PostMapping
    public ResponseEntity<Map<String, Long>> like(@RequestBody @Valid LikeForm likeForm) {
        Map<String, Long> map = new HashMap<>();
        Long likeNum = likeService.like(likeForm.getOpenid(), likeForm.getEntityType(), likeForm.getEntityId());
        map.put("likeNum", likeNum);
        return ResultUtils.ok(map);
    }

    /**
     * 取消点赞
     *
     * @param likeForm
     * @return 点赞数
     */
    @DeleteMapping
    public ResponseEntity<Map<String, Long>> cancel(@RequestBody @Valid LikeForm likeForm) {
        Map<String, Long> map = new HashMap<>();
        Long likeNum = likeService.cancelLike(likeForm.getOpenid(), likeForm.getEntityType(), likeForm.getEntityId());
        map.put("likeNum", likeNum);
        return ResultUtils.ok(map);
    }

    /**
     * 文章的点赞总数
     *
     * @param entityId
     * @return
     */
    @GetMapping(params = {"entityId"})
    public ResponseEntity<Map<String, Long>> likeCount(
            @RequestParam("entityId") Integer entityId) {
        Map<String, Long> map = new HashMap<>();
        Long likeNum = likeService.getCount(EntityType.ARTICLE, entityId);
        map.put("likeNum", likeNum);
        return ResultUtils.ok(map);
    }
}
