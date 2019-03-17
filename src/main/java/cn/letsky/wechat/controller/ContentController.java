package cn.letsky.wechat.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.letsky.wechat.converter.Form2Model;
import cn.letsky.wechat.model.Content;
import cn.letsky.wechat.model.User;
import cn.letsky.wechat.service.ContentService;
import cn.letsky.wechat.service.UserService;
import cn.letsky.wechat.util.ResultUtils;
import cn.letsky.wechat.viewobject.ContentVO;
import cn.letsky.wechat.viewobject.ResultVO;

@RestController
@RequestMapping("/wx/content")
public class ContentController {

	@Autowired
	private UserService userService;

	@Autowired
	private ContentService contentService;

	@GetMapping("/list")
	public ResultVO<List<ContentVO>> get(@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "size", defaultValue = "5") int size) {
		Pageable pageable = PageRequest.of(page - 1, size);
		Page<Content> res = contentService.getContent(pageable);
		List<ContentVO> list = new ArrayList<>();
		for (Content content : res.getContent()) {
			ContentVO contentVO = new ContentVO();
			BeanUtils.copyProperties(content, contentVO);
			User user = userService.getUser(content.getAuthorId()).orElse(null);
			if (user != null) {
				contentVO.setAvatarUrl(user.getAvatarUrl());
				contentVO.setNickname(user.getNickname());
			}
			list.add(contentVO);
		}
		return ResultUtils.success(list);
	}

//	@PostMapping("/sent")
//	public ResultVO<T>
}
