package cn.letsky.wechat.controller.admin;

import cn.letsky.wechat.domain.model.Article;
import cn.letsky.wechat.domain.model.User;
import cn.letsky.wechat.domain.vo.ArticleVO;
import cn.letsky.wechat.domain.vo.ResultVO;
import cn.letsky.wechat.service.ArticleService;
import cn.letsky.wechat.service.UserService;
import cn.letsky.wechat.util.ResultUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public ResultVO getList(@PageableDefault(size = 20) Pageable pageable) {
        Page<Article> articlePage = articleService.getPublicArticles(pageable);
        Long count = articlePage.getTotalElements();
        Map<String, Long> map = new HashMap<>();
        map.put("count", count);
        List<ArticleVO> list = articlePage.get()
                .map(e -> transform(e, new ArticleVO()))
                .collect(Collectors.toList());

        return ResultUtils.success(list, map);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleVO> get(@PathVariable("id") Integer id) {
        Article article = articleService.getArticle(id);
        return ResultUtils.ok(transform(article, new ArticleVO()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Integer id) {
        articleService.delete(id);
        return ResultUtils.ok();
    }

    private ArticleVO transform(Article article, ArticleVO articleVO) {
        BeanUtils.copyProperties(article, articleVO);
        User user = userService.getUser(article.getOpenid())
                .orElseThrow(EntityNotFoundException::new);
        articleVO.setNickname(user.getNickname());
        articleVO.setAvatarUrl(user.getAvatarUrl());
        articleVO.setImgs(article.getImg().split("#"));
        return articleVO;
    }

}
