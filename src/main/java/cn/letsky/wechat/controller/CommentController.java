package cn.letsky.wechat.controller;

import cn.letsky.wechat.constant.EntityType;
import cn.letsky.wechat.constant.ResultEnum;
import cn.letsky.wechat.domain.form.CommentForm;
import cn.letsky.wechat.domain.model.Comment;
import cn.letsky.wechat.domain.model.User;
import cn.letsky.wechat.domain.vo.CommentVO;
import cn.letsky.wechat.exception.CommonException;
import cn.letsky.wechat.service.CommentService;
import cn.letsky.wechat.service.UserService;
import cn.letsky.wechat.util.FilterUtils;
import cn.letsky.wechat.util.PageUtils;
import cn.letsky.wechat.util.ResultUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    private final FilterUtils filterUtils;
    private final UserService userService;

    public CommentController(CommentService commentService, FilterUtils filterUtils,
                             UserService userService) {
        this.commentService = commentService;
        this.filterUtils = filterUtils;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<CommentVO>> getComments(
            @RequestParam("entityType") Integer entityType,
            @RequestParam("entityId") Integer entityId,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size) {

        Pageable pageable = PageUtils.getPageable(page, size);
        Page<Comment> commentPage =
                commentService.getComments(entityType, entityId, pageable);
        List<CommentVO> commentVOList = commentPage.stream()
                .map(e -> transform(e, new CommentVO()))
                .collect(Collectors.toList());
        commentVOList.stream().forEach(e -> {
            Page<Comment> childrenPage =
                    commentService.getComments(EntityType.COMMENT, e.getId(), pageable);
            if (!childrenPage.isEmpty()) {
                List<CommentVO> childrenComment = childrenPage.stream()
                        .map(c -> transform(c, new CommentVO()))
                        .collect(Collectors.toList());
                e.setChildren(childrenComment);
            }
        });
        return ResultUtils.ok(commentVOList);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Comment> addComment(@RequestBody @Valid CommentForm commentForm) {

        if (!EntityType.contains(commentForm.getEntityType())) {
            throw new CommonException(ResultEnum.ENTITY_TYPE_ERROR);
        }
        if (filterUtils.isSensitive(commentForm.getContent())) {
            throw new CommonException(ResultEnum.SENSITIVE_WORD);
        }
        commentService.post(
                commentForm.getOpenid(), commentForm.getContent(),
                commentForm.getEntityType(), commentForm.getEntityId());
        return ResultUtils.ok();
    }

    private CommentVO transform(Comment comment, CommentVO commentVO) {
        BeanUtils.copyProperties(comment, commentVO);
        User user = userService.getUser(comment.getUid())
                .orElseThrow(EntityNotFoundException::new);
        if (user == null) {
            return null;
        }
        commentVO.setOpenid(user.getOpenid());
        commentVO.setNickname(user.getNickname());
        commentVO.setAvatarUrl(user.getAvatarUrl());
        return commentVO;
    }

}
