package cn.letsky.wechat.controller;

import cn.letsky.wechat.service.CommentService;
import cn.letsky.wechat.util.ResultUtils;
import cn.letsky.wechat.viewobject.CommentVO;
import cn.letsky.wechat.viewobject.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/comment")
@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     *
     * @param id 被评论的资源id
     * @return
     */
    @RequestMapping("/{id}")
    public ResultVO<List<CommentVO>> get(@PathVariable("id") int id){
        List<CommentVO> commentVOList = commentService.findAllByOwnerId(id);
        return ResultUtils.success(commentVOList);
    }

}
