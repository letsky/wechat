package cn.letsky.wechat.service;

import cn.letsky.wechat.model.User;

public interface UserService {
	/**
	 * 新增或者更新用户信息
	 * @param user 用户实体
	 * @return 处理后的user对象
	 */
	User save(User user);

	/**
	 * 获取用户
	 * @param id 用户唯一标识
	 * @return user对象
	 */
	User findById(String id);

}
