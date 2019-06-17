package cn.letsky.wechat.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.letsky.wechat.dao.UserDao;
import cn.letsky.wechat.model.User;
import cn.letsky.wechat.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	private final UserDao userDao;

	public UserServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public User save(User user) {
		return userDao.save(user);
	}

	@Override
	public User findById(String id) {
		return userDao.findById(id).orElse(null);
	}

}
