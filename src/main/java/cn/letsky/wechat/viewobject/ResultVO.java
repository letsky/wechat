package cn.letsky.wechat.viewobject;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
public class ResultVO<T> implements Serializable{
	
	private static final long serialVersionUID = -1390040791477926573L;

	private Integer code;

	@JsonInclude(Include.NON_EMPTY)
    private String errmsg;

    @JsonInclude(Include.NON_EMPTY)
    private T data;

    public ResultVO(){
    }

    public ResultVO(Integer code, String errmsg){
        this.code = code;
        this.errmsg = errmsg;
        this.data = null;
    }

    public ResultVO(Integer code, T data) {
    	this.code = code;
        this.errmsg = null;
        this.data = data;
	}
}
