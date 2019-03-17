package cn.letsky.wechat.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.letsky.wechat.model.Content;

public interface ContentService {
	
	/**
	 * 获取正常状态的content
	 * @param pageable 分页
	 * @return 分页后正常状态的content列表
	 */
	Page<Content> getContent(Pageable pageable);
	
	
}
