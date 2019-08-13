package cn.letsky.wechat.service.Impl;

import cn.letsky.wechat.dao.UserDao;
import cn.letsky.wechat.model.User;
import cn.letsky.wechat.service.UserService;
import cn.letsky.wechat.util.PageUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * {@link UserService}实现类
 */
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
	public Optional<User> getUser(String id) {
		return userDao.findById(id);
	}

	@Override
	public Page<User> getUsers(Integer page, Integer size) {
		Pageable pageable = PageUtils.getPageable(page, size);
		return userDao.findAll(pageable);
	}

}
