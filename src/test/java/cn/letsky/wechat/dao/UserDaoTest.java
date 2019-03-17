package cn.letsky.wechat.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import cn.letsky.wechat.model.User;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoTest {

	@Autowired
	private UserDao userDao;
	
	@Test
	@Transactional
	public void save() {
		User user = new User();
		user.setOpenid("wx6a356c77cc3f80d5");
		user.setNickname("漠轻桥");
		user.setGender(1);
		user.setAvatarUrl("http://h.hiphotos.baidu.com/image/pic/item/730e0cf3d7ca7bcb051bd704b0096b63f624a8bc.jpg");
		userDao.save(user);
		Assert.assertNotNull(user);
	}
}
