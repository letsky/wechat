package cn.letsky.wechat.dao;

import cn.letsky.wechat.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentDao extends JpaRepository<Comment, Integer> {

    Page<Comment> findAllByEntityTypeAndEntityIdOrderByCreatedDesc(
            Integer entityType, Integer entityId, Pageable pageable);

    Long countByEntityTypeAndEntityId(Integer entityType, Integer entityId);
}
