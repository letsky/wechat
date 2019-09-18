package cn.letsky.wechat.repository;

import cn.letsky.wechat.domain.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void save() {
		User user = new User();
		user.setOpenid("wx6a356c77cc3f80d5");
		user.setNickname("漠轻桥");
		user.setGender(1);
		user.setAvatarUrl("https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTK3scd1NENlOKKXJQYwQibAuJkBbMV3VTCZuPial0DSicl66XSibgI0DJtRZhwfW3n8jcQ5XrTO5ibxepg/132");
		User res = userRepository.save(user);
		Assert.assertNotNull(res);
	}

	@Test
	public void findOne(){
		Optional<User> user = userRepository.findById("ex");
		if (user.isPresent()){
			System.err.println(user);
		}
		System.err.println("null");
	}
}
