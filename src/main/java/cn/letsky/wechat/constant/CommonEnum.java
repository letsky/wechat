package cn.letsky.wechat.constant;

import lombok.Getter;

@Getter
public enum CommonEnum {

	SUCCESS(200, "success"), 
	ERROR(1, "error");
	
	private Integer code;

	private String msg;

	CommonEnum(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}
}
