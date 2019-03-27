package cn.letsky.wechat.controller;

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

    @PostMapping("/sent")
    public ResultVO addComment(@RequestParam("uid") String uid,
                           @RequestParam("content") String content,
                           @RequestParam("entityType") Integer entityType,
                           @RequestParam("entityId") Integer entityId){

        Comment res = commentService.save(uid, content, entityType, entityId);
        if (res == null){
            throw new CommonException(ResultEnum.FAIL);
        }
        return ResultUtils.success();
    }

    @GetMapping
    public ResultVO<List<CommentVO>> getComment(@RequestParam("entityType") Integer entityType,
                               @RequestParam("entityId") Integer entityId,
                               @RequestParam(value = "page", defaultValue = "1") Integer page,
                               @RequestParam(value = "size", defaultValue = "5") Integer size){
        Pageable pageable = PageUtils.getPageable(page, size);
        List<CommentVO> commentVOList = commentService.findAllVO(entityType, entityId, pageable);
        return ResultUtils.success(commentVOList);
    }

}
