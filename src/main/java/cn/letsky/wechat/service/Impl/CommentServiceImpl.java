package cn.letsky.wechat.service.Impl;

import cn.letsky.wechat.constant.ResultEnum;
import cn.letsky.wechat.domain.model.Comment;
import cn.letsky.wechat.exception.CommonException;
import cn.letsky.wechat.repository.CommentRepository;
import cn.letsky.wechat.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * {@link CommentService}实现类
 */
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment post(String openid, String content, Integer entityType, Integer entityId) {
        if (StringUtils.isEmpty(content)) {
            throw new CommonException(ResultEnum.SEND_NULL_COMMENT);
        }
        Comment comment = new Comment();
        comment.setUid(openid);
        comment.setEntityType(entityType);
        comment.setEntityId(entityId);
        comment.setContent(content);
        return commentRepository.save(comment);
    }

    @Override
    public Long getCount(Integer entityType, Integer entityId) {
        return commentRepository.countByEntityTypeAndEntityId(entityType, entityId);
    }

    @Override
    public Page<Comment> getComments(Integer entityType,
                                     Integer entityId, Pageable pageable) {
        Page<Comment> comments = commentRepository
                .findAllByEntityTypeAndEntityIdOrderByCreatedDesc(
                        entityType, entityId, pageable);
        return comments.isEmpty() ? Page.empty() : comments;
    }
}
