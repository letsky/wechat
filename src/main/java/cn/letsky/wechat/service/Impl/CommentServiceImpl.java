package cn.letsky.wechat.service.Impl;

import cn.letsky.wechat.constant.ResultEnum;
import cn.letsky.wechat.dao.CommentDao;
import cn.letsky.wechat.dao.UserDao;
import cn.letsky.wechat.exception.CommonException;
import cn.letsky.wechat.model.Comment;
import cn.letsky.wechat.model.User;
import cn.letsky.wechat.service.CommentService;
import cn.letsky.wechat.service.UserService;
import cn.letsky.wechat.viewobject.CommentVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private UserService userService;


    @Override
    public Comment save(String uid, String content, Integer entityType, Integer entityId) {
        if (StringUtils.isEmpty(content)) {
            throw new CommonException(ResultEnum.SEND_NULL_COMMENT);
        }
        Comment comment = new Comment();
        comment.setUid(uid);
        comment.setEntityType(entityType);
        comment.setEntityId(entityId);
        comment.setContent(content);
        return commentDao.save(comment);
    }

    @Override
    public Long count(Integer entityType, Integer entityId) {
        return commentDao.countByEntityTypeAndEntityId(entityType, entityId);
    }

    @Override
    public Page<Comment> findAll(Integer entityType,
                                 Integer entityId, Pageable pageable) {
        Page<Comment> comments = commentDao
                .findAllByEntityTypeAndEntityIdOrderByCreatedDesc(
                        entityType, entityId, pageable);
        return comments.isEmpty() ? Page.empty() : comments;
    }

    @Override
    public List<CommentVO> findAllVO(Integer entityType,
                                     Integer entityId, Pageable pageable) {
        Page<Comment> comments = findAll(entityType, entityId, pageable);
        if (comments.isEmpty())
            return null;
        List<CommentVO> commentVOList = comments.stream()
                .map(comment -> {
                    CommentVO commentVO = new CommentVO();
                    BeanUtils.copyProperties(comment, commentVO);
                    User user = userService.findById(comment.getUid());
                    if (user == null) {
                        return null;
                    }
                    commentVO.setUid(user.getOpenid());
                    commentVO.setNickname(user.getNickname());
                    commentVO.setAvatarUrl(user.getAvatarUrl());
                    return commentVO;
                }).collect(Collectors.toList());
        return commentVOList;
    }
}
