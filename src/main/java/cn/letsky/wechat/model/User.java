package cn.letsky.wechat.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class User {

	@Id
	private String openid;
	
	private String nickname;
	
	private int gender;
	
	@Column(name = "avatar_url")
	private String avatarUrl;
	
	private String email;
	
	private String password;
	
	private String salt;
	
	private Date created;
	
	public User() {	
	}
	
	public User(String openid, String nickname, int gender, String avatarUrl) {
		this.openid = openid;
		this.nickname = nickname;
		this.gender = gender;
		this.avatarUrl = avatarUrl;
	}	
}
