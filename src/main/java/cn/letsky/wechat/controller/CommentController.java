package cn.letsky.wechat.controller;

import cn.letsky.wechat.constant.EntityType;
import cn.letsky.wechat.constant.ResultEnum;
import cn.letsky.wechat.exception.CommonException;
import cn.letsky.wechat.form.CommentForm;
import cn.letsky.wechat.model.Comment;
import cn.letsky.wechat.model.User;
import cn.letsky.wechat.model.UserHolder;
import cn.letsky.wechat.service.CommentService;
import cn.letsky.wechat.service.UserService;
import cn.letsky.wechat.util.FilterUtils;
import cn.letsky.wechat.util.PageUtils;
import cn.letsky.wechat.util.ResultUtils;
import cn.letsky.wechat.vo.CommentVO;
import cn.letsky.wechat.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    private final FilterUtils filterUtils;
    private final UserHolder userHolder;
    private final UserService userService;

    public CommentController(CommentService commentService,
                             FilterUtils filterUtils,
                             UserHolder userHolder,
                             UserService userService) {
        this.commentService = commentService;
        this.filterUtils = filterUtils;
        this.userHolder = userHolder;
        this.userService = userService;
    }

    @GetMapping
    public ResultVO<List<CommentVO>> getComment(
            @RequestParam("entityType") Integer entityType,
            @RequestParam("entityId") Integer entityId,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size) {

        Pageable pageable = PageUtils.getPageable(page, size);
        Page<Comment> commentPage =
                commentService.getComments(entityType, entityId, pageable);
        List<CommentVO> commentVOList = commentPage.stream().map(e -> transform(e, new CommentVO())).collect(Collectors.toList());
        commentVOList.stream().forEach(e -> {
            Page<Comment> childrenPage =
                    commentService.getComments(EntityType.COMMENT, e.getId(), pageable);
            if (!childrenPage.isEmpty()){
                List<CommentVO> childrenComment = childrenPage.stream().map(c -> transform(c, new CommentVO())).collect(Collectors.toList());
                e.setChildren(childrenComment);
            }
        });
        return ResultUtils.success(commentVOList);
    }

    @PostMapping
    public ResultVO addComment(@Valid CommentForm commentForm) {

        if (!EntityType.contains(commentForm.getEntityType())) {
            throw new CommonException(ResultEnum.ENTITY_TYPE_ERROR);
        }
        if (filterUtils.isSensitive(commentForm.getContent())) {
            throw new CommonException(ResultEnum.SENSITIVE_WORD);
        }
        Comment res = commentService.post(
                userHolder.get().getOpenid(), commentForm.getContent(),
                commentForm.getEntityType(), commentForm.getEntityId());
        if (res == null) {
            throw new CommonException(ResultEnum.FAIL);
        }
        return ResultUtils.success();
    }

    private CommentVO transform(Comment comment, CommentVO commentVO){
        BeanUtils.copyProperties(comment, commentVO);
        User user = userService.getUser(comment.getUid()).orElseThrow(NoSuchElementException::new);
        if (user == null){
            return null;
        }
        commentVO.setUid(user.getOpenid());
        commentVO.setNickname(user.getNickname());
        commentVO.setAvatarUrl(user.getAvatarUrl());
        return commentVO;
    }

}
