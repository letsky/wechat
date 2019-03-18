package cn.letsky.wechat.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.letsky.wechat.model.Article;

public interface ArticleService {

    /**
     * 获取单条文章的内容
     *
     * @param id 文章的id
     * @return 文章的内容
     */
    Article getOne(Integer id);

    /**
     * 获取正常状态的content
     *
     * @param pageable 分页请求
     * @return 分页后正常状态的文章列表
     */
    Page<Article> getAll(Pageable pageable);

    /**
     * 保存文章
     * @param article 文章实体
     * @return 保存后的文章对象
     */
    Article save(Article article);

    /**
     * 将文章状态改为1
     * @param id 文章id
     */
    void delete(Integer id);
}
