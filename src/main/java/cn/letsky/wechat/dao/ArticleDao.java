package cn.letsky.wechat.dao;

import cn.letsky.wechat.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleDao extends JpaRepository<Article, Integer> {

	/**
	 * 根据状态查询所有的content
	 * @param status 状态
	 * @param pageable 分页参数
	 * @return 符合条件的content
	 */
	 Page<Article> findAllByStatus(int status, Pageable pageable);


}
