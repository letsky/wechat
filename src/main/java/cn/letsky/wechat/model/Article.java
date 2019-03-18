package cn.letsky.wechat.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Article {

	@Id
	private Integer id;
	
	private String content;
	
	private Date created;

	private String openid;
	
	/**
	 * 0为正常，1为删除，默认为0
	 */
	private Integer status;
	
	private String tag;
	
	@Column(name = "comment_num")
	private Integer commentNum;
	
	@Column(name = "like_num")
	private Integer likeNum;
	
	/**
	 * 是否允许评论，0为允许，1为不允许，默认为0
	 */
	@Column(name = "allow_comment")
	private Integer allowComment;
	
	public Article() {
		super();
	}
	
	public Article(Integer id, String content, Date created, String authorId,
				   Integer commentNum) {
		this(id, content, created, authorId, 0, null, commentNum, 0);
	}

	public Article(Integer id, String content, Date created, String openid, Integer status, String tag,
				   Integer commentNum, Integer allowComment) {
		super();
		this.id = id;
		this.content = content;
		this.created = created;
		this.openid = openid;
		this.status = status;
		this.tag = tag;
		this.commentNum = commentNum;
		this.allowComment = allowComment;
	}
	
	
}
