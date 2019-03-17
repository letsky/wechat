package cn.letsky.wechat.util;

import javax.persistence.criteria.CommonAbstractCriteria;

import cn.letsky.wechat.constant.CommonEnum;
import cn.letsky.wechat.viewobject.ResultVO;

public class ResultUtils {

	/**
	 * 返回执行成功的结果，并返回相应的数据
	 * @param data
	 * @return 状态码200以及数据
	 */
    public static <T> ResultVO<T> success(T data){
        return new ResultVO<T>(CommonEnum.SUCCESS.getCode(), data);
    }

    /**
     * 返回执行成功的结果
     * @return 状态码200
     */
    public static ResultVO success(){
        return success(null);
    }

    /**
     * 返回失败的消息和状态码
     * @param code 调用失败的状态码
     * @param msg 调用失败的消息
     * @return
     */
    public static ResultVO error(Integer code, String msg){
        return new ResultVO(code, msg);
    }
}
