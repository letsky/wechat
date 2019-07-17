package cn.letsky.wechat.dao;

import cn.letsky.wechat.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import cn.letsky.wechat.constant.EntityType;

@Repository
public interface CommentDao extends JpaRepository<Comment, Integer> {

    Page<Comment> findAllByEntityTypeAndEntityIdOrderByCreatedDesc(
            Integer entityType, Integer entityId, Pageable pageable);

    /**
     * 统计评论数
     * @param entityType 实体类型
     * @param entityId
     * @return
     * {@link EntityType}
     */
    Long countByEntityTypeAndEntityId(Integer entityType, Integer entityId);
}
