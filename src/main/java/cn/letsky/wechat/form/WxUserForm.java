package cn.letsky.wechat.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WxUserForm implements Form {

    @NotNull(message = "openid为null")
    private String openid;

    @NotEmpty(message = "昵称为空")
    private String nickname;

    private Integer gender;

    @NotEmpty(message = "头像为空")
    private String avatarUrl;

}
