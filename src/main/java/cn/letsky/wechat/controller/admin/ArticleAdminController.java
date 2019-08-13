package cn.letsky.wechat.controller.admin;

import cn.letsky.wechat.model.Article;
import cn.letsky.wechat.model.User;
import cn.letsky.wechat.service.ArticleService;
import cn.letsky.wechat.service.UserService;
import cn.letsky.wechat.util.ResultUtils;
import cn.letsky.wechat.vo.ArticleVO;
import cn.letsky.wechat.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/articles")
@CrossOrigin(allowCredentials = "true")
public class ArticleAdminController {

    private final ArticleService articleService;
    private final UserService userService;

    public ArticleAdminController(ArticleService articleService,
                                  UserService userService) {
        this.articleService = articleService;
        this.userService = userService;
    }

    @GetMapping
    public ResultVO getList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                            @RequestParam(value = "size", defaultValue = "20") Integer size) {
        Page<Article> articlePage = articleService.getArticles(page, size);
        Long count = articlePage.getTotalElements();
        Map<String, Long> map = new HashMap<>();
        map.put("count", count);
        List<ArticleVO> list = articlePage.get()
                .map(e -> transform(e, new ArticleVO()))
                .collect(Collectors.toList());

        return ResultUtils.success(list, map);
    }

    @GetMapping("/{id}")
    public ResultVO get(@PathVariable("id") Integer id) {
        Article article = articleService.getArticle(id);
        return ResultUtils.success(transform(article, new ArticleVO()));
    }

    @DeleteMapping("/{id}")
    public ResultVO delete(@PathVariable("id") Integer id){
        articleService.delete(id);
        return ResultUtils.success();
    }

    private ArticleVO transform(Article article, ArticleVO articleVO) {
        BeanUtils.copyProperties(article, articleVO);
        User user = userService.getUser(article.getOpenid())
                .orElseThrow(NoSuchElementException::new);
        articleVO.setNickname(user.getNickname());
        articleVO.setAvatarUrl(user.getAvatarUrl());
        articleVO.setImgs(article.getImg().split("#"));
        return articleVO;
    }

}
