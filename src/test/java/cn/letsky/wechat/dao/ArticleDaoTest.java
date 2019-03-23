package cn.letsky.wechat.dao;

import java.util.Date;
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
	
	@Test
	public void add() {
		String openid = "wx6a356c77cc3f80d5";
		for(int i = 0; i < 20; i++) {
			Article content = new Article();
			content.setContent("【缺爱还是生活压力大？#2018年结婚率创新低# 沪浙最不积极[doge]】数据显示，2018年全国结婚率为7.2‰，为2013年以来的最低。经济越发达结婚率越低，比如2018年上海、浙江结婚率只有4.4‰、5.9‰，广东、北京、天津等地结婚率也偏低。专家表示，大城市人们生活节奏快，经济压力大，都会导致结婚率的下降。“因为生活压力大，缺爱的人也越来越多。”(21世纪经济报道)");
			content.setCreated(new Date());
			content.setOpenid(openid);
			content.setStatus(0);
			Article res = articleDao.save(content);
			Assert.assertNotNull(res);
		}
	}

	@Test
	public void findOne() {
		Optional<Article> res = articleDao.findById(3);
		System.err.println(res.orElse(null));
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
