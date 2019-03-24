package cn.letsky.wechat.dao;

import cn.letsky.wechat.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentDao extends JpaRepository<Comment, Integer> {

    List<Comment> findAllByOwnerId(Integer ownerId);
}
