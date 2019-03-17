package cn.letsky.wechat.service;

import java.util.Optional;

import cn.letsky.wechat.model.User;

public interface UserService {
	/**
	 * 新增或者更新用户信息
	 * @param user User类型的对象
	 * @return 处理后的user对象
	 */
	User saveUser(User user);
	
	Optional<User> getUser(String id);
}
