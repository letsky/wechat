package cn.letsky.wechat.exception;

import cn.letsky.wechat.constant.ResultEnum;
import lombok.Getter;

@Getter
public class CommonException extends RuntimeException {

    private static final long serialVersionUID = -1333432571817461142L;

    private Integer code;

    public CommonException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }
}
