package cn.letsky.wechat.form;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class WxUserForm implements Form {

	@NotEmpty(message = "微信授权失败")
	private String openid;
	
	@NotEmpty(message = "微信授权失败")
	private String nickname;
	
	@NotEmpty(message = "微信授权失败")
	private String gender;
	
	@NotEmpty(message = "微信授权失败")
	private String avatarUrl;
	
	public WxUserForm() {
	}

	public WxUserForm(String openid, String nickname, String gender, String avatarUrl) {
		super();
		this.openid = openid;
		this.nickname = nickname;
		this.gender = gender;
		this.avatarUrl = avatarUrl;
	}
}
