package cn.letsky.wechat.service.Impl;

import cn.letsky.wechat.domain.model.User;
import cn.letsky.wechat.repository.UserRepository;
import cn.letsky.wechat.service.UserService;
import cn.letsky.wechat.util.PageUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

/**
 * {@link UserService}实现类
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public Optional<User> getUser(String openid) {
		return userRepository.findById(openid);
	}

	@Override
	public Page<User> getUsers(Integer page, Integer size) {
		Pageable pageable = PageUtils.getPageable(page, size);
		return userRepository.findAll(pageable);
	}

	@Override
	public void delete(String openid) {
		User user = userRepository.findById(openid)
                .orElseThrow(EntityNotFoundException::new);
		userRepository.delete(user);
	}

	@Override
	public boolean checkUser(String openid) {
		return userRepository.findById(openid).isPresent();
	}

}
