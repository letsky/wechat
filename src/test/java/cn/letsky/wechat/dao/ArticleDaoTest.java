package cn.letsky.wechat.dao;

import cn.letsky.wechat.constant.Visible;
import cn.letsky.wechat.constant.status.ArticleStatus;
import cn.letsky.wechat.model.Article;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleDaoTest {

	@Autowired
	private ArticleDao articleDao;

	@Test
	public void findAllByStatusAndVisibleOrderByCreatedDesc() {
	}

	@Test
	public void findAllByOpenidOrderByCreatedDesc() {
	}

	//测试查询所有关注用户的文章
	@Test
	public void findAllByOpenidInAndStatusAndVisibleOrderByCreatedDesc() {
		Set<String> set = new HashSet<>();
		set.add("oNSbI5cntqiXPrUMDk4StM2VSu58");
		set.add("oNSbI5XkGJPe6TQ2GSTj7O8zLPVo");
		set.add("oNSbI5XYNzS2TsuQp47ZuJeJbj9g");
		Pageable pageable = PageRequest.of(0, 20);
		Page<Article> articlePage =  articleDao.findAllByOpenidInAndStatusAndVisibleOrderByCreatedDesc(
				set, ArticleStatus.NORMAL, Visible.PUBLIC, pageable
		);
		articlePage.get().forEach(System.out::println);
	}
}
