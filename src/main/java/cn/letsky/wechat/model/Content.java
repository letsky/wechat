package cn.letsky.wechat.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Content {

	@Id
	@Column(name = "content_id")
	private int id;
	
	private String content;
	
	private Date created;
	
	@Column(name = "author_id")
	private String authorId;
	
	/**
	 * 0为正常，1为删除，默认为0
	 */
	private int status;
	
	private String tag;
	
	@Column(name = "comments_num")
	private int commentNum;
	
	/**
	 * 是否允许评论，0为允许，1为不允许，默认为0
	 */
	@Column(name = "allow_comment")
	private int allowComment;
	
	public Content() {
		super();
	}
	
	public Content(int id, String content, Date created, String authorId,
			int commentNum, int allowComment) {
		this(id, content, created, authorId, 0, null, commentNum, 0);
	}

	public Content(int id, String content, Date created, String authorId, int status, String tag,
			int commentNum, int allowComment) {
		super();
		this.id = id;
		this.content = content;
		this.created = created;
		this.authorId = authorId;
		this.status = status;
		this.tag = tag;
		this.commentNum = commentNum;
		this.allowComment = allowComment;
	}
	
	
}
