package cn.letsky.wechat.viewobject;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
public class ArticleVO implements Serializable {
	 
	private static final long serialVersionUID = 1635181928183897522L;

	private Integer id;
	
	private String content;

	@JsonInclude(Include.NON_EMPTY)
	private String[] imgs;
	
	private Date created;
	
	private String openid;
	
	private String nickname;
	
	private String avatarUrl;
	
	@JsonInclude(Include.NON_EMPTY)
	private String tag;

	@JsonInclude(Include.NON_EMPTY)
	private Integer commentNum;

	@JsonInclude(Include.NON_EMPTY)
	private Integer likeNum;
	
	/**
	 * 是否允许评论，0为允许，1为不允许，默认为0
	 */
	@JsonInclude(Include.NON_EMPTY)
	private Integer allowComment;

	public ArticleVO() {

	}
}
