package cn.letsky.wechat.util;

import java.util.UUID;

public class SessionUtils {

	/**
	 * 创建唯一的登录态
	 * @return 登录状态session
	 */
	public static String create() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	
}
