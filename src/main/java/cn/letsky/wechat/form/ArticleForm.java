package cn.letsky.wechat.form;

import cn.letsky.wechat.constant.Visible;
import cn.letsky.wechat.constant.status.CommentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleForm implements Form {

    /**
     * 用户openid
     */
    @NotBlank(message = "openid不能为空")
    private String openid;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 是否允许评论，0为允许，1为不允许，默认为0
     * {@link CommentStatus#ALLOW}
     * {@link CommentStatus#NOT_ALLOW}
     */
    private Integer allowComment = CommentStatus.ALLOW;

    /**
     * 可见性，0为允许他人可见，1为自己可见
     */
    private Integer visible = Visible.PUBLIC;

    /**
     * 图片链接数组
     */
    private String[] imgs;
}
