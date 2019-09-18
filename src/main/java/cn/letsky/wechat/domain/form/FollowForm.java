package cn.letsky.wechat.domain.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowForm {

    private String fromUser;

    private String toUser;
}
