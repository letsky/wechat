package cn.letsky.wechat.controller;

import java.util.Arrays;
import java.util.List;

import cn.letsky.wechat.constant.ResultEnum;
import cn.letsky.wechat.exception.CommonException;
import cn.letsky.wechat.form.ArticleForm;
import cn.letsky.wechat.model.Article;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import cn.letsky.wechat.service.ArticleService;
import cn.letsky.wechat.service.UserService;
import cn.letsky.wechat.util.ResultUtils;
import cn.letsky.wechat.viewobject.ArticleVO;
import cn.letsky.wechat.viewobject.ResultVO;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;

    /**
     * 获取文章列表
     *
     * @param page 页数
     * @param size 每页的数量
     * @return 文章列表
     */
    @GetMapping("/list")
    public ResultVO<List<ArticleVO>> getArticleList(@RequestParam(value = "page", defaultValue = "1") int page,
                                                    @RequestParam(value = "size", defaultValue = "5") int size) {
        if (page < 1){
            page = 1;
        }
        Pageable pageable = PageRequest.of(page - 1, size);
        List<ArticleVO> list = articleService.getAllVO(pageable);
        return ResultUtils.success(list);
    }

    /**
     * 获取单个文章
     *
     * @param id 文章id
     * @return 单个文章
     */
    @GetMapping("/{id}")
    public ResultVO<ArticleVO> getArticle(@PathVariable("id") Integer id) {
        return ResultUtils.success(articleService.getOneVO(id));
    }

    /**
     * 发送文章
     *
     * @param articleForm   前端表单
     * @param bindingResult 表单验证的结果
     * @return 操作的状态码
     */
    @PostMapping("/sent")
    public ResultVO sentArticle(@Valid ArticleForm articleForm,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("[发送帖子失败]：articleForm={}", articleForm);
            throw new CommonException(ResultEnum.PARAM_ERROR);
        }
        Article article = new Article();
        article.setOpenid(articleForm.getOpenid());
        article.setContent(articleForm.getContent());
        article.setAllowComment(articleForm.getAllowComment());
        article.setImg(StringUtils.join(articleForm.getImgs(), "#"));
        Article result = articleService.save(article);
        if (result == null) {
            throw new CommonException(ResultEnum.ERROR);
        }
        return ResultUtils.success();
    }

    /**
     * 删除帖子
     *
     * @param id 帖子id
     * @return 操作的状态码
     */
    @PostMapping("/{id}")
    public ResultVO deleteArticle(@PathVariable("id") Integer id) {
        articleService.delete(id);
        return ResultUtils.success();
    }
}
