package cn.letsky.wechat.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;

@Data
public class ArticleForm implements Form {

    @NotEmpty(message = "你还没有填写内容哦")
    private String content;

    @NotEmpty
    private String openid;

    private String tag;

    private Integer allowComment;

    private String[] imgs;

    public ArticleForm() {
        this.allowComment = 0;
    }
}
