package cn.letsky.wechat.controller;

import java.util.List;

import cn.letsky.wechat.constant.ResultEnum;
import cn.letsky.wechat.exception.CommonException;
import cn.letsky.wechat.form.ArticleForm;
import cn.letsky.wechat.model.Article;
import cn.letsky.wechat.model.Trie;
import cn.letsky.wechat.model.User;
import cn.letsky.wechat.service.CommentService;
import cn.letsky.wechat.util.FilterUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private CommentService commentService;

    @Autowired
    private FilterUtils filterUtils;

    /**
     * 获取文章列表
     *
     * @param page 页数
     * @param size 每页的数量
     * @return 文章列表
     */
    @GetMapping("/list")
    public ResultVO<List<ArticleVO>> getArticleList(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "5") int size) {

        List<ArticleVO> list = articleService.findAllVO(page, size);
        return ResultUtils.success(list);
    }

    @GetMapping(value = "/list", params = {"openid"})
    public ResultVO<List<ArticleVO>> getArticleListByOpenid(
            @RequestParam("openid") String openid,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "5") int size) {

        List<ArticleVO> list = articleService.findAllVOByOpenid(openid, page, size);
        return ResultUtils.success(list);
    }

    /**
     * 获取单个文章
     *
     * @param id 文章id
     * @return 单个文章
     */
    @GetMapping()
    public ResultVO<ArticleVO> getArticle(@RequestParam("id") Integer id) {

        ArticleVO articleVO = articleService.findByIdVO(id);
        if (articleVO == null)
            throw new CommonException(ResultEnum.ENTITY_NOT_FOUNT);
        return ResultUtils.success(articleVO);
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
        User user = userService.findById(articleForm.getOpenid());
        if (user == null){
            throw new CommonException(ResultEnum.NOT_REGISTER);
        }
        if (filterUtils.isSensitive(articleForm.getContent())) {
            throw new CommonException(ResultEnum.SENSITIVE_WORD);
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
    @PostMapping()
    public ResultVO deleteArticle(@RequestParam("id") Integer id) {
        articleService.delete(id);
        return ResultUtils.success();
    }
}
