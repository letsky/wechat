package cn.letsky.wechat.repository;

import cn.letsky.wechat.constant.EntityType;
import cn.letsky.wechat.domain.model.Comment;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Test
    public void findAllByEntityTypeAndEntityId(){
        Pageable pageable = PageRequest.of(0, 5);
        Page<Comment> comments = commentRepository.findAllByEntityTypeAndEntityIdOrderByCreatedDesc(
                EntityType.ARTICLE, 2, pageable);
        Assert.assertNotNull(comments.getContent());
    }

    @Test
    public void countByEntityTypeAndEntityId(){
        long count = commentRepository.countByEntityTypeAndEntityId(EntityType.ARTICLE, 2);
        Assert.assertNotEquals(0, count);
    }
}
