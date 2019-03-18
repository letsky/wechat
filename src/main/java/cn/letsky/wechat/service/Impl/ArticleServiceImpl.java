package cn.letsky.wechat.service.Impl;

import cn.letsky.wechat.constant.ResultEnum;
import cn.letsky.wechat.dao.ArticleDao;
import cn.letsky.wechat.model.Article;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import cn.letsky.wechat.service.ArticleService;

@Slf4j
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Override
    public Article getOne(Integer id) {
        return articleDao.getOne(id);
    }

    @Override
    public Page<Article> getAll(Pageable pageable) {
        return articleDao.findAllByStatus(0, pageable);
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
