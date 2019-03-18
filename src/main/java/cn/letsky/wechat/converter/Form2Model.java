package cn.letsky.wechat.converter;

import org.springframework.beans.BeanUtils;

import cn.letsky.wechat.form.Form;

public class Form2Model {

	/**
	 * 把Form接口的表单转换成Model
	 * @param form 在form包下实现Form接口的对象
	 * @param clazz 转换成model类型
	 * @return model类型的对象
	 */
	public static <T> T convert(Form form, Class<T> clazz) {

		try {
			T model = clazz.newInstance();
			BeanUtils.copyProperties(form, model);
			return model;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
}
