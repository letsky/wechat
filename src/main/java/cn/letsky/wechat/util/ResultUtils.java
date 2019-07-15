package cn.letsky.wechat.util;

import cn.letsky.wechat.constant.ResultEnum;
import cn.letsky.wechat.vo.ResultVO;

import java.util.Map;

public class ResultUtils {

    /**
     * 返回执行成功的结果
     *
     * @param data 需要返回的数据
     * @return 状态码200以及数据
     */
    public static <T> ResultVO<T> success(T data) {
        return new ResultVO<>(ResultEnum.SUCCESS.getCode(), data);
    }

    public static <T> ResultVO<T> success(T data, Map ext) {
        return new ResultVO<>(ResultEnum.SUCCESS.getCode(), data, ext);
    }

    /**
     * 返回执行成功的结果
     *
     * @return 状态码200
     */
    public static ResultVO success() {
        return success(null);
    }

    /**
     * 返回错误的状态码及信息
     *
     * @param resultEnum 状态码常量
     * @return 错误的状态码及信息
     */
    public static ResultVO error(ResultEnum resultEnum) {
        return new ResultVO(resultEnum.getCode(), resultEnum.getMsg());
    }

    /**
     * 返回错误的状态码及信息
     *
     * @param code 错误码
     * @param msg  错误消息
     * @return 错误的状态码及信息
     */
    public static ResultVO error(int code, String msg) {
        return new ResultVO(code, msg);
    }
}
