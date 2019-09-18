package cn.letsky.wechat.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
public class UserVO implements Serializable {

    private static final long serialVersionUID = 8667616947001969L;

    /**
     * 微信用户唯一标识
     */
    private String openid;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 性别 0：未知、1：男、2：女
     */
    private Integer gender;

    /**
     * 头像
     */
    private String avatarUrl;

    /**
     * 邮箱地址
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String email;

    /**
     * 注册时间
     */
    private Date created;
}
