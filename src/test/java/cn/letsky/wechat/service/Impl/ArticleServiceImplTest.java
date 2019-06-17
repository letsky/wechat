package cn.letsky.wechat.service.Impl;

import cn.letsky.wechat.model.Article;
import cn.letsky.wechat.service.ArticleService;
import lombok.NoArgsConstructor;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ArticleServiceImplTest {

    private Integer ARTICLE_ID = 1;
    private final String OPENId = "wxopenid";

    @Autowired
    private ArticleService articleService;


    @Before
    public void save(){
        Article article = new Article();
        article.setContent("test");
        article.setVisible(1);
        article.setOpenid(OPENId);
        Article res = articleService.save(article);
        ARTICLE_ID = res.getId();
        System.out.println(res);
        Assert.assertNotNull(res);

    }

    @Test
    public void findById() {
        Article res = articleService.findById(ARTICLE_ID);
        System.out.println(res);
        Assert.assertNotNull(res);
    }

    @Test
    public void findAll() {
        Page<Article> articlePage = articleService.findAll(1,20);
        Long num = articlePage.getTotalElements();
        System.out.println(num);
        Assert.assertNotEquals(Long.valueOf(0), num);
    }

    @Test
    public void findAllByOpenid() {
        Page<Article> articlePage = articleService.findAllByOpenid(OPENId, 1, 20);
        Long num = articlePage.getTotalElements();
        System.out.println(num);
        Assert.assertNotEquals(Long.valueOf(0), num);
    }

    @After
    public void delete() {
        articleService.delete(ARTICLE_ID);
        Article res = articleService.findById(ARTICLE_ID);
        Assert.assertEquals(Integer.valueOf(1), res.getStatus());
    }
}