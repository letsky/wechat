package cn.letsky.wechat.exception.handler;

import cn.letsky.wechat.constant.ResultEnum;
import cn.letsky.wechat.exception.CommonException;
import cn.letsky.wechat.util.ResultUtils;
import cn.letsky.wechat.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 异常处理类
 */
@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Void> exceptionHandler(Exception e) {
		log.error("[Exception]:" + getTrace(e));
		return ResponseEntity.badRequest().build();
	}

	@ExceptionHandler(CommonException.class)
	public ResponseEntity<ResultVO> commonException(CommonException e) {
		log.error("[CommonException]:" + getTrace(e));
		ResultVO resultVO = new ResultVO(e.getCode(), e.getMessage());
		return ResultUtils.badRequest(resultVO);
	}

	//返回404
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ResultVO> entityNotFoundException(EntityNotFoundException e) {
		log.error("[EntityNotFoundException]:" + getTrace(e));
		ResultVO resultVO = new ResultVO(ResultEnum.ENTITY_NOT_FOUNT);
		return ResultUtils.notFound(resultVO);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ResultVO> illegalArgumentException(IllegalArgumentException e) {
		log.error("[IllegalArgumentException]:" + getTrace(e));
		ResultVO resultVO = new ResultVO(ResultEnum.PARAM_ERROR);
		return ResultUtils.badRequest(resultVO);
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
	 * 处理请求方法异常
	 *
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ResultVO> httpRequestMethodNotSupportedException(
			HttpRequestMethodNotSupportedException ex) {
		log.error("[HttpRequestMethodNotSupportedException]:" + getTrace(ex));
		ResultVO resultVO = new ResultVO();
		resultVO.setCode(ResultEnum.NOT_SUPPORT_REQUEST_METHOD.getCode());
		resultVO.setErrmsg(ex.getMessage());
		return new ResponseEntity<>(resultVO, HttpStatus.METHOD_NOT_ALLOWED);
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
