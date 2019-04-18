package cn.letsky.wechat.controller;

import cn.letsky.wechat.constant.EntityType;
import cn.letsky.wechat.constant.ResultEnum;
import cn.letsky.wechat.exception.CommonException;
import cn.letsky.wechat.form.CommentForm;
import cn.letsky.wechat.model.Comment;
import cn.letsky.wechat.service.CommentService;
import cn.letsky.wechat.util.FilterUtils;
import cn.letsky.wechat.util.PageUtils;
import cn.letsky.wechat.util.ResultUtils;
import cn.letsky.wechat.viewobject.CommentVO;
import cn.letsky.wechat.viewobject.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/comment")
@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private FilterUtils filterUtils;

    /**
     * 发送评论
     *
     * @param commentForm
     * @return
     */
    @PostMapping("/sent")
    public ResultVO addComment(@Valid CommentForm commentForm) {

        if (!EntityType.contains(commentForm.getEntityType())) {
            throw new CommonException(ResultEnum.ENTITY_TYPE_ERROR);
        }
        if (filterUtils.isSensitive(commentForm.getContent())) {
            throw new CommonException(ResultEnum.SENSITIVE_WORD);
        }
        Comment res = commentService.save(
                commentForm.getUid(), commentForm.getContent(),
                commentForm.getEntityType(), commentForm.getEntityId());
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
    public ResultVO<List<CommentVO>> getComment(
            @RequestParam("entityType") Integer entityType,
            @RequestParam("entityId") Integer entityId,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size) {

        Pageable pageable = PageUtils.getPageable(page, size);
        List<CommentVO> commentVOList =
                commentService.findAllVO(entityType, entityId, pageable);
        return ResultUtils.success(commentVOList);
    }


}
