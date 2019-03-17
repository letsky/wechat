package cn.letsky.wechat.dao;

import java.util.Date;
import java.util.Optional;

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

import cn.letsky.wechat.model.Content;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContentDaoTest {

	@Autowired
	private ContentDao contentDao;
	
//	@Test
//	@Transactional
//	public void add() {
//		for(int i = 10; i < 20; i++) {
//			Content content = new Content();
//			content.setContent("这是内容" + i);
//			content.setCreated(new Date());
//			content.setAuthorId("wx12345" + i);
//			Content res = contentDao.save(content);
//			Assert.assertNotNull(res);
//		}
//	}
//	
	@Test
	public void findOne() {
		Optional<Content> res = contentDao.findById(1);
		Assert.assertNotNull(res.orElse(null));
	}
	
	@Test
	public void findAll() {
		Pageable page = PageRequest.of(0, 3);
		Page<Content> res = contentDao.findAll(page);
		Assert.assertEquals(3, res.getSize());
	}
	
	@Test
	@Transactional
	public void modify() {
		Optional<Content> res = contentDao.findById(1);
		Content content = res.orElse(null);
		content.setCommentNum(1);
		Content result = contentDao.save(content);
		Assert.assertNotNull(result);
	}
	
	
	@Test
	public void findAllByStatus() {
		Pageable page = PageRequest.of(0, 3);
		Page<Content> res = contentDao.findAllByStatus(0, page);
		Assert.assertEquals(3, res.getSize());
	}
	
}
