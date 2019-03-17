package cn.letsky.wechat.util;

import java.util.UUID;

public class SessionUtils {

	public static String create() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	
}
