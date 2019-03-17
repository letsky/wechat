package cn.letsky.wechat.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtils {

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	
	/**
	 * 将对象转换成Json格式
	 * @param object 被转换的对象
	 * @return Json字符串
	 */
	public static String toJson(Object object) {
		try {
			return OBJECT_MAPPER.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			log.error("Json转换失败：" + e.getMessage());
		}
		return null;
	}
}