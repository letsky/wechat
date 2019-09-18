package cn.letsky.wechat.repository;

import cn.letsky.wechat.constant.Visible;
import cn.letsky.wechat.constant.status.ArticleStatus;
import cn.letsky.wechat.domain.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {

    /**
     * 根据状态查询所有的文章(按发表时间降序)
     *
     * @param status   状态
     * @param visible  可见性
     * @param pageable 分页参数
     * @return 符合条件的content
     * {@link Visible
     */
    Page<Article> findAllByStatusAndVisibleOrderByCreatedDesc(Integer status, Integer visible, Pageable pageable);

    /**
     * 查询用户发表的文章(按发表时间降序)
     *
     * @param openid   微信用户唯一标识
     * @param pageable 分页参数
     * @return
     */
    Page<Article> findAllByOpenidOrderByCreatedDesc(String openid, Pageable pageable);

    /**
     * 查询关注用户发表的文章
     *
     * @param ids 关注用户集合
     * @param status 文章状态
     * @param visible 文章可见性
     * @param pageable 分页参数
     * @return
     * {@link ArticleStatus}
     * {@link Visible}
     */
    Page<Article> findAllByOpenidInAndStatusAndVisibleOrderByCreatedDesc(
            Collection<String> ids, Integer status,
            Integer visible, Pageable pageable
    );
}
