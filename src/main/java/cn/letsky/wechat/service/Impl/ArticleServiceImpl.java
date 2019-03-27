package cn.letsky.wechat.service.Impl;

import cn.letsky.wechat.constant.StatusEnum;
import cn.letsky.wechat.dao.ArticleDao;
import cn.letsky.wechat.model.Article;
import cn.letsky.wechat.model.User;
import cn.letsky.wechat.service.UserService;
import cn.letsky.wechat.viewobject.ArticleVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import cn.letsky.wechat.service.ArticleService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ArticleServiceImpl implements ArticleService {

    private final String SPILT = "#";

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private UserService userService;

    @Override
    public Article findById(Integer id) {
        Optional<Article> article = articleDao.findById(id);
        return article.orElse(null);
    }

    @Override
    public ArticleVO findByIdVO(Integer id){
        Article article = findById(id);
        if (article == null)
            return null;
        ArticleVO articleVO = new ArticleVO();
        return transform(article, articleVO);
    }

    @Override
    public Page<Article> findAll(Pageable pageable) {
        return articleDao.findAllByStatus(StatusEnum.ARTITLE_NORMAL.getCode(), pageable);
    }

    @Override
    public List<ArticleVO> findAllVO(Pageable pageable) {
        Page<Article> articlePage = findAll(pageable);
        List<ArticleVO> list = new ArrayList<>();
        for (Article article : articlePage.getContent()) {
            ArticleVO articleVO = new ArticleVO();
            list.add(transform(article, articleVO));
        }
        return list;
    }


    @Override
    public Article save(Article article) {
        return articleDao.save(article);
    }

    @Override
    public void delete(Integer id) {
        Article article = articleDao.getOne(id);
        article.setStatus(StatusEnum.ARTITLE_DELETE.getCode());
        articleDao.save(article);
    }


    /**
     * 将Article对象转换成ArticleVO对象
     * @param article
     * @param articleVO
     * @return ArticleVO对象
     */
    private ArticleVO transform(Article article, ArticleVO articleVO){
        BeanUtils.copyProperties(article, articleVO);
        if (article.getImg() != null && article.getImg().length() != 0)
            articleVO.setImgs(article.getImg().split(SPILT));
        User user = userService.findById(article.getOpenid());
        if (user != null) {
            articleVO.setAvatarUrl(user.getAvatarUrl());
            articleVO.setNickname(user.getNickname());
        }
        return articleVO;
    }
}
