package cn.letsky.wechat.service;

import cn.letsky.wechat.viewobject.CommentVO;

import java.util.List;

public interface CommentService {

    /**
     * 保存评论
     * @param commentVO
     * @return
     */
    CommentVO save(CommentVO commentVO);

    List<CommentVO> findAllByOwnerId(Integer ownerId);
}
