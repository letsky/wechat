package cn.letsky.wechat.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class User implements Serializable {

	private static final long serialVersionUID = 3717836851471119239L;

	/**
	 * 用户唯一标识
	 */
	@Id
	private String openid;

	/**
	 * 昵称
	 */
	private String nickname;

	/**
	 * 性别 0：未知、1：男、2：女
	 */
	private Integer gender;

	/**
	 * 头像
	 */
	@Column(name = "avatar_url")
	private String avatarUrl;

	/**
	 * 邮箱地址
	 */
	private String email;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 加密随机字符串
	 */
	private String salt;

	/**
	 * 注册时间
	 */
	private Date created;
	
	public User() {	
	}
	
	public User(String openid, String nickname, Integer gender, String avatarUrl) {
		this.openid = openid;
		this.nickname = nickname;
		this.gender = gender;
		this.avatarUrl = avatarUrl;
	}	
}
