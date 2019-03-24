package cn.letsky.wechat.viewobject;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class CommentVO implements Serializable {
    private static final long serialVersionUID = 3829376821426089909L;

    /**
     * 评论主键
     */
    private Integer id;

    /**
     * 该条评论的父评论id
     */
    private Integer pid;

    /**
     * 评论的资源id。标记这条评论是属于哪个资源的。
     * 资源可以是人、项目、设计资源
     */
    private Integer ownerId;

    /**
     * 评论类型。1用户评论，2项目评论，3资源评论
     */
    private Integer type;

    /**
     * 评论者id
     */
    private String fromId;

    /**
     * 评论者名字
     */
    private String fromName;

    /**
     * 评论者头像
     */
    private String fromAvatar;

    /**
     * 被评论者id
     */
    private String toId;

    /**
     * 被评论者名字
     */
    private String toName;

    /**
     * 被评论者头像
     */
    private String toAvatar;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 创建时间
     */
    private Date created;

    private List<CommentVO> children;
}
