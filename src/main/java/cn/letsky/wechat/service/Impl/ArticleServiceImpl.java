package cn.letsky.wechat.service.Impl;

import cn.letsky.wechat.constant.ResultEnum;
import cn.letsky.wechat.constant.Visible;
import cn.letsky.wechat.constant.status.ArticleStatus;
import cn.letsky.wechat.dao.ArticleDao;
import cn.letsky.wechat.exception.CommonException;
import cn.letsky.wechat.model.Article;
import cn.letsky.wechat.service.ArticleService;
import cn.letsky.wechat.util.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

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
    public Article findById(Integer id) {
        Optional<Article> article = articleDao.findById(id);
        return article.orElse(null);
    }

    @Override
    public Page<Article> findAll(Integer page, Integer size) {
        return findAll(Visible.PUBLIC, page, size);
    }

    @Override
    public Page<Article> findAll(Integer visible, Integer page, Integer size) {
        Pageable pageable = PageUtils.getPageable(page, size);
        Page<Article> articlePage = articleDao.findAllByStatusAndVisibleOrderByCreatedDesc(
                ArticleStatus.NORMAL, visible, pageable);
        if (page > articlePage.getTotalPages()) {
            throw new CommonException(ResultEnum.BEYOND_PAGE_LIMIT);
        }
        return articlePage;
    }

    @Override
    public Page<Article> findAllByOpenid(String openid, Integer page, Integer size) {
        Pageable pageable = PageUtils.getPageable(page, size);
        Page<Article> articlePage = articleDao.findAllByOpenidOrderByCreatedDesc(openid, pageable);
        if (page > articlePage.getTotalPages()) {
            throw new CommonException(ResultEnum.BEYOND_PAGE_LIMIT);
        }
        return articlePage;
    }

    @Override
    public Article save(Article article) {
        return articleDao.save(article);
    }

    @Override
    public void delete(Integer id) {
        Article article = articleDao.getOne(id);
        article.setStatus(ArticleStatus.DELETE);
        articleDao.save(article);
    }

    @Override
    public Page<Article> findAllFollowed(Collection<String> ids, Integer page, Integer size) {
        Pageable pageable = PageUtils.getPageable(page, size);
        Page<Article> articlePage = articleDao.findAllByOpenidInAndStatusAndVisibleOrderByCreatedDesc(
                ids, ArticleStatus.NORMAL, Visible.PUBLIC, pageable
        );
        return articlePage;
    }
}
