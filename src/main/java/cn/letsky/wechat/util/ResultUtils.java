package cn.letsky.wechat.util;

import cn.letsky.wechat.constant.ResultEnum;
import cn.letsky.wechat.vo.ResultVO;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public class ResultUtils {

    /**
     * 返回执行成功的结果
     *
     * @param data 需要返回的数据
     * @return 状态码200以及数据
     */
    @Deprecated
    public static <T> ResultVO<T> success(T data) {
        return new ResultVO<>(ResultEnum.SUCCESS.getCode(), data);
    }

    @Deprecated
    public static <T> ResultVO<T> success(T data, Map ext) {
        return new ResultVO<>(ResultEnum.SUCCESS.getCode(), data, ext);
    }

    /**
     * 返回执行成功的结果
     *
     * @return 状态码200
     */
    @Deprecated
    public static ResultVO success() {
        return success(null);
    }

    /**
     * 返回错误的状态码及信息
     *
     * @param resultEnum 状态码常量
     * @return 错误的状态码及信息
     */
    @Deprecated
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
    @Deprecated
    public static ResultVO error(int code, String msg) {
        return new ResultVO(code, msg);
    }


    /**
     * 执行成功 http状态码200
     *
     * @return
     */
    public static ResponseEntity ok() {
        return ResponseEntity.ok().build();
    }

    /**
     * 执行成功 http状态码200
     *
     * @param data 携带的消息
     * @param <T>  消息的类型
     * @return
     */
    public static <T> ResponseEntity<T> ok(T data) {
        return ResponseEntity.ok(data);
    }

    /**
     * http状态码404
     *
     * @return
     */
    public static ResponseEntity notFound() {
        return ResponseEntity.notFound().build();
    }

    /**
     * http状态码204
     *
     * @return
     */
    public static ResponseEntity notContent() {
        return ResponseEntity.noContent().build();
    }
}
