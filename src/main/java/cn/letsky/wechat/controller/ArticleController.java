package cn.letsky.wechat.controller;

import cn.letsky.wechat.constant.EntityType;
import cn.letsky.wechat.constant.ResultEnum;
import cn.letsky.wechat.constant.status.LikeStatus;
import cn.letsky.wechat.constant.status.UserStatus;
import cn.letsky.wechat.domain.form.ArticleForm;
import cn.letsky.wechat.domain.model.Article;
import cn.letsky.wechat.domain.model.User;
import cn.letsky.wechat.domain.vo.ArticleVO;
import cn.letsky.wechat.exception.CommonException;
import cn.letsky.wechat.service.*;
import cn.letsky.wechat.util.FilterUtils;
import cn.letsky.wechat.util.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/articles")
public class ArticleController {

    private final UserService userService;
    private final ArticleService articleService;
    private final FilterUtils filterUtils;
    private final CommentService commentService;
    private final LikeService likeService;
    private final FollowService followService;

    public ArticleController(UserService userService, ArticleService articleService,
                             FilterUtils filterUtils, CommentService commentService,
                             LikeService likeService, FollowService followService) {
        this.userService = userService;
        this.articleService = articleService;
        this.filterUtils = filterUtils;
        this.commentService = commentService;
        this.likeService = likeService;
        this.followService = followService;
    }

    //未登录展示的文章
    @GetMapping
    public ResponseEntity<List<ArticleVO>> getArticleList(Pageable pageable) {
        Page<Article> articlePage = articleService.getPublicArticles(pageable);
        List<ArticleVO> list = articlePage.stream()
                .map(e -> transform(e, null))
                .collect(Collectors.toList());
        return ResultUtils.ok(list);
    }

    //登录后展示的文章
    @GetMapping(params = {"openid"})
    public ResponseEntity getArticleList(
            @RequestParam(value = "openid") String openid, Pageable pageable) {
        Page<Article> publicArticles = articleService.getPublicArticles(pageable);
        List<ArticleVO> collect = publicArticles.stream()
                .map(e -> transform(e, openid))
                .collect(Collectors.toList());
        return ResultUtils.ok(collect);
    }

    //获取关注用户的文章
    @GetMapping("/following")
    public ResponseEntity<List<ArticleVO>> getFollowedArticleList(
            @RequestParam(value = "openid") String openid,
            @PageableDefault(size = 20) Pageable pageable) {
        Set<String> ids = followService.getFollowing(openid);
        Page<Article> articlePage = articleService.getFollowingArticles(ids, pageable);
        List<ArticleVO> list = articlePage.stream()
                .map(e -> transform(e, openid))
                .collect(Collectors.toList());
        return ResultUtils.ok(list);
    }

    //获取当前用户所有发表的文章
    @GetMapping("/users/{openid}")
    public ResponseEntity<List<ArticleVO>> getArticleListByOpenid(
            @PathVariable("openid") String openid,
            @PageableDefault(size = 20) Pageable pageable) {

        Page<Article> articlePage = articleService.getUserArticles(openid, pageable);
        List<ArticleVO> list = articlePage.get()
                .map(e -> transform(e, openid))
                .collect(Collectors.toList());
        return ResultUtils.ok(list);
    }

    //根据文章id获取文章
    @GetMapping("/{id}")
    public ResponseEntity<ArticleVO> getArticle(@PathVariable("id") Integer id) {
        Article article = articleService.getArticle(id);
        return ResultUtils.ok(transform(article, null));
    }

    //发表文章
    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Article> postArticle(
            @RequestBody @Valid ArticleForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("[保存文章失败]：articleForm={}", form);
            throw new CommonException(ResultEnum.PARAM_ERROR);
        }
        if (filterUtils.isSensitive(form.getContent())) {
            throw new CommonException(ResultEnum.SENSITIVE_WORD);
        }
        Article article = new Article();
        BeanUtils.copyProperties(form, article);
        article.setImg(StringUtils.join(form.getImgs(), "#"));
        articleService.post(article);
        return ResultUtils.ok();
    }

    //删除文章
    @DeleteMapping("/{id}")
    public ResponseEntity deleteArticle(@PathVariable("id") Integer id) {
        articleService.delete(id);
        return ResultUtils.ok();
    }

    /**
     * 将Article对象转换成ArticleVO对象
     *
     * @param article
     * @param openid
     * @return ArticleVO对象
     */
    private ArticleVO transform(Article article, String openid) {
        ArticleVO articleVO = new ArticleVO();
        BeanUtils.copyProperties(article, articleVO);
        //还原图片链接
        if (!StringUtils.isEmpty(article.getImg()))
            articleVO.setImgs(article.getImg().split("#"));

        //拼接用户信息
        User articleUser = userService.getUser(article.getOpenid())
                .orElseThrow(EntityNotFoundException::new);
        String articleUserOpenid = articleUser.getOpenid();
        articleVO.setAvatarUrl(articleUser.getAvatarUrl());
        articleVO.setNickname(articleUser.getNickname());

        //评论数
        Long commentNum = commentService
                .getCount(EntityType.ARTICLE, article.getId());

        //点赞数
        Long likeNum = likeService.getCount(EntityType.ARTICLE, article.getId());

        //设置默认值
        Integer liked = LikeStatus.UNLIKE;
        Integer followed = UserStatus.UNFOLLOW;
        boolean showFollowButton = true;

        if (!StringUtils.isBlank(openid)) {
            //是否点赞
            liked = likeService.getLikeStatus(openid, EntityType.ARTICLE, article.getId());
            //是否关注
            followed = (followService.isFollowing(openid, articleUserOpenid)) ? UserStatus.FOLLOWING : UserStatus.UNFOLLOW;
            //是否显示关注按钮
            if (openid.equals(articleUserOpenid)) {
                showFollowButton = false;
            }
        }

        articleVO.setCommentNum(commentNum);
        articleVO.setLikeNum(likeNum);
        articleVO.setLiked(liked);
        articleVO.setFollowed(followed);
        articleVO.setShowFollowButton(showFollowButton);
        return articleVO;
    }
}
