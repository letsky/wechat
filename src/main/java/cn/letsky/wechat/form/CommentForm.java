package cn.letsky.wechat.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentForm implements Form {

    @NotNull
    private String uid;

    @NotNull
    private String content;

    private Integer entityType;

    private Integer entityId;
}
