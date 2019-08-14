package cn.letsky.wechat.service.Impl;

import cn.letsky.wechat.constant.Visible;
import cn.letsky.wechat.constant.status.ArticleStatus;
import cn.letsky.wechat.dao.ArticleDao;
import cn.letsky.wechat.model.Article;
import cn.letsky.wechat.service.ArticleService;
import cn.letsky.wechat.util.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;

/**
 * {@link ArticleService}实现类
 */
@Slf4j
@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleDao articleDao;

    public ArticleServiceImpl(ArticleDao articleDao) {
        this.articleDao = articleDao;
    }

    @Override
    public Article getArticle(Integer id) {
        return articleDao.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Page<Article> getPublicArticles(Integer page, Integer size) {
        return getArticles(Visible.PUBLIC, page, size);
    }

    @Override
    public Page<Article> getArticles(Integer visible, Integer page, Integer size) {
        Pageable pageable = PageUtils.getPageable(page, size);
        return articleDao.findAllByStatusAndVisibleOrderByCreatedDesc(
                ArticleStatus.NORMAL, visible, pageable);
    }

    @Override
    public Page<Article> getUserArticles(String openid, Integer page, Integer size) {
        Pageable pageable = PageUtils.getPageable(page, size);
        return articleDao.findAllByOpenidOrderByCreatedDesc(openid, pageable);
    }

    @Override
    @Transactional
    public Article post(Article article) {
        return articleDao.save(article);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Article article = articleDao.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        article.setStatus(ArticleStatus.DELETE);
        articleDao.save(article);
    }

    @Override
    public Page<Article> getFollowUserArticles(Collection<String> ids, Integer page, Integer size) {
        Pageable pageable = PageUtils.getPageable(page, size);
        return articleDao.findAllByOpenidInAndStatusAndVisibleOrderByCreatedDesc(
                ids, ArticleStatus.NORMAL, Visible.PUBLIC, pageable
        );
    }
}
