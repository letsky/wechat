package cn.letsky.wechat.dao;

import java.util.Optional;

import cn.letsky.wechat.model.Article;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleDaoTest {

	@Autowired
	private ArticleDao articleDao;
	
//	@Test
//	@Transactional
//	public void add() {
//		for(int i = 10; i < 20; i++) {
//			Article content = new Article();
//			content.setContent("这是内容" + i);
//			content.setCreated(new Date());
//			content.setAuthorId("wx12345" + i);
//			Article res = articleDao.save(content);
//			Assert.assertNotNull(res);
//		}
//	}
//	
	@Test
	public void findOne() {
		Optional<Article> res = articleDao.findById(1);
		Assert.assertNotNull(res.orElse(null));
	}
	
	@Test
	public void findAll() {
		Pageable page = PageRequest.of(0, 3);
		Page<Article> res = articleDao.findAll(page);
		Assert.assertEquals(3, res.getSize());
	}
	
	@Test
	@Transactional
	public void modify() {
		Optional<Article> res = articleDao.findById(1);
		Article article = res.orElse(null);
		article.setCommentNum(1);
		Article result = articleDao.save(article);
		Assert.assertNotNull(result);
	}
	
	
	@Test
	public void findAllByStatus() {
		Pageable page = PageRequest.of(0, 3);
		Page<Article> res = articleDao.findAllByStatus(0, page);
		Assert.assertEquals(3, res.getSize());
	}
	
}
