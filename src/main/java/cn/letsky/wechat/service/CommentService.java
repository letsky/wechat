package cn.letsky.wechat.service;

import cn.letsky.wechat.model.Comment;
import cn.letsky.wechat.viewobject.CommentVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {


    Comment save(String uid, String content, Integer entityType, Integer entityId);

    Long count(Integer entityType, Integer entityId);

    Page<Comment> findAll(Integer entityType, Integer entityId, Pageable pageable);

    List<CommentVO> findAllVO(Integer entityType, Integer entityId, Pageable pageable);
}
