package cn.letsky.wechat.domain.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentForm {

    @NotBlank(message = "openid不能为空")
    private String openid;

    @NotBlank(message = "评论不能为空")
    private String content;

    private Integer entityType;

    private Integer entityId;
}
