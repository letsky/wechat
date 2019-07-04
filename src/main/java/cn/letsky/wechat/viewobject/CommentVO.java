package cn.letsky.wechat.viewobject;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class CommentVO implements Serializable {

    private static final long serialVersionUID = 3829376821426089909L;

    /**
     * 评论主键
     */
    private Integer id;

    /**
     * 评论用户id
     */
    private String uid;

    /**
     * 评论者名字
     */
    private String nickname;

    /**
     * 评论者头像
     */
    private String avatarUrl;

    /**
     * 评论类型。
     */
    private Integer entityType;

    /**
     * 评论的资源id
     */
    private Integer entityId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论时间
     */
    private Date created;

    private List<CommentVO> children;
}
