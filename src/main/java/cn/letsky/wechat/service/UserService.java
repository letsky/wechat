package cn.letsky.wechat.service;

import cn.letsky.wechat.model.User;
import org.springframework.data.domain.Page;

public interface UserService {
    /**
     * 新增或者更新用户信息
     *
     * @param user 用户实体
     * @return 保存后的User对象
     */
    User save(User user);

    /**
     * 获取用户
     *
     * @param id 用户唯一标识
     * @return 当前id对应的User实体
     */
    User getUser(String id);

    /**
     * 获取用户列表
     *
     * @param page 页码
     * @param size 每页的数量
     * @return
     */
    Page<User> getUsers(Integer page, Integer size);
}
