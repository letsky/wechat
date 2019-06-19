package cn.letsky.wechat.controller;

import java.util.List;
import java.util.stream.Collectors;

import cn.letsky.wechat.constant.EntityType;
import cn.letsky.wechat.constant.ResultEnum;
import cn.letsky.wechat.exception.CommonException;
import cn.letsky.wechat.form.ArticleForm;
import cn.letsky.wechat.model.Article;
import cn.letsky.wechat.model.UserHolder;
import cn.letsky.wechat.model.User;
import cn.letsky.wechat.service.CommentService;
import cn.letsky.wechat.service.LikeService;
import cn.letsky.wechat.util.FilterUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import cn.letsky.wechat.service.ArticleService;
import cn.letsky.wechat.service.UserService;
import cn.letsky.wechat.util.ResultUtils;
import cn.letsky.wechat.viewobject.ArticleVO;
import cn.letsky.wechat.viewobject.ResultVO;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/articles")
public class ArticleController {

    private final UserService userService;
    private final ArticleService articleService;
    private final FilterUtils filterUtils;
    private final CommentService commentService;
    private final LikeService likeService;
    private final UserHolder userHolder;

    public ArticleController(UserService userService,
                             ArticleService articleService,
                             FilterUtils filterUtils,
                             CommentService commentService,
                             LikeService likeService,
                             UserHolder userHolder) {
        this.userService = userService;
        this.articleService = articleService;
        this.filterUtils = filterUtils;
        this.commentService = commentService;
        this.likeService = likeService;
        this.userHolder = userHolder;
    }

    @GetMapping
    public ResultVO<List<ArticleVO>> getArticleList(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "20") int size) {

        Page<Article> articlePage = articleService.findAll(page, size);
        List<ArticleVO> list = articlePage.get()
                .map(e -> transform(e, new ArticleVO(), userHolder.get().getOpenid()))
                .collect(Collectors.toList());
        return ResultUtils.success(list);
    }

    @GetMapping("/user/{openid}")
    public ResultVO<List<ArticleVO>> getArticleListByOpenid(
            @PathVariable("openid") String openid,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "20") int size) {

        Page<Article> articlePage = articleService.findAllByOpenid(openid, page, size);
        List<ArticleVO> list = articlePage.get()
                .map(e -> transform(e, new ArticleVO(), openid))
                .collect(Collectors.toList());
        return ResultUtils.success(list);
    }

    @GetMapping("/{id}")
    public ResultVO<ArticleVO> getArticle(@PathVariable("id") Integer id) {

        String openid = userHolder.get().getOpenid();
        Article article = articleService.findById(id);
        if (article == null)
            throw new CommonException(ResultEnum.ENTITY_NOT_FOUNT);
        ArticleVO articleVO = new ArticleVO();
        transform(article, articleVO, openid);
        return ResultUtils.success(articleVO);
    }

    @PostMapping
    public ResultVO sendArticle(@Valid ArticleForm articleForm,
                                BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.error("[保存帖子失败]：articleForm={}", articleForm);
            throw new CommonException(ResultEnum.PARAM_ERROR);
        }
        if (filterUtils.isSensitive(articleForm.getContent())) {
            throw new CommonException(ResultEnum.SENSITIVE_WORD);
        }
        Article article = new Article();
        BeanUtils.copyProperties(articleForm, article);
        article.setOpenid(userHolder.get().getOpenid());
        article.setImg(StringUtils.join(articleForm.getImgs(), "#"));
        Article result = articleService.save(article);
        if (result == null) {
            throw new CommonException(ResultEnum.ERROR);
        }
        return ResultUtils.success();
    }

    @DeleteMapping
    public ResultVO deleteArticle(@RequestParam("id") Integer id) {
        articleService.delete(id);
        return ResultUtils.success();
    }

    /**
     * 将Article对象转换成ArticleVO对象
     *
     * @param article
     * @param articleVO
     * @return ArticleVO对象
     */
    private ArticleVO transform(Article article, ArticleVO articleVO, String openid) {

        BeanUtils.copyProperties(article, articleVO);
        if (article.getImg() != null && article.getImg().length() != 0)
            articleVO.setImgs(article.getImg().split("#"));
        User user = userService.findById(article.getOpenid());
        if (user != null) {
            articleVO.setAvatarUrl(user.getAvatarUrl());
            articleVO.setNickname(user.getNickname());
        }
        Long commentNum = commentService
                .count(EntityType.ARTICLE.getCode(), article.getId());
        Integer liked = likeService.getLikeStatus(openid,
                EntityType.ARTICLE.getCode(), article.getId());
        Long likeNum = likeService.likeCount(EntityType.ARTICLE.getCode(), article.getId());
        articleVO.setCommentNum(commentNum);
        articleVO.setLiked(liked);
        articleVO.setLikeNum(likeNum);
        return articleVO;
    }
}
