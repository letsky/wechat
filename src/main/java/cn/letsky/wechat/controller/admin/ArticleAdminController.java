package cn.letsky.wechat.controller.admin;

import cn.letsky.wechat.constant.EntityType;
import cn.letsky.wechat.controller.ArticleController;
import cn.letsky.wechat.model.Article;
import cn.letsky.wechat.model.User;
import cn.letsky.wechat.service.ArticleService;
import cn.letsky.wechat.service.UserService;
import cn.letsky.wechat.util.ResultUtils;
import cn.letsky.wechat.viewobject.ArticleVO;
import cn.letsky.wechat.viewobject.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/articles")
@CrossOrigin(allowCredentials = "true")
@Slf4j
public class ArticleAdminController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResultVO getList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                            @RequestParam(value = "size", defaultValue = "20") Integer size){
        Page<Article> articlePage = articleService.findAll(page, size);
        Long count = articlePage.getTotalElements();
        Map<String, Long> map = new HashMap<>();
        map.put("count", count);
        List<ArticleVO> list = articlePage.get()
                .map(e -> transform(e, new ArticleVO()))
                .collect(Collectors.toList());

        return ResultUtils.success(list, map);
    }

    private ArticleVO transform(Article article, ArticleVO articleVO){
        BeanUtils.copyProperties(article, articleVO);
        User user = userService.findById(article.getOpenid());
        articleVO.setNickname(user.getNickname());
        articleVO.setAvatarUrl(user.getAvatarUrl());
        articleVO.setImgs(article.getImg().split("#"));
        return articleVO;
    }

}
