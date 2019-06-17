package cn.letsky.wechat.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class  ArticleForm implements Form {

    /**
     * 文章内容
     */
    private String content;

    /**
     * 发表人的openid
     */
    @NotBlank
    private String openid;

    /**
     * 是否允许评论，0为允许，1为不允许，默认为0
     */
    private Integer allowComment;

    /**
     * 可见性，0为允许他人可见，1为自己可见
     */
    private Integer visible;

    /**
     * 图片链接数组
     */
    private String[] imgs;

}
