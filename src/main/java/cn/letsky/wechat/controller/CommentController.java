package cn.letsky.wechat.controller;

import cn.letsky.wechat.constant.EntityType;
import cn.letsky.wechat.constant.ResultEnum;
import cn.letsky.wechat.exception.CommonException;
import cn.letsky.wechat.model.Comment;
import cn.letsky.wechat.service.CommentService;
import cn.letsky.wechat.util.PageUtils;
import cn.letsky.wechat.util.ResultUtils;
import cn.letsky.wechat.viewobject.CommentVO;
import cn.letsky.wechat.viewobject.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/comment")
@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 发送评论
     *
     * @param uid
     * @param content
     * @param entityType
     * @param entityId
     * @return
     */
    @PostMapping("/sent")
    public ResultVO addComment(@RequestParam("uid") String uid,
                               @RequestParam("content") String content,
                               @RequestParam("entityType") Integer entityType,
                               @RequestParam("entityId") Integer entityId) {

        if (!EntityType.contains(entityType)) {
            throw new CommonException(ResultEnum.ENTITY_TYPE_ERROR);
        }
        Comment res = commentService.save(uid, content, entityType, entityId);
        if (res == null) {
            throw new CommonException(ResultEnum.FAIL);
        }
        return ResultUtils.success();
    }

    /**
     * 获取评论
     *
     * @param entityType
     * @param entityId
     * @param page
     * @param size
     * @return
     */
    @GetMapping
    public ResultVO<List<CommentVO>> getComment(@RequestParam("entityType") Integer entityType,
                                                @RequestParam("entityId") Integer entityId,
                                                @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                @RequestParam(value = "size", defaultValue = "5") Integer size) {
        Pageable pageable = PageUtils.getPageable(page, size);
        List<CommentVO> commentVOList = commentService.findAllVO(entityType, entityId, pageable);
        if (commentVOList == null)
            throw new CommonException(ResultEnum.NULL_COMMENT);
        return ResultUtils.success(commentVOList);
    }


}