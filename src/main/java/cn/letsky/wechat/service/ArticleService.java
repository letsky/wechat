package cn.letsky.wechat.service;

import cn.letsky.wechat.constant.status.CommentStatus;
import cn.letsky.wechat.domain.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

public interface ArticleService {

    /**
     * 获取单条文章
     *
     * @param id 文章的id
     * @return 文章的内容
     */
    Article getArticle(Integer id);

    /**
     * 获取文章列表
     *
     * @param pageable
     * @return 可见性为public的文章列表
     * <p>
     * {@link CommentStatus#ALLOW}
     */
    Page<Article> getPublicArticles(Pageable pageable);

    /**
     * 获取文章列表
     *
     * @param visible 文章可见性
     * @param pageable
     * @return 给定文章可见性的文章列表
     * <p>
     * {@link CommentStatus#ALLOW}
     * {@link CommentStatus#NOT_ALLOW}
     */
    Page<Article> getArticles(Integer visible, Pageable pageable);

    /**
     * 返回给定用户所有已发送的文章
     *
     * @param openid 微信用户id
     * @param pageable
     * @return 给定用户的文章列表
     */
    Page<Article> getUserArticles(String openid, Pageable pageable);

    /**
     * 发表文章
     *
     * @param article 文章实体
     * @return 保存的文章对象
     */
    Article post(Article article);

    /**
     * 删除文章，非真实删除
     *
     * @param id 文章id
     */
    void delete(Integer id);

    /**
     * 查询关注的人发表的文章
     *
     * @param ids  关注人的集合
     * @param pageable
     * @return
     */
    Page<Article> getFollowingArticles(Collection<String> ids, Pageable pageable);
}
