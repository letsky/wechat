package cn.letsky.wechat.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
public class Comment implements Serializable {


    private static final long serialVersionUID = -1712532973061005548L;

    /**
     * 评论主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 该条评论的父评论id
     */
    private Integer pid;

    /**
     * 评论的资源id。标记这条评论是属于哪个资源的。
     * 资源可以是人、项目、设计资源
     */
    @Column(name = "owner_id")
    private Integer ownerId;

    /**
     * 评论类型。1用户评论，2项目评论，3资源评论
     */
    private Integer type;

    /**
     * 评论者id
     */
    @Column(name = "from_id")
    private String fromId;

    /**
     * 被评论者id
     */
    @Column(name = "to_id")
    private String toId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 创建时间
     */
    private Date created;
}
