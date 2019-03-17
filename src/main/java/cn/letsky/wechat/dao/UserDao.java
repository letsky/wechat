package cn.letsky.wechat.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cn.letsky.wechat.model.User;

@Repository
public interface UserDao extends JpaRepository<User, String> {
	
}
