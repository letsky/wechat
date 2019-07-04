package cn.letsky.wechat.dao;

import cn.letsky.wechat.constant.EntityType;
import cn.letsky.wechat.model.Comment;
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
public class CommentDaoTest {

    @Autowired
    private CommentDao commentDao;

    @Test
    public void findAllByEntityTypeAndEntityId(){
        Pageable pageable = PageRequest.of(0, 5);
        Page<Comment> comments = commentDao.findAllByEntityTypeAndEntityIdOrderByCreatedDesc(
                EntityType.ARTICLE.getType(), 2, pageable);
        Assert.assertNotNull(comments.getContent());
    }

    @Test
    public void countByEntityTypeAndEntityId(){
        long count = commentDao.countByEntityTypeAndEntityId(EntityType.ARTICLE.getType(), 2);
        Assert.assertNotEquals(0, count);
    }
}
