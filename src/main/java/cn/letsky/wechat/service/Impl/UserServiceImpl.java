package cn.letsky.wechat.service.Impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.letsky.wechat.dao.UserDao;
import cn.letsky.wechat.model.User;
import cn.letsky.wechat.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;

	@Override
	public User saveUser(User user) {
		return userDao.save(user);
	}

	@Override
	public Optional<User> getUser(String id) {
		return userDao.findById(id);
	}

}
