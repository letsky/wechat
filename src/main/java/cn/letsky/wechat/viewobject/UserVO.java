package cn.letsky.wechat.viewobject;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
public class UserVO {

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
