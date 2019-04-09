package cn.letsky.wechat.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class  ArticleForm implements Form {

    private String content;

    @NotBlank
    private String openid;

    private String tag;

    private Integer allowComment;

    private String[] imgs;

    public ArticleForm() {
        this.allowComment = 0;
    }
}
