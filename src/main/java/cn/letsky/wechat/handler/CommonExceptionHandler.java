package cn.letsky.wechat.handler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import cn.letsky.wechat.exception.OperationException;
import cn.letsky.wechat.util.ResultUtils;
import cn.letsky.wechat.viewobject.ResultVO;

@RestControllerAdvice
public class CommonExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public ResultVO exceptionHandler(Exception e) {
		return ResultUtils.error(1, "不能访问的哦~");
	}

	@ExceptionHandler(OperationException.class)
	public ResultVO operationHandler(OperationException e) {
		return ResultUtils.error(e.getCode(), e.getMessage());
	}
	
	
}
