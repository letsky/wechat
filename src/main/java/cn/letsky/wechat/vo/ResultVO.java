package cn.letsky.wechat.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@NoArgsConstructor
public class ResultVO<T> implements Serializable{
	
	private static final long serialVersionUID = -1390040791477926573L;

	private Integer code;

	@JsonInclude(Include.NON_EMPTY)
    private String errmsg;

    @JsonInclude(Include.NON_EMPTY)
    private T data;

    @JsonInclude(Include.NON_EMPTY)
    private Map ext;

    public ResultVO(Integer code, String errmsg){
        this.code = code;
        this.errmsg = errmsg;
        this.data = null;
        this.ext = null;
    }

    public ResultVO(Integer code, T data) {
    	this.code = code;
        this.errmsg = null;
        this.data = data;
        this.ext = null;
	}

    public ResultVO(Integer code, T data, Map ext) {
        this.code = code;
        this.errmsg = null;
        this.data = data;
        this.ext = ext;
    }
}
