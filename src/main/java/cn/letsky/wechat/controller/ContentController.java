package cn.letsky.wechat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.letsky.wechat.model.Content;
import cn.letsky.wechat.service.ContentService;
import cn.letsky.wechat.service.UserService;
import cn.letsky.wechat.util.ResultUtils;
import cn.letsky.wechat.viewobject.ResultVO;

@RestController
@RequestMapping("/wx/content")
public class ContentController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ContentService contentService;

	@GetMapping("/list")
	public ResultVO<List<Content>> get(@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "size", defaultValue = "5") int size) {
		Pageable pageable = PageRequest.of(page - 1, size);
		Page<Content> res = contentService.getContent(pageable);
		return ResultUtils.success(res.getContent());
	}
	
//	@PostMapping("/sent")
//	public ResultVO<T>
}
