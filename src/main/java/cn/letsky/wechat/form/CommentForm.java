package cn.letsky.wechat.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CommentForm implements Form {

    @NotNull
    private String uid;

    @NotNull
    private String content;

    private Integer entityType;

    private Integer entityId;
}
