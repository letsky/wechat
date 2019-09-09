package cn.letsky.wechat.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LikeForm {

    @NotBlank(message = "openid不能为空")
    private String openid;

    private Integer entityType;

    private Integer entityId;
}
