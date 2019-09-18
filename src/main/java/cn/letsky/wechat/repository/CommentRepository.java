package cn.letsky.wechat.repository;

import cn.letsky.wechat.constant.EntityType;
import cn.letsky.wechat.domain.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    /**
     * 查询评论
     *
     * @param entityType
     * @param entityId
     * @param pageable
     * @return
     */
    Page<Comment> findAllByEntityTypeAndEntityIdOrderByCreatedDesc(
            Integer entityType, Integer entityId, Pageable pageable);

    /**
     * 统计评论数
     *
     * @param entityType 实体类型
     * @param entityId
     * @return
     * {@link EntityType}
     */
    Long countByEntityTypeAndEntityId(Integer entityType, Integer entityId);
}
