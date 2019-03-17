package cn.letsky.wechat.exception;

import lombok.Getter;

@Getter
public class OperationException extends RuntimeException {
	
	private static final long serialVersionUID = -6955940688455031572L;

	private Integer code;

    public OperationException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }
}
