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
     * 评论用户id
     */
    private String uid;

    /**
     * 评论的资源id
     */
    @Column(name = "entity_id")
    private Integer entityId;

    /**
     * 评论类型
     * 0为评论帖子
     */
    @Column(name = "entity_type")
    private Integer entityType;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 创建时间
     */
    private Date created;
}
