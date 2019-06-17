package cn.letsky.wechat.dao;

import cn.letsky.wechat.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleDao extends JpaRepository<Article, Integer> {

    /**
     * 根据状态查询所有的文章(按发表时间降序)
     *
     * @param status   状态
     * @param visible  可见性
     * @param pageable 分页参数
     * @return 符合条件的content
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
}
