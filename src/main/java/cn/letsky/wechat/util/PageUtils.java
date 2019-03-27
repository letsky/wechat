package cn.letsky.wechat.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PageUtils {

    /**
     * 获取Pageable对象，并校验参数
     * @param page 页数
     * @param size 每页大小
     * @return Pageable对象
     */
    public static Pageable getPageable(Integer page, Integer size){
        if (page < 1 && size > 20){
            page = 1;
            size = 5;
        }
        return PageRequest.of(page - 1, size);
    }
}
