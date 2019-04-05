package cn.letsky.wechat.form;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class WxUserForm implements Form {

	@NotNull(message = "openid为null")
	private String openid;
	
	@NotEmpty(message = "昵称为空")
	private String nickname;

	private Integer gender;
	
	@NotEmpty(message = "头像为空")
	private String avatarUrl;

	public WxUserForm() {
	}

	public WxUserForm(String openid, String nickname, Integer gender, String avatarUrl) {
		this.openid = openid;
		this.nickname = nickname;
		this.gender = gender;
		this.avatarUrl = avatarUrl;
	}
}
