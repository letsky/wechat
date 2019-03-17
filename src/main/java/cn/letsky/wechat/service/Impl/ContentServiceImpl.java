package cn.letsky.wechat.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import cn.letsky.wechat.dao.ContentDao;
import cn.letsky.wechat.model.Content;
import cn.letsky.wechat.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService {
	
	@Autowired
	private ContentDao contentDao;

	@Override
	public Page<Content> getContent(Pageable pageable) {
		return contentDao.findAllByStatus(0, pageable);
	}

}
