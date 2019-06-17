package cn.letsky.wechat.service.Impl;

import cn.letsky.wechat.constant.EntityType;
import cn.letsky.wechat.constant.ResultEnum;
import cn.letsky.wechat.constant.StatusEnum;
import cn.letsky.wechat.dao.ArticleDao;
import cn.letsky.wechat.exception.CommonException;
import cn.letsky.wechat.model.Article;
import cn.letsky.wechat.model.Trie;
import cn.letsky.wechat.model.User;
import cn.letsky.wechat.service.CommentService;
import cn.letsky.wechat.service.LikeService;
import cn.letsky.wechat.service.UserService;
import cn.letsky.wechat.util.PageUtils;
import cn.letsky.wechat.viewobject.ArticleVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import cn.letsky.wechat.service.ArticleService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ArticleServiceImpl implements ArticleService {

    private final String SPILT = "#";

    private final ArticleDao articleDao;

    private final UserService userService;

    private final CommentService commentService;

    private final LikeService likeService;

    public ArticleServiceImpl(ArticleDao articleDao,
                              UserService userService,
                              CommentService commentService,
                              LikeService likeService) {
        this.articleDao = articleDao;
        this.userService = userService;
        this.commentService = commentService;
        this.likeService = likeService;
    }

    @Override
    public Article findById(Integer id) {
        Optional<Article> article = articleDao.findById(id);
        return article.orElse(null);
    }

    @Override
    public ArticleVO findByIdVO(Integer id, String openid){
        Article article = findById(id);
        if (article == null)
            return null;
        ArticleVO articleVO = new ArticleVO();
        return transform(article, articleVO, openid);
    }

    @Override
    public Page<Article> findAll(Integer page, Integer size) {
        Pageable pageable = PageUtils.getPageable(page, size);
        Page<Article> articlePage = articleDao.findAllByStatusOrderByCreatedDesc(
                StatusEnum.ARTICLE_NORMAL.getCode(), pageable);
        if (page > articlePage.getTotalPages()){
            throw new CommonException(ResultEnum.BEYOND_PAGE_LIMIT);
        }
        return articlePage;
    }

    @Override
    public List<ArticleVO> findAllVO(String openid, Integer page, Integer size) {
        Page<Article> articlePage = findAll(page, size);
        List<ArticleVO> list = articlePage.getContent().stream()
                .map(e -> transform(e, new ArticleVO(), openid))
                .collect(Collectors.toList());
        return list;
    }

    @Override
    public List<ArticleVO> findAllVOByOpenid(String openid, Integer page, Integer size) {
        Pageable pageable = PageUtils.getPageable(page, size);
        Page<Article> articlePage = articleDao.findAllByOpenidOrderByCreatedDesc(openid, pageable);
        if (page > articlePage.getTotalPages()){
            throw new CommonException(ResultEnum.BEYOND_PAGE_LIMIT);
        }

        List<ArticleVO> list = articlePage.getContent()
                .stream().map(e -> transform(e, new ArticleVO(), openid))
                .collect(Collectors.toList());
        return list;
    }

    @Override
    public Article save(Article article) {
        return articleDao.save(article);
    }

    @Override
    public void delete(Integer id) {
        Article article = articleDao.getOne(id);
        article.setStatus(StatusEnum.ARTICLE_DELETE.getCode());
        articleDao.save(article);
    }



    /**
     * 将Article对象转换成ArticleVO对象
     * @param article
     * @param articleVO
     * @return ArticleVO对象
     */
    private ArticleVO transform(Article article, ArticleVO articleVO, String openid){

        BeanUtils.copyProperties(article, articleVO);
        if (article.getImg() != null && article.getImg().length() != 0)
            articleVO.setImgs(article.getImg().split(SPILT));
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
