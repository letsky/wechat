package cn.letsky.wechat.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
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

	private Long commentNum;

    /**
     * 是否点赞
     */
    @JsonInclude(Include.NON_EMPTY)
    private Integer liked;

    /**
     * 是否关注
     */
    @JsonInclude(Include.NON_EMPTY)
    private Integer followed;

	/**
	 * 是否显示关注按钮
	 */
	@JsonInclude(Include.NON_EMPTY)
    private boolean showFollowButton;

	private Long likeNum;
	
	/**
	 * 是否允许评论，0为允许，1为不允许，默认为0
	 */
	@JsonInclude(Include.NON_EMPTY)
	private Integer allowComment;
}
