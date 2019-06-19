package cn.letsky.wechat.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@Entity
public class Article implements Serializable {

	private static final long serialVersionUID = 4746618198119865506L;
	/**
	 * 文章id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 文章内容
	 */
	private String content;

	/**
	 * 创建时间
	 */
	private Date created;

	/**
	 * 发表人openid
	 */
	private String openid;
	
	/**
	 * 文章状态，0为正常，1为删除，默认为0
	 */

	@JsonIgnore
	private Integer status;

	/**
	 * 标签
	 */
	@JsonIgnore
	private String tag;

	/**
	 * 图片
	 */
	private String img;

	/**
	 * 评论数
	 */
	@Column(name = "comment_num")
	private Integer commentNum;

	/**
	 * 点赞数
	 */
	@Column(name = "like_num")
	private Integer likeNum;
	
	/**
	 * 是否允许评论，0为允许，1为不允许，默认为0
	 */
	@JsonIgnore
	@Column(name = "allow_comment")
	private Integer allowComment;

	/**
	 * 可见性，0为允许他人可见，1为自己可见
	 */
	@JsonIgnore
	private Integer visible;
	
	public Article() {
		super();
		this.status = 0;
		this.tag = null;
		this.commentNum = 0;
		this.likeNum = 0;
		this.allowComment = 0;
		this.visible = 0;
	}

}
