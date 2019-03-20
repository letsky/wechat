package cn.letsky.wechat.service.Impl;

import cn.letsky.wechat.constant.ResultEnum;
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

@Slf4j
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private UserService userService;

    @Override
    public Article getOne(Integer id) {
        return articleDao.getOne(id);
    }

    @Override
    public Page<Article> getAll(Pageable pageable) {
        return articleDao.findAllByStatus(0, pageable);
    }

    @Override
    public List<ArticleVO> getAllVO(Pageable pageable) {
        Page<Article> articlePage = articleDao.findAllByStatus(0, pageable);
        List<ArticleVO> list = new ArrayList<>();
        for (Article article : articlePage.getContent()) {
            ArticleVO articleVO = new ArticleVO();
            BeanUtils.copyProperties(article, articleVO);
            if (article.getImgs() != null)
                articleVO.setImgs(article.getImgs().split("#"));
            User user = userService.getUser(article.getOpenid()).orElse(null);
            if (user != null) {
                articleVO.setAvatarUrl(user.getAvatarUrl());
                articleVO.setNickname(user.getNickname());
            }
            list.add(articleVO);
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
        article.setStatus(ResultEnum.DELETE.getCode());
        articleDao.save(article);
    }


}
