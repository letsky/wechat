package cn.letsky.wechat.handler;

import cn.letsky.wechat.constant.ResultEnum;
import cn.letsky.wechat.exception.CommonException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import cn.letsky.wechat.util.ResultUtils;
import cn.letsky.wechat.viewobject.ResultVO;

import javax.persistence.EntityNotFoundException;

@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public ResultVO exceptionHandler(Exception e) {
		log.error("[Exception]:" + e.getMessage());
		e.printStackTrace();
		return ResultUtils.error(ResultEnum.ERROR);
	}
	
	@ExceptionHandler(CommonException.class)
	public  ResultVO commonException(CommonException e){
		log.error("[CommonException]:" + e.getMessage());
		return ResultUtils.error(e.getCode(), e.getMessage());
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResultVO entityNotFoundException(EntityNotFoundException e){
		log.error("[EntityNotFoundException]:" + e.getMessage());
		return ResultUtils.error(ResultEnum.ENTITY_NOT_FOUNT);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResultVO illegalArgumentException(IllegalArgumentException e){
		log.error("[IllegalArgumentException]:" + e.getMessage());
		return ResultUtils.error(ResultEnum.PARAM_ERROR);
	}
}
