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
	public void save() {
		User user = new User();
		user.setOpenid("wx6a356c77cc3f80d5");
		user.setNickname("漠轻桥");
		user.setGender(1);
		user.setAvatarUrl("https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTK3scd1NENlOKKXJQYwQibAuJkBbMV3VTCZuPial0DSicl66XSibgI0DJtRZhwfW3n8jcQ5XrTO5ibxepg/132");
		userDao.save(user);
		Assert.assertNotNull(user);
	}

	public void findOne(){
		User user = userDao.getOne("wx6a356c77cc3f80d5");
		Assert.assertNotNull(user);
	}
}
