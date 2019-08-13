package cn.letsky.wechat.service;

import cn.letsky.wechat.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {

    /**
     * 发表评论
     *
     * @param openid     微信用户id
     * @param content    评论主体
     * @param entityType 评论的实体类型
     * @param entityId   评论的实体id
     * @return 保存的评论
     */
    Comment post(String openid, String content,
                 Integer entityType, Integer entityId);

    /**
     * 获取评论数
     *
     * @param entityType 需要获取评论总数的实体类型
     * @param entityId   需要获取评论总数的实体id
     * @return 当前实体的评论总数
     */
    Long getCount(Integer entityType, Integer entityId);

    /**
     * 获取评论列表
     *
     * @param entityType 需要获取评论的实体类型
     * @param entityId   需要获取评论的实体id
     * @param pageable   分页对象
     * @return 当前实体的评论列表
     */
    Page<Comment> getComments(Integer entityType, Integer entityId, Pageable pageable);
}
