package cn.letsky.wechat.viewobject;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ContentVO implements Serializable {
	 
	private static final long serialVersionUID = 1635181928183897522L;

	private int id;
	
	private String content;
	
	private Date created;
	
	private String authorId;
	
	private String nickname;
	
	private String avatarUrl;
	
	@JsonInclude(Include.NON_EMPTY)
	private String tag;
	
	private int commentNum;
	
	private int likeNum;
	
	/**
	 * 是否允许评论，0为允许，1为不允许，默认为0
	 */
	private int allowComment;

}
