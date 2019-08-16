package cn.letsky.wechat.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WxUserForm implements Form {

    @NotEmpty(message = "openid为null")
    private String openid;

    @NotEmpty(message = "昵称为空")
    private String nickname;

    private Integer gender;

    @NotEmpty(message = "头像为空")
    private String avatarUrl;

}
