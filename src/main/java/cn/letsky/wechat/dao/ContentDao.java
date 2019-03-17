package cn.letsky.wechat.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cn.letsky.wechat.model.Content;

@Repository
public interface ContentDao extends JpaRepository<Content, Integer> {

	/**
	 * 根据状态查询所有的content
	 * @param status 状态
	 * @param pageable 分页参数
	 * @return 符合条件的content
	 */
	 Page<Content> findAllByStatus(int status, Pageable pageable);
}
