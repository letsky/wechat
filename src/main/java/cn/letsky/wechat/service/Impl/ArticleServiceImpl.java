package cn.letsky.wechat.service.Impl;

import cn.letsky.wechat.constant.Visible;
import cn.letsky.wechat.constant.status.ArticleStatus;
import cn.letsky.wechat.domain.model.Article;
import cn.letsky.wechat.repository.ArticleRepository;
import cn.letsky.wechat.service.ArticleService;
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

    private final ArticleRepository articleRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public Article getArticle(Integer id) {
        return articleRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Page<Article> getPublicArticles(Pageable pageable) {
        return getArticles(Visible.PUBLIC, pageable);
    }

    @Override
    public Page<Article> getArticles(Integer visible, Pageable pageable) {
        return articleRepository.findAllByStatusAndVisibleOrderByCreatedDesc(
                ArticleStatus.NORMAL, visible, pageable);
    }

    @Override
    public Page<Article> getUserArticles(String openid, Pageable pageable) {
        return articleRepository.findAllByOpenidOrderByCreatedDesc(openid, pageable);
    }

    @Override
    @Transactional
    public Article post(Article article) {
        return articleRepository.save(article);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        article.setStatus(ArticleStatus.DELETE);
        articleRepository.save(article);
    }

    @Override
    public Page<Article> getFollowingArticles(Collection<String> ids, Pageable pageable) {
        return articleRepository.findAllByOpenidInAndStatusAndVisibleOrderByCreatedDesc(
                ids, ArticleStatus.NORMAL, Visible.PUBLIC, pageable
        );
    }
}
