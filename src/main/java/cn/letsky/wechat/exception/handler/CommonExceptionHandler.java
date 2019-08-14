package cn.letsky.wechat.exception.handler;

import cn.letsky.wechat.constant.ResultEnum;
import cn.letsky.wechat.exception.CommonException;
import cn.letsky.wechat.util.ResultUtils;
import cn.letsky.wechat.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.NoSuchElementException;

/**
 * 异常处理类
 */
@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResultVO exceptionHandler(Exception e) {
		log.error("[Exception]:" + getTrace(e));
		return ResultUtils.error(ResultEnum.ERROR);
	}

	@ExceptionHandler(CommonException.class)
	public ResponseEntity<ResultVO> commonException(CommonException e) {
		log.error("[CommonException]:" + getTrace(e));
		ResultVO resultVO = new ResultVO(e.getCode(), e.getMessage());
		return new ResponseEntity<>(resultVO, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResultVO entityNotFoundException(EntityNotFoundException e){
		log.error("[EntityNotFoundException]:" + getTrace(e));
		return ResultUtils.error(ResultEnum.ENTITY_NOT_FOUNT);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResultVO illegalArgumentException(IllegalArgumentException e){
		log.error("[IllegalArgumentException]:" + getTrace(e));
		return ResultUtils.error(ResultEnum.PARAM_ERROR);
	}

    @ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<ResultVO> noSuchElementException(NoSuchElementException e) {
		log.error("[NoSuchElementException]:" + getTrace(e));
		ResultVO resultVO = new ResultVO(ResultEnum.ENTITY_NOT_FOUNT);
		return new ResponseEntity<>(resultVO, HttpStatus.NOT_FOUND);
    }

	/**
	 * 处理MediaType异常
	 *
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public ResponseEntity<ResultVO> httpMediaTypeNotSupportedException(
			HttpMediaTypeNotSupportedException ex) {
		log.error("[HttpMediaTypeNotSupportedException]:" + getTrace(ex));
		ResultVO resultVO = new ResultVO();
		resultVO.setCode(ResultEnum.NOT_SUPPORT_CONTENT_TYPE.getCode());
		resultVO.setErrmsg(ex.getMessage());
		return new ResponseEntity<>(resultVO, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
	}

	/**
	 * 获取异常堆栈信息
	 *
	 * @param t throwable
	 * @return
	 */
	private String getTrace(Throwable t) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		t.printStackTrace(pw);
		StringBuffer sb = sw.getBuffer();
		return sb.toString();
	}
}
